package org.jetbrains.buildServer.achievements.impl;

import jetbrains.buildServer.BaseTestCase;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.util.TimeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.impl.UserEventsRegistryImpl;
import org.jmock.Mock;
import org.testng.annotations.BeforeMethod;

import java.util.Date;

public class AchievementsTestCase extends BaseTestCase {
  private UserEventsRegistryImpl myRegistry;
  private long myTime;

  @BeforeMethod
  protected void setUp() throws Exception {
    super.setUp();
    TimeService timeService = new TimeService() {
      public long now() {
        return myTime;
      }
    };
    myTime = new Date().getTime();
    myRegistry = new UserEventsRegistryImpl(timeService);
  }

  public SUser createUser() {
    Mock userMock = mock(SUser.class);
    userMock.stubs().method("getId").will(returnValue(1L));
    return (SUser) userMock.proxy();
  }

  public UserEvents getUserEvents(@NotNull SUser user) {
    return myRegistry.getUserEvents(user);
  }

  public void setTime(long time) {
    myTime = time;
  }


}
