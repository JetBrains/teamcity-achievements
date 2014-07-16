package org.jetbrains.buildserver.achievements.controller;

import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimplePageExtension;
import jetbrains.buildServer.web.util.SessionUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.impl.Achievement;
import org.jetbrains.buildserver.achievements.impl.AchievementsGrantor;

import javax.servlet.http.HttpServletRequest;
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
    List<Achievement> granted = myAchievementsGrantor.getGrantedAchievements(SessionUser.getUser(request));
    model.put("myAchievements", granted);
  }
}
