package org.jetbrains.buildServer.achievements.impl;

import jetbrains.buildServer.users.SUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.impl.Achievement;
import org.jetbrains.buildserver.achievements.impl.AchievementsConfig;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.TimeZone;

@Test
public class AchievementsConfigTest extends AchievementsTestCase {

  public void night_owl() {
    Achievement achievement = getAchievement("nightOwl");
    SUser user = createUser();
    UserEvents userEvents = getUserEvents(user);

    setHour(1);

    TimeZone tz = TimeZone.getDefault();
    userEvents.registerEvent(AchievementEvents.userAction.name(), tz);

    assertFalse(achievement.shouldGrantAchievement(user, userEvents, AchievementEvents.userAction.name(), tz));

    setHour(2);

    userEvents.registerEvent(AchievementEvents.userAction.name(), tz);

    setHour(3);

    userEvents.registerEvent(AchievementEvents.userAction.name(), tz);

    assertTrue(achievement.shouldGrantAchievement(user, userEvents, AchievementEvents.userAction.name(), tz));
  }

  public void early_bird() {
    Achievement achievement = getAchievement("earlyBird_2");
    SUser user = createUser();
    UserEvents userEvents = getUserEvents(user);

    setHour(5);

    TimeZone tz = TimeZone.getDefault();
    userEvents.registerEvent(AchievementEvents.userAction.name(), tz);

    assertFalse(achievement.shouldGrantAchievement(user, userEvents, AchievementEvents.userAction.name(), tz));

    setHour(6);

    userEvents.registerEvent(AchievementEvents.userAction.name(), tz);

    setHour(7);

    userEvents.registerEvent(AchievementEvents.userAction.name(), tz);

    assertTrue(achievement.shouldGrantAchievement(user, userEvents, AchievementEvents.userAction.name(), tz));
  }

  private void setHour(int hour) {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, hour);
    setTime(c.getTime().getTime());
  }

  private Achievement getAchievement(@NotNull String id) {
    return new AchievementsConfig().getAchievementsMap().get(id);
  }
}
