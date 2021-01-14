/*
 * Copyright 2000-2021 JetBrains s.r.o.
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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;

public class Saboteur extends SimpleAchievement {
  public Saboteur() {
    super(AchievementEvents.testBombed.name(), 20);
  }

  @NotNull
  public String getId() {
    return "saboteur";
  }

  @NotNull
  public String getName() {
    return "Saboteur";
  }

  @NotNull
  public String getDescription() {
    return "Granted for bombing (muting) several tests with automatic explosion (unmute) on a specific date.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-male";
  }
}
