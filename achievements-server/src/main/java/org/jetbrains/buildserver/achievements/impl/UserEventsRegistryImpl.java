package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.UserEventsRegistry;

import java.util.HashMap;
import java.util.Map;

public class UserEventsRegistryImpl implements UserEventsRegistry {
  private Map<Long, UserEvents> myUserEvents = new HashMap<Long, UserEvents>();

  @NotNull
  public UserEvents getUserEvents(@NotNull User user) {
    UserEvents userEvents = myUserEvents.get(user.getId());
    if (userEvents == null) {
      userEvents = new UserEventsImpl();
      myUserEvents.put(user.getId(), userEvents);
    }
    return userEvents;
  }

}
