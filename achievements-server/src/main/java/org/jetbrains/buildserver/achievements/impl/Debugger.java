

package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class Debugger extends SimpleAchievement {
  public Debugger() {
    super(AchievementEvents.issueMentioned.name(), 10);
  }

  @NotNull
  public String getId() {
    return "debugger";
  }

  @NotNull
  public String getName() {
    return "Debugger";
  }

  @NotNull
  public String getDescription() {
    return "Granted for a series of commits mentioning fixed bugs.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-bug";
  }
}