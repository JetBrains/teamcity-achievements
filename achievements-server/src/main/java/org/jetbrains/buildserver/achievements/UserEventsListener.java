package org.jetbrains.buildserver.achievements;

import jetbrains.buildServer.users.User;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface UserEventsListener extends EventListener {
  void userEventsPublished(@NotNull User user);
}
