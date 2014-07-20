package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class Sapper extends SimpleAchievement {
  public Sapper() {
    super(AchievementEvents.testDisarmed.name(), 20);
  }

  @NotNull
  public String getId() {
    return "sapper";
  }

  @NotNull
  public String getName() {
    return "Sapper";
  }

  @NotNull
  public String getDescription() {
    return "Granted for disarming (unmuting) several tests.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-magnet";
  }
}
