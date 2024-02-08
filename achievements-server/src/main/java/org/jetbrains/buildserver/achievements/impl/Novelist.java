

package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.vcs.SVcsModification;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;

public class Novelist implements Achievement {
  @NotNull
  public String getId() {
    return "novelist";
  }

  @NotNull
  public String getName() {
    return "Novelist";
  }

  @NotNull
  public String getDescription() {
    return "Granted for extra long commit descriptions.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-book";
  }

  public boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, @NotNull String lastEventName, Object additionalData) {
    if (!lastEventName.equals(AchievementEvents.changeAdded.name())) return false;
    if (!(additionalData instanceof SVcsModification)) return false;
    SVcsModification mod = (SVcsModification) additionalData;
    return mod.getDescription().length() > 3000;
  }
}