

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