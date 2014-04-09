package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

import java.util.Arrays;
import java.util.List;

public class AchievementsConfig {
  private final static List<Achievement> ALL_ACHIEVEMENTS = Arrays.asList(new Achievement() {
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

    @NotNull
    public String getEvent() {
      return AchievementEvents.buildUnpinned.name();
    }

    public int getEventsCount() {
      return 10;
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
      return "Granted for bombing (muting) several tests with automatic explosion (unmute) on a specific date.";
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

    @NotNull
    public String getEvent() {
      return AchievementEvents.testDisarmed.name();
    }

    public int getEventsCount() {
      return 20;
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-magnet";
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
      return "Granted if a person is assigned investigation for a series of broken compilations.";
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
      return "Granted for a series of commits mentioning fixed bugs.";
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
      return "Granted for assigning investigations to several different persons.";
    }

    @NotNull
    public String getEvent() {
      return AchievementEvents.investigationDelegated.name();
    }

    public int getEventsCount() {
      return 10;
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-eye-open";
    }
  },


  new Achievement() {
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

    @NotNull
    public String getEvent() {
      return AchievementEvents.buildTagged.name();
    }

    public int getEventsCount() {
      return 20;
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-tag";
    }
  },

  new Achievement() {
    @NotNull
    public String getId() {
      return "novelist";
    }

    @NotNull
    public String getName() {
      return "Novelist";
    }

    @NotNull
    public String getDescription() {
      return "Granted for extra long commit descriptions.";
    }

    @NotNull
    public String getEvent() {
      return AchievementEvents.longCommentAdded.name();
    }

    public int getEventsCount() {
      return 1;
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-book";
    }
  },

  new Achievement() {
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

    @NotNull
    public String getEvent() {
      return AchievementEvents.investigationTaken.name();
    }

    public int getEventsCount() {
      return 10;
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-fire";
    }
  }

  );

  @NotNull
  public List<Achievement> getAchievements() {
    return ALL_ACHIEVEMENTS;
  }
}
