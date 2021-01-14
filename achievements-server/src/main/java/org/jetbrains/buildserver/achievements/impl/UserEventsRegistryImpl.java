/*
 * Copyright 2000-2021 JetBrains s.r.o.
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

import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.SystemTimeService;
import jetbrains.buildServer.util.TimeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.UserEventsListener;
import org.jetbrains.buildserver.achievements.UserEventsRegistry;

import java.util.HashMap;
import java.util.Map;

public class UserEventsRegistryImpl implements UserEventsRegistry {
  private final EventDispatcher<UserEventsListener> myEventDispatcher = EventDispatcher.create(UserEventsListener.class);
  private Map<Long, UserEvents> myUserEvents = new HashMap<Long, UserEvents>();
  private final TimeService myTimeService;

  public UserEventsRegistryImpl(@NotNull TimeService timeService) {
    myTimeService = timeService;
  }

  @NotNull
  public UserEvents getUserEvents(@NotNull final User user) {
    UserEvents userEvents = myUserEvents.get(user.getId());
    if (userEvents == null) {
      userEvents = new UserEventsImpl(myTimeService) {
        @Override
        public synchronized void registerEvent(@NotNull String eventName) {
          super.registerEvent(eventName);
          myEventDispatcher.getMulticaster().userEventPublished(user, eventName, null);
        }

        public void registerEvent(@NotNull String eventName, @Nullable Object additionalData) {
          super.registerEvent(eventName);
          myEventDispatcher.getMulticaster().userEventPublished(user, eventName, additionalData);
        }
      };
      myUserEvents.put(user.getId(), userEvents);
    }
    return userEvents;
  }


  public void addListener(@NotNull UserEventsListener listener) {
    myEventDispatcher.addListener(listener);
  }

  public void removeListener(@NotNull UserEventsListener listener) {
    myEventDispatcher.removeListener(listener);
  }
}
