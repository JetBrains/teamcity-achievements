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
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class UserActionUtil {
  public static boolean checkUserActionsMadeBetween(@NotNull UserEvents events, @NotNull TimeZone userTimeZone, int minHour, int maxHour) {
    int numEvents = 3;
    List<Long> times = events.getEventTimes(AchievementEvents.userAction.name());
    if (times.size() < numEvents) return false;

    for (int i=0; i<numEvents; i++) {
      long timestamp = times.get(i);

      Calendar c = Calendar.getInstance();
      c.setTimeZone(userTimeZone);
      c.setTime(new Date(timestamp));
      int hour = c.get(Calendar.HOUR_OF_DAY);
      if (hour < minHour || hour > maxHour) return false;
    }

    return true;
  }

}
