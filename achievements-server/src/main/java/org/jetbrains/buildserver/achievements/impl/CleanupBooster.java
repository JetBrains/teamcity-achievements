

package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class CleanupBooster extends SimpleAchievement {
  public CleanupBooster() {
    super(AchievementEvents.buildUnpinned.name(), 10);
  }

  @NotNull
  public String getId() {
    return "cleanupBooster";
  }

  @NotNull
  public String getName() {
    return "Cleanup Booster";
  }

  @NotNull
  public String getDescription() {
    return "Granted for unpinning several builds in a row. This will help cleanup process to free more disk space on the server.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-trash";
  }
}