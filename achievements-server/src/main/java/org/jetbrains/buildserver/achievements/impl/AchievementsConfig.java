package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
        return 1;
      }

      @Nullable
      public String getIconClassNames() {
        return "icon-trash";
      }
    },


    new Achievement() {
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

      @Nullable
      public String getIconClassNames() {
        return "icon-male";
      }
    },


    new Achievement() {
      @NotNull
      public String getId() {
        return "codeCrusher";
      }

      @NotNull
      public String getName() {
        return "Code Crusher";
      }

      @NotNull
      public String getDescription() {
        return "Achievement is assigned for a series of broken compilations.";
      }

      @NotNull
      public String getEvent() {
        return AchievementEvents.compilationBroken.name();
      }

      public int getEventsCount() {
        return 3;
      }

      @Nullable
      public String getIconClassNames() {
        return "icon-frown";
      }
    },


    new Achievement() {
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
        return "Achievement is assigned for a series of commits mentioning fixed bugs during the short period of time.";
      }

      @NotNull
      public String getEvent() {
        return AchievementEvents.bugFixed.name();
      }

      public int getEventsCount() {
        return 10;
      }

      @Nullable
      public String getIconClassNames() {
        return "icon-bug";
      }
    },


    new Achievement() {
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
        return "Achievement is granted for assigning investigations to several different persons.";
      }

      @NotNull
      public String getEvent() {
        return AchievementEvents.investigationAssigned.name();
      }

      public int getEventsCount() {
        return 10;
      }

      @Nullable
      public String getIconClassNames() {
        return "icon-eye-open";
      }
    });
  }
}
