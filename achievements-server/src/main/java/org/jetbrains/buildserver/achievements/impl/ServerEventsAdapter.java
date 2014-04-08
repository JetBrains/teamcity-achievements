package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.STest;
import jetbrains.buildServer.serverSide.mute.MuteInfo;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEventsListener;
import org.jetbrains.buildserver.achievements.UserEventsRegistry;

public class ServerEventsAdapter extends BuildServerAdapter {
  private final UserEventsRegistry myUserEventsRegistry;
  private final EventDispatcher<UserEventsListener> myEventDispatcher = EventDispatcher.create(UserEventsListener.class);

  public ServerEventsAdapter(@NotNull UserEventsRegistry userEventsRegistry) {
    myUserEventsRegistry = userEventsRegistry;
  }

  @Override
  public void buildUnpinned(@NotNull SBuild build, @Nullable User user, @Nullable String comment) {
    super.buildUnpinned(build, user, comment);

    if (user != null) {
      myUserEventsRegistry.getUserEvents(user).registerEvent(AchievementEvents.buildUnpinned.name());

      myEventDispatcher.getMulticaster().userEventsPublished(user);
    }
  }

  @Override
  public void testsMuted(@NotNull MuteInfo muteInfo) {
    super.testsMuted(muteInfo);

    User user = muteInfo.getMutingUser();
    if (user == null) return;

    if (muteInfo.getAutoUnmuteOptions().getUnmuteByTime() == null) return;

    for (STest ignored : muteInfo.getTests()) {
      myUserEventsRegistry.getUserEvents(user).registerEvent(AchievementEvents.testBombed.name());
    }

    myEventDispatcher.getMulticaster().userEventsPublished(user);
  }

  public void addListener(@NotNull UserEventsListener listener) {
    myEventDispatcher.addListener(listener);
  }

  public void removeListener(@NotNull UserEventsListener listener) {
    myEventDispatcher.removeListener(listener);
  }
}
