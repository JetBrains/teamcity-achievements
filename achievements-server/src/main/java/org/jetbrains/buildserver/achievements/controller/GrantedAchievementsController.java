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

package org.jetbrains.buildserver.achievements.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.util.Dates;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jetbrains.buildserver.achievements.UserEventsRegistry;
import org.jetbrains.buildserver.achievements.impl.Achievement;
import org.jetbrains.buildserver.achievements.impl.AchievementsGrantor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GrantedAchievementsController extends BaseController {
  private final AchievementsGrantor myAchievementsGrantor;
  private final PluginDescriptor myPluginDescriptor;
  private final UserEventsRegistry myUserEventsRegistry;

  public GrantedAchievementsController(@NotNull AchievementsGrantor achievementsGrantor,
                                       @NotNull WebControllerManager controllerManager,
                                       @NotNull PluginDescriptor pluginDescriptor,
                                       @NotNull UserEventsRegistry userEventsRegistry) {
    myAchievementsGrantor = achievementsGrantor;
    myPluginDescriptor = pluginDescriptor;
    myUserEventsRegistry = userEventsRegistry;
    controllerManager.registerController("/grantedAchievements.html", this);
  }

  @Nullable
  @Override
  protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse httpServletResponse) throws Exception {
    final SUser user = SessionUser.getUser(request);
    if (user == null) return simpleView("User not found");

    if (request.getParameter("userAction") != null) {
      reportUserAction(user, request);
      return null;
    }

    List<Achievement> granted = myAchievementsGrantor.getGrantedAchievements(user);

    List<AchievementBean> newAchievements = new ArrayList<AchievementBean>();
    for (Achievement a: granted) {
      AchievementBean ab = new AchievementBean(a);
      if (ab.isHidden(user)) continue;
      newAchievements.add(ab);
    }

    ModelAndView mv = new ModelAndView(myPluginDescriptor.getPluginResourcesPath("/grantedAchievements.jsp"));
    mv.getModel().put("newAchievements", newAchievements);
    return mv;
  }

  private void reportUserAction(@NotNull SUser user, @NotNull HttpServletRequest request) {
    UserEvents events = myUserEventsRegistry.getUserEvents(user);
    long lastEventTime = events.getLastEventTime(AchievementEvents.userAction.name());
    if (lastEventTime == -1 || new Date().getTime() - lastEventTime > 5 * Dates.ONE_MINUTE) { // 5 minutes resolution
      TimeZone tz = SessionUser.getUserTimeZone(request);
      events.registerEvent(AchievementEvents.userAction.name(), tz);
    }
  }
}
