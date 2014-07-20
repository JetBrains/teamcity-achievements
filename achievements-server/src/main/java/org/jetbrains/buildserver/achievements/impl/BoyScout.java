package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class BoyScout extends SimpleAchievement {
  public BoyScout() {
    super(AchievementEvents.investigationTaken.name(), 10);
  }

  @NotNull
  public String getId() {
    return "boyscout";
  }

  @NotNull
  public String getName() {
    return "Boy Scout";
  }

  @NotNull
  public String getDescription() {
    return "Granted for assigning several investigations for a test or problem to himself.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-fire";
  }
}
