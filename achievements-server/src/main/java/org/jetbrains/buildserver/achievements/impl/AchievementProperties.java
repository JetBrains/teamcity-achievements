package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.PluginPropertyKey;
import org.jetbrains.annotations.NotNull;

public class AchievementProperties {
  @NotNull
  public static PluginPropertyKey getHidePropertyKey(@NotNull Achievement achievement) {
    return new PluginPropertyKey("achievements", "achievements", "achievement.hidden." + achievement.getId());
  }

  @NotNull
  public static PluginPropertyKey getGrantedPropertyKey(@NotNull Achievement achievement) {
    return new PluginPropertyKey("achievements", "achievements", "achievement.granted." + achievement.getId());
  }

  @NotNull
  public static PluginPropertyKey getAchievementsEnabledPropertyKey() {
    return new PluginPropertyKey("achievements", "achievements", "enabled");
  }
}
