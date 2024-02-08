

package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.BaseTestCase;
import jetbrains.buildServer.users.SUser;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.impl.UserEventsRegistryImpl;
import org.jmock.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

@Test
public class UserEventsRegistryImplTest extends AchievementsTestCase {
  public void test_register_event() {
    UserEvents userEvents = getUserEvents(createUser());
    userEvents.registerEvent("event1");
    assertEquals(1, userEvents.getNumberOfEvents("event1"));

    userEvents.registerEvent("event2");
    assertEquals(1, userEvents.getNumberOfEvents("event2"));

    userEvents.registerEvent("event1");
    assertEquals(2, userEvents.getNumberOfEvents("event1"));

    assertTrue(userEvents.getEventsRate("event1", 1) > 1.5);
  }

  public void test_event_times() throws InterruptedException {
    UserEvents userEvents = getUserEvents(createUser());
    setTime(new Date().getTime() + 20);
    userEvents.registerEvent("event1");

    setTime(new Date().getTime() + 40);
    userEvents.registerEvent("event1");

    setTime(new Date().getTime() + 60);
    userEvents.registerEvent("event1");

    assertEquals(3, userEvents.getNumberOfEvents("event1"));

    List<Long> times = userEvents.getEventTimes("event1");
    assertEquals(3, times.size());

    assertTrue(times.toString(), times.get(0) > times.get(1) && times.get(1) > times.get(2));
  }
}