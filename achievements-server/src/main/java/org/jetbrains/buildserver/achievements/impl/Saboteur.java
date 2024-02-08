

package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class Saboteur extends SimpleAchievement {
  public Saboteur() {
    super(AchievementEvents.testBombed.name(), 20);
  }

  @NotNull
  public String getId() {
    return "saboteur";
  }

  @NotNull
  public String getName() {
    return "Saboteur";
  }

  @NotNull
  public String getDescription() {
    return "Granted for bombing (muting) several tests with automatic explosion (unmute) on a specific date.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-male";
  }
}