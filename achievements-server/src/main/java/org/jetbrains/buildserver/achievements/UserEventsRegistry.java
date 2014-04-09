package org.jetbrains.buildserver.achievements;

import jetbrains.buildServer.users.User;
import org.jetbrains.annotations.NotNull;

public interface UserEventsRegistry {
  @NotNull
  UserEvents getUserEvents(@NotNull User user);

  void addListener(@NotNull UserEventsListener listener);

  void removeListener(@NotNull UserEventsListener listener);
}