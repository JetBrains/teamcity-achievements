package org.jetbrains.buildServer.achievements.impl;

import jetbrains.buildServer.BaseTestCase;
import jetbrains.buildServer.users.SUser;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.impl.UserEventsRegistryImpl;
import org.jmock.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class UserEventsRegistryImplTest extends BaseTestCase {
  private UserEventsRegistryImpl myRegistry;

  @BeforeMethod
  protected void setUp() throws Exception {
    super.setUp();
    myRegistry = new UserEventsRegistryImpl();
  }

  public void test_register_event() {
    Mock userMock = mock(SUser.class);
    userMock.stubs().method("getId").will(returnValue(1L));

    UserEvents userEvents = myRegistry.getUserEvents((SUser) userMock.proxy());
    userEvents.registerEvent("event1");
    assertEquals(1, userEvents.getNumberOfEvents("event1"));

    userEvents.registerEvent("event2");
    assertEquals(1, userEvents.getNumberOfEvents("event2"));

    userEvents.registerEvent("event1");
    assertEquals(2, userEvents.getNumberOfEvents("event1"));

    assertTrue(userEvents.getEventsRate("event1", 1) > 1.5);
  }
}
