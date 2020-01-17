/*
 * Copyright 2000-2020 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.buildserver.achievements.impl;

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

  @NotNull
  public UserEventsRegistryImpl getRegistry() {
    return myRegistry;
  }
}
