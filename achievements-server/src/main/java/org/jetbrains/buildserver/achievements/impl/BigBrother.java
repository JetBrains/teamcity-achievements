package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class BigBrother extends SimpleAchievement {
  public BigBrother() {
    super(AchievementEvents.investigationDelegated.name(), 10);
  }

  @NotNull
  public String getId() {
    return "bigBrother";
  }

  @NotNull
  public String getName() {
    return "Big Brother";
  }

  @NotNull
  public String getDescription() {
    return "Granted for assigning investigations to several different persons.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-eye-open";
  }
}
