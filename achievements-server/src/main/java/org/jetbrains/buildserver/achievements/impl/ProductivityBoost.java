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
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.vcs.SVcsModification;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.UserEventsListener;
import org.jetbrains.buildserver.achievements.UserEventsRegistry;

import java.util.Calendar;
import java.util.Date;

public class ProductivityBoost extends SimpleAchievement {
  public ProductivityBoost(@NotNull final UserEventsRegistry userEventsRegistry) {
    super(AchievementEvents.changeAdded.name(), 20);
    userEventsRegistry.addListener(new UserEventsListener() {
      public void userEventPublished(@NotNull User user, @NotNull String eventName, @Nullable Object additionalData) {
        if (!AchievementEvents.changeAdded.name().equals(eventName)) return;
        if (!(additionalData instanceof SVcsModification)) return;

        SVcsModification mod = (SVcsModification) additionalData;
        Date vcsDate = mod.getVcsDate();
        if (Calendar.getInstance().getTime().getTime() - vcsDate.getTime() < 24 * 3600 * 1000) {
          userEventsRegistry.getUserEvents(user).registerEvent(getId() + ":changeAdded");
        }
      }
    });
  }

  @NotNull
  public String getId() {
    return "productivityBoost";
  }

  @NotNull
  public String getName() {
    return "Productivity Boost";
  }

  @NotNull
  public String getDescription() {
    return "Granted for making a lot of commits during the day.";
  }

  @Nullable
  public String getIconClassNames() {
    return "icon-thumbs-up";
  }

  @Override
  public boolean shouldGrantAchievement(@NotNull SUser user, @NotNull UserEvents events, @NotNull String lastEventName, @Nullable Object additionalData) {
    String watchedEvent = getId() + ":changeAdded";
    return events.getNumberOfEvents(watchedEvent) >= 20;
  }
}
