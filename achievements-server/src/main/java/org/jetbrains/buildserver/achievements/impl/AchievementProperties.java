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

import jetbrains.buildServer.users.PluginPropertyKey;
import org.jetbrains.annotations.NotNull;

public class AchievementProperties {
  @NotNull
  public static PluginPropertyKey getHidePropertyKey(@NotNull Achievement achievement) {
    return new PluginPropertyKey("achievements", "achievements", "achievement.hidden." + achievement.getId());
  }

  @NotNull
  public static PluginPropertyKey getGrantedPropertyKey(@NotNull Achievement achievement) {
    return new PluginPropertyKey("achievements", "achievements", "achievement.granted." + achievement.getId());
  }

  @NotNull
  public static PluginPropertyKey getAchievementsEnabledPropertyKey() {
    return new PluginPropertyKey("achievements", "achievements", "enabled");
  }
}
