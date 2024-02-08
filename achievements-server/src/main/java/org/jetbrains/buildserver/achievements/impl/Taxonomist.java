

package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class Taxonomist extends SimpleAchievement {
  public Taxonomist() {
    super(AchievementEvents.buildTagged.name(), 10);
  }

  @NotNull
  public String getId() {
    return "taxonomist";
  }

  @NotNull
  public String getName() {
    return "Taxonomist";
  }

  @NotNull
  public String getDescription() {
    return "Granted for tagging several builds in a row.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-tag";
  }
}