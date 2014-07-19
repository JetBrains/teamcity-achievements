package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.vcs.SVcsModification;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;

import java.util.*;

public class AchievementsConfig {
  private final static List<Achievement> ALL_ACHIEVEMENTS = Arrays.asList(new SimpleAchievement(AchievementEvents.buildUnpinned.name(), 10) {
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


  new SimpleAchievement(AchievementEvents.issueMentioned.name(), 10) {
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


  new SimpleAchievement(AchievementEvents.buildTagged.name(), 10) {
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

    @Nullable
    public String getIconClassNames() {
      return "icon-book";
    }

    public boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, @NotNull String lastEventName, Object additionalData) {
      if (!lastEventName.equals(AchievementEvents.changeAdded.name())) return false;
      if (!(additionalData instanceof SVcsModification)) return false;
      SVcsModification mod = (SVcsModification) additionalData;
      return mod.getDescription().length() > 3000;
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
      return "earlyBird_2";
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
      return "icon-twitter";
    }

    public boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, @NotNull String lastEventName, Object additionalData) {
      return lastEventName.equals(AchievementEvents.userAction.name()) &&
          additionalData instanceof TimeZone &&
          checkUserActionsMadeBetween(events, (TimeZone) additionalData, 5, 8);

    }
  },

  new Achievement() {
    @NotNull
    public String getId() {
      return "nightOwl";
    }

    @NotNull
    public String getName() {
      return "Night Owl";
    }

    @NotNull
    public String getDescription() {
      return "Granted for some late action on the build server.";
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-time";
    }

    public boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, @NotNull String lastEventName, Object additionalData) {
      return lastEventName.equals(AchievementEvents.userAction.name()) &&
          additionalData instanceof TimeZone &&
          checkUserActionsMadeBetween(events, (TimeZone) additionalData, 1, 4);
    }
  },

  new SimpleAchievement(AchievementEvents.changeAdded.name(), 20) {
    @NotNull
    public String getId() {
      return "productivityBoost";
    }

    @NotNull
    public String getName() {
      return "Productivity Boost";
    }

    @NotNull
    public String getDescription() {
      return "Granted for making a lot of commits during the day.";
    }

    @Nullable
    public String getIconClassNames() {
      return "icon-thumbs-up";
    }
  }

  );

  private static boolean checkUserActionsMadeBetween(@NotNull UserEvents events, @NotNull TimeZone userTimeZone, int minHour, int maxHour) {
    int numEvents = 3;
    List<Long> times = events.getEventTimes(AchievementEvents.userAction.name());
    if (times.size() < numEvents) return false;

    for (int i=0; i<numEvents; i++) {
      long timestamp = times.get(i);

      Calendar c = Calendar.getInstance();
      c.setTimeZone(userTimeZone);
      c.setTime(new Date(timestamp));
      int hour = c.get(Calendar.HOUR_OF_DAY);
      if (hour < minHour || hour > maxHour) return false;
    }

    return true;
  }

  @NotNull
  public List<Achievement> getAchievements() {
    return ALL_ACHIEVEMENTS;
  }

  @NotNull
  public Map<String, Achievement> getAchievementsMap() {
    Map<String, Achievement> res = new HashMap<String, Achievement>();
    for (Achievement a: ALL_ACHIEVEMENTS) {
      res.put(a.getId(), a);
    }
    return res;
  }
}
