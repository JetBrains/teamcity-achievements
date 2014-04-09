package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.serverSide.SecurityContextEx;
import jetbrains.buildServer.users.PluginPropertyKey;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.users.UserModel;
import jetbrains.buildServer.util.ExceptionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.UserEventsListener;
import org.jetbrains.buildserver.achievements.UserEventsRegistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AchievementsGrantor implements UserEventsListener {
  private final AchievementsConfig myConfig;
  private final UserEventsRegistry myEventsRegistry;
  private final UserModel myUserModel;
  private final SecurityContextEx mySecurityContext;

  public AchievementsGrantor(@NotNull AchievementsConfig config,
                             @NotNull ServerEventsAdapter serverEventsAdapter,
                             @NotNull UserEventsRegistry userEventsRegistry,
                             @NotNull UserModel userModel,
                             @NotNull SecurityContextEx securityContext) {
    myConfig = config;
    myEventsRegistry = userEventsRegistry;
    myUserModel = userModel;
    mySecurityContext = securityContext;
    serverEventsAdapter.addListener(this);
  }

  public void userEventsPublished(@NotNull User user) {
    UserEvents events = myEventsRegistry.getUserEvents(user);
    for (Achievement achievement: myConfig.getAchievements()) {
      if (hasAchievement(user, achievement)) continue;

      String eventName = achievement.getEvent();
      int eventsCount = achievement.getEventsCount();

      if (events.getNumberOfEvents(eventName) >= eventsCount) {
        grantAchievement(user, achievement);
      }
    }
  }

  public boolean hasAchievement(@NotNull User user, @NotNull Achievement achievement) {
    return user.getPropertyValue(makePropertyKey(achievement)) != null;
  }

  public void grantAchievement(@NotNull User user, @NotNull Achievement achievement) {
    SUser su = (SUser) user;
    su.setUserProperty(makePropertyKey(achievement), String.valueOf(new Date().getTime()));
  }

  @NotNull
  public List<Achievement> getGrantedAchievements(@NotNull User user) {
    List<Achievement> res = new ArrayList<Achievement>();
    for (Achievement achievement: myConfig.getAchievements()) {
      if (hasAchievement(user, achievement)) {
        res.add(achievement);
      }
    }

    return res;
  }

  public int getNumberOfUsersWithAchievement(@NotNull final Achievement achievement) {
    try {
      return mySecurityContext.runAsSystem(new SecurityContextEx.RunAsActionWithResult<Integer>() {
        public Integer run() throws Throwable {
          int count = 0;
          for (SUser user: myUserModel.getAllUsers().getUsers()) {
            if (hasAchievement(user, achievement)) {
              count++;
            }
          }
          return count;
        }
      });
    } catch (Throwable throwable) {
      ExceptionUtil.rethrowAsRuntimeException(throwable);
    }
    return 0;
  }

  @NotNull
  private PluginPropertyKey makePropertyKey(@NotNull Achievement achievement) {
    return new PluginPropertyKey("achievements", "achievements", "achievement.granted." + achievement.getId());
  }

}
