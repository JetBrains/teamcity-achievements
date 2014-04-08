package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.AchievementEvents;

import java.util.Arrays;
import java.util.List;

public class AchievementsConfig {
  @NotNull
  public List<Achievement> getAchievements() {
    return Arrays.asList(new Achievement() {
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
        return "Achievement is assigned for unpinning several builds in a row. This will help cleanup process to free more disk space on the server.";
      }

      @NotNull
      public String getEvent() {
        return AchievementEvents.buildUnpinned.name();
      }

      public int getEventsCount() {
        return 20;
      }
    }, new Achievement() {
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
        return "Achievement is assigned for muting (bombing) several tests with automatic unmute (explosion) on specific date.";
      }

      @NotNull
      public String getEvent() {
        return AchievementEvents.testBombed.name();
      }

      public int getEventsCount() {
        return 20;
      }
    });
  }
}
