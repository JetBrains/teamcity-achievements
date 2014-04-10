package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.SUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AchievementsConfig {
  private final static List<Achievement> ALL_ACHIEVEMENTS = Arrays.<Achievement>asList(new SimpleAchievement(AchievementEvents.buildUnpinned.name(), 10) {
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
  },


  new SimpleAchievement(AchievementEvents.testBombed.name(), 20) {
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
  },


  new SimpleAchievement(AchievementEvents.testDisarmed.name(), 20) {
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
  },


  new SimpleAchievement(AchievementEvents.compilationBroken.name(), 3) {
    @NotNull
    public String getId() {
      return "codeCrusher";
    }

    @NotNull
    public String getName() {
      return "Crusher";
    }

    @NotNull
    public String getDescription() {
      return "Granted if a person is assigned investigation for a series of broken compilations.";
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-frown";
    }
  },


  new SimpleAchievement(AchievementEvents.bugFixed.name(), 10) {
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
  },


  new SimpleAchievement(AchievementEvents.investigationDelegated.name(), 10) {
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
  },


  new SimpleAchievement(AchievementEvents.buildTagged.name(), 20) {
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
  },

  new SimpleAchievement(AchievementEvents.longCommentAdded.name(), 1) {
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

    @Nullable
    public String getIconClassNames() {
      return "icon-book";
    }
  },

  new SimpleAchievement(AchievementEvents.investigationTaken.name(), 10) {
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
  },

  new Achievement() {
    @NotNull
    public String getId() {
      return "earlyBird";
    }

    @NotNull
    public String getName() {
      return "Early Bird";
    }

    @NotNull
    public String getDescription() {
      return "Granted for some early action on the build server.";
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-time";
    }

    public boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, Object additionalData) {
      if (!(additionalData instanceof java.util.TimeZone)) return false;

      long timestamp = events.getLastEventTime(AchievementEvents.userAction.name());
      Calendar c = Calendar.getInstance();
      c.setTimeZone((java.util.TimeZone) additionalData);
      c.setTime(new Date(timestamp));

      return  c.get(Calendar.HOUR_OF_DAY) < 8;
    }
  }

  );

  @NotNull
  public List<Achievement> getAchievements() {
    return ALL_ACHIEVEMENTS;
  }
}
