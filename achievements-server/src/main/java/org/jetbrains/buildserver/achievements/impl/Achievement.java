

package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.SUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.UserEvents;

public interface Achievement {
  @NotNull
  String getId();

  @NotNull
  String getName();

  @NotNull
  String getDescription();

  @Nullable
  String getIconClassNames();

  boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, @NotNull String lastEventName, @Nullable Object lastEventAdditionalData);
}