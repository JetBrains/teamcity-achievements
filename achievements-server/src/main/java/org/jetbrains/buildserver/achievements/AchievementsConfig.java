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

package org.jetbrains.buildserver.achievements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.impl.Achievement;

import java.util.*;

public class AchievementsConfig {
  private final List<Achievement> myAchievements;

  public AchievementsConfig(@NotNull Collection<Achievement> achievements) {
    myAchievements = new ArrayList<Achievement>(achievements);
    Collections.sort(myAchievements, new Comparator<Achievement>() {
      public int compare(Achievement o1, Achievement o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
      }
    });
  }

  @NotNull
  public List<Achievement> getAchievements() {
    return Collections.unmodifiableList(myAchievements);
  }

  @NotNull
  public Map<String, Achievement> getAchievementsMap() {
    Map<String, Achievement> res = new HashMap<String, Achievement>();
    for (Achievement a: myAchievements) {
      res.put(a.getId(), a);
    }
    return res;
  }

}
