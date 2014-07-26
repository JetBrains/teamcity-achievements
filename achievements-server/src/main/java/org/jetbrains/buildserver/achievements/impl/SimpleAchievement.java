package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.SUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.UserEvents;

public abstract class SimpleAchievement implements Achievement {
  private final String myEventName;
  private final int myEventsCount;

  protected SimpleAchievement(@NotNull String eventName, int eventsCount) {
    myEventName = eventName;
    myEventsCount = eventsCount;
  }

  public boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, @NotNull String lastEventName, @Nullable Object additionalData) {
    return true || events.getNumberOfEvents(myEventName) >= myEventsCount;
  }
}
