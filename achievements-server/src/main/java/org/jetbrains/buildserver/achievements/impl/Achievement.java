package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

  @Nullable
  String getIconClassNames();
}
