package org.jetbrains.buildserver.achievements.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class Crusher extends SimpleAchievement {
  public Crusher() {
    super(AchievementEvents.compilationBroken.name(), 3);
  }

  @NotNull
  public String getId() {
    return "codeCrusher";
  }

  @NotNull
  public String getName() {
    return "Crusher";
  }

  @NotNull
  public String getDescription() {
    return "Granted if a person is assigned investigation for a series of broken compilations.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-frown";
  }
}
