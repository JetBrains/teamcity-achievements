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

package org.jetbrains.buildserver.achievements.controller;

import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;
import jetbrains.buildServer.web.util.SessionUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.impl.Achievement;
import org.jetbrains.buildserver.achievements.AchievementsConfig;
import org.jetbrains.buildserver.achievements.impl.AchievementsGrantor;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class MyAchievementsTab extends SimpleCustomTab {
  private static final String TAB_TITLE = "My Achievements";
  private final AchievementsGrantor myAchievementsGrantor;
  private final AchievementsConfig myAchievementsConfig;

  public MyAchievementsTab(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor pluginDescriptor, @NotNull AchievementsGrantor achievementsGrantor, @NotNull AchievementsConfig achievementsConfig) {
    super(pagePlaces);
    setPluginName(pluginDescriptor.getPluginName());
    setPlaceId(PlaceId.MY_TOOLS_TABS);
    setIncludeUrl(pluginDescriptor.getPluginResourcesPath("/myAchievements.jsp"));
    setTabTitle(TAB_TITLE);
    register();

    myAchievementsConfig = achievementsConfig;
    myAchievementsGrantor = achievementsGrantor;
  }

  @NotNull
  @Override
  public String getTabTitle(@NotNull HttpServletRequest request) {
    final SUser user = SessionUser.getUser(request);
    List<Achievement> granted = myAchievementsGrantor.getGrantedAchievements(user);
    return TAB_TITLE + (granted.isEmpty() ? "" : "<span class=\"tabCounter\">" + granted.size() + "</span>");
  }

  @Override
  public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
    super.fillModel(model, request);
    final SUser user = SessionUser.getUser(request);

    List<Achievement> granted = myAchievementsGrantor.getGrantedAchievements(user);

    List<Achievement> available = new ArrayList<Achievement>();
    available.addAll(myAchievementsConfig.getAchievements());
    available.removeAll(granted);

    model.put("grantedAchievements", granted);
    model.put("availableAchievements", available);
    model.put("allAchievementsMap", myAchievementsGrantor.getAchievementUsersMap());
    model.put("achievementsEnabled", myAchievementsGrantor.isEnabled(user));
  }
}
