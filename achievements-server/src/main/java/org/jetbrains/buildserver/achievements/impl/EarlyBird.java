package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.users.SUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;

import java.util.TimeZone;

public class EarlyBird implements Achievement {
  @NotNull
  public String getId() {
    return "earlyBird_2";
  }

  @NotNull
  public String getName() {
    return "Early Bird";
  }

  @NotNull
  public String getDescription() {
    return "Granted for some early action on the build server.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-twitter";
  }

  public boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, @NotNull String lastEventName, Object additionalData) {
    return lastEventName.equals(AchievementEvents.userAction.name()) &&
        additionalData instanceof TimeZone &&
        UserActionUtil.checkUserActionsMadeBetween(events, (TimeZone) additionalData, 5, 8);

  }
}
