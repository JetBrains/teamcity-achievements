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
import jetbrains.buildServer.web.openapi.SimplePageExtension;
import jetbrains.buildServer.web.util.SessionUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.impl.Achievement;
import org.jetbrains.buildserver.achievements.impl.AchievementsGrantor;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AchievementsLoaderPageExtension extends SimplePageExtension {
  private final AchievementsGrantor myAchievementsGrantor;

  public AchievementsLoaderPageExtension(@NotNull PagePlaces pagePlaces,
                                         @NotNull PluginDescriptor pluginDescriptor,
                                         @NotNull AchievementsGrantor achievementsGrantor) {
    super(pagePlaces);
    myAchievementsGrantor = achievementsGrantor;
    setPluginName(pluginDescriptor.getPluginName());
    setPlaceId(PlaceId.BEFORE_CONTENT);
    setIncludeUrl(pluginDescriptor.getPluginResourcesPath("/achievementsLoader.jsp"));
    addCssFile(pluginDescriptor.getPluginResourcesPath("/achievements.css"));
    register();
  }

  @Override
  public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
    super.fillModel(model, request);

    SUser user = SessionUser.getUser(request);
    boolean enabled = user != null && myAchievementsGrantor.isEnabled(user);

    if (enabled) {
      List<Achievement> granted = myAchievementsGrantor.getGrantedAchievements(SessionUser.getUser(request));
      model.put("myAchievements", granted);
    } else {
      model.put("myAchievements", Collections.emptyList());
    }
    model.put("myAchievementsEnabled", enabled);
  }
}
