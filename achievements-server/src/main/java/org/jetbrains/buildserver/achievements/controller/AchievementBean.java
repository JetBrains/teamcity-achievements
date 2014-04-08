package org.jetbrains.buildserver.achievements.controller;

import jetbrains.buildServer.users.PluginPropertyKey;
import jetbrains.buildServer.users.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.impl.Achievement;

public class AchievementBean {
  private final Achievement myAchievement;

  public AchievementBean(@NotNull Achievement achievement) {
    myAchievement = achievement;
  }

  @NotNull
  public String getHidePropertyKey() {
    return makePropertyKey().getKey();
  }

  public Achievement getAchievement() {
    return myAchievement;
  }

  public boolean isHidden(@NotNull User user) {
    return user.getBooleanProperty(makePropertyKey());
  }

  @NotNull
  private PluginPropertyKey makePropertyKey() {
    return new PluginPropertyKey("achievements", "achievements", "achievement.hidden." + myAchievement.getId());
  }
}
