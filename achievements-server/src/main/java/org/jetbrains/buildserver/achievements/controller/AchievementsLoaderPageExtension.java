package org.jetbrains.buildserver.achievements.controller;

import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimplePageExtension;
import org.jetbrains.annotations.NotNull;

public class AchievementsLoaderPageExtension extends SimplePageExtension {
  public AchievementsLoaderPageExtension(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor pluginDescriptor) {
    super(pagePlaces);
    setPluginName(pluginDescriptor.getPluginName());
    setPlaceId(PlaceId.BEFORE_CONTENT);
    setIncludeUrl(pluginDescriptor.getPluginResourcesPath("/achievementsLoader.jsp"));
    addCssFile(pluginDescriptor.getPluginResourcesPath("/achievements.css"));
    register();
  }
}
