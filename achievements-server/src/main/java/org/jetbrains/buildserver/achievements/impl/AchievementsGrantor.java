package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.PluginPropertyKey;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.UserEventsListener;
import org.jetbrains.buildserver.achievements.UserEventsRegistry;

import java.util.Date;

public class AchievementsGrantor implements UserEventsListener {
  private final AchievementsConfig myConfig;
  private final UserEventsRegistry myEventsRegistry;

  public AchievementsGrantor(@NotNull AchievementsConfig config, @NotNull ServerEventsAdapter serverEventsAdapter, @NotNull UserEventsRegistry userEventsRegistry) {
    myConfig = config;
    myEventsRegistry = userEventsRegistry;
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
  private PluginPropertyKey makePropertyKey(@NotNull Achievement achievement) {
    return new PluginPropertyKey("achievements", "achievements", "achievement.granted." + achievement.getId());
  }

}
