package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.EventDispatcher;
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

  @NotNull
  public UserEvents getUserEvents(@NotNull final User user) {
    UserEvents userEvents = myUserEvents.get(user.getId());
    if (userEvents == null) {
      userEvents = new UserEventsImpl() {
        @Override
        public synchronized void registerEvent(@NotNull String eventName) {
          super.registerEvent(eventName);
          myEventDispatcher.getMulticaster().userEventsPublished(user, null);
        }

        public void registerEvent(@NotNull String eventName, @Nullable Object additionalData) {
          super.registerEvent(eventName);
          myEventDispatcher.getMulticaster().userEventsPublished(user, additionalData);
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
