/*
 * Copyright 2000-2020 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
