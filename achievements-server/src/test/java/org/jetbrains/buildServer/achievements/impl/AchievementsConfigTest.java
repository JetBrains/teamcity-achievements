

package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.vcs.SVcsModification;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jmock.Mock;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Test
public class AchievementsConfigTest extends AchievementsTestCase {

  public void night_owl() {
    Achievement achievement = new NightOwl();
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
    Achievement achievement = new EarlyBird();
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

  public void test_productivity_boost_not_granted() {
    Achievement achievement = new ProductivityBoost(getRegistry());
    SUser user = createUser();
    UserEvents userEvents = getUserEvents(user);

    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_MONTH, -2);

    Mock modMock = mock(SVcsModification.class);
    modMock.stubs().method("getVcsDate").will(returnValue(c.getTime()));

    SVcsModification mod = (SVcsModification) modMock.proxy();

    for (int i=0; i<25; i++) {
      userEvents.registerEvent(AchievementEvents.changeAdded.name(), mod);
      assertFalse(achievement.shouldGrantAchievement(user, userEvents, AchievementEvents.changeAdded.name(), mod));
    }
  }

  public void test_productivity_boost() {
    Achievement achievement = new ProductivityBoost(getRegistry());
    SUser user = createUser();
    UserEvents userEvents = getUserEvents(user);

    Mock modMock = mock(SVcsModification.class);
    modMock.stubs().method("getVcsDate").will(returnValue(new Date()));

    SVcsModification mod = (SVcsModification) modMock.proxy();

    for (int i=0; i<19; i++) {
      userEvents.registerEvent(AchievementEvents.changeAdded.name(), mod);
      assertFalse(achievement.shouldGrantAchievement(user, userEvents, AchievementEvents.changeAdded.name(), mod));
    }

    userEvents.registerEvent(AchievementEvents.changeAdded.name(), mod);
    assertTrue(achievement.shouldGrantAchievement(user, userEvents, AchievementEvents.changeAdded.name(), mod));
  }

  private void setHour(int hour) {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, hour);
    setTime(c.getTime().getTime());
  }
}