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
