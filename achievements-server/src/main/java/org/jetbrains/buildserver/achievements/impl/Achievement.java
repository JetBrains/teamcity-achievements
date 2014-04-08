package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;

public interface Achievement {
  @NotNull
  String getId();

  @NotNull
  String getName();

  @NotNull
  String getDescription();

  @NotNull
  String getEvent();

  int getEventsCount();
}
