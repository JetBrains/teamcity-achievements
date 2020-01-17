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

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementsConfig;
import org.jetbrains.buildserver.achievements.impl.Achievement;
import org.jetbrains.buildserver.achievements.impl.AchievementsGrantor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RevokeAchievementsController extends BaseController {
  private final AchievementsConfig myConfig;
  private final AchievementsGrantor myAchievementsGrantor;

  public RevokeAchievementsController(@NotNull AchievementsConfig config,
                                      @NotNull AchievementsGrantor achievementsGrantor,
                                      @NotNull WebControllerManager controllerManager) {
    myConfig = config;
    myAchievementsGrantor = achievementsGrantor;
    controllerManager.registerController("/revokeAchievement.html", this);
  }

  @Nullable
  @Override
  protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    List<String> toReset = new ArrayList<String>();
    if ("__all__".equals(id)) {
      for (Achievement a: myConfig.getAchievements()) {
        toReset.add(a.getId());
      }
    } else {
      if (myConfig.getAchievementsMap().containsKey(id)) {
        toReset.add(id);
      }
    }

    for (String aid: toReset) {
      Achievement achievement = myConfig.getAchievementsMap().get(aid);
      if (achievement != null) {
        myAchievementsGrantor.revokeAchievement(SessionUser.getUser(request), achievement);
      }
    }

    return simpleView("Revoked: " + toReset);
  }
}
