package org.jetbrains.buildserver.achievements;

import org.jetbrains.annotations.NotNull;

public interface UserEvents {
  void registerEvent(@NotNull String eventName);

  int getNumberOfEvents(@NotNull String eventName);

  float getEventsRate(@NotNull String eventName, int timeIntervalSeconds);
}
