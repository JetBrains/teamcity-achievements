package org.jetbrains.buildserver.achievements.controller;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.users.PluginPropertyKey;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.SessionUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.impl.Achievement;
import org.jetbrains.buildserver.achievements.impl.AchievementsGrantor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class GrantedAchievementsController extends BaseController {
  private final AchievementsGrantor myAchievementsGrantor;
  private final PluginDescriptor myPluginDescriptor;

  public GrantedAchievementsController(@NotNull AchievementsGrantor achievementsGrantor, @NotNull WebControllerManager controllerManager, @NotNull PluginDescriptor pluginDescriptor) {
    myAchievementsGrantor = achievementsGrantor;
    myPluginDescriptor = pluginDescriptor;
    controllerManager.registerController("/grantedAchievements.html", this);
  }

  @Nullable
  @Override
  protected ModelAndView doHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse httpServletResponse) throws Exception {
    final SUser user = SessionUser.getUser(request);

    List<Achievement> granted = myAchievementsGrantor.getGrantedAchievements(user);

    List<AchievementBean> beans = new ArrayList<AchievementBean>();
    for (Achievement a: granted) {
      AchievementBean ab = new AchievementBean(a);
      if (ab.isHidden(user)) continue;
      beans.add(ab);
    }

    ModelAndView mv = new ModelAndView(myPluginDescriptor.getPluginResourcesPath("/grantedAchievements.jsp"));
    mv.getModel().put("achievements", beans);
    return mv;
  }

  private boolean isHidden(@NotNull User user, @NotNull Achievement achievement) {
    return user.getBooleanProperty(makePropertyKey(achievement));
  }

  @NotNull
  private PluginPropertyKey makePropertyKey(@NotNull Achievement achievement) {
    return new PluginPropertyKey("achievements", "achievements", "achievement.hidden." + achievement.getId());
  }
}
