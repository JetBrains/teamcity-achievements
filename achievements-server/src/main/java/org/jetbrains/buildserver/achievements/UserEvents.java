package org.jetbrains.buildserver.achievements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UserEvents {
  void registerEvent(@NotNull String eventName);

  void registerEvent(@NotNull String eventName, @Nullable Object additionalData);

  long getLastEventTime(@NotNull String eventName);

  // most recent first
  @NotNull
  List<Long> getEventTimes(@NotNull String eventName);

  int getNumberOfEvents(@NotNull String eventName);

  float getEventsRate(@NotNull String eventName, int timeIntervalSeconds);
}
