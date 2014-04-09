package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.BuildProblemTypes;
import jetbrains.buildServer.responsibility.ResponsibilityEntry;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.mute.MuteInfo;
import jetbrains.buildServer.serverSide.problems.BuildProblem;
import jetbrains.buildServer.serverSide.problems.BuildProblemInfo;
import jetbrains.buildServer.tests.TestName;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.vcs.SVcsModification;
import jetbrains.buildServer.vcs.VcsModification;
import jetbrains.buildServer.vcs.VcsRoot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.buildserver.achievements.AchievementEvents;
import org.jetbrains.buildserver.achievements.UserEventsListener;
import org.jetbrains.buildserver.achievements.UserEventsRegistry;

import java.util.Collection;
import java.util.List;

public class ServerEventsAdapter extends BuildServerAdapter {
  private final UserEventsRegistry myUserEventsRegistry;
  private final EventDispatcher<UserEventsListener> myEventDispatcher = EventDispatcher.create(UserEventsListener.class);

  public ServerEventsAdapter(@NotNull UserEventsRegistry userEventsRegistry, @NotNull EventDispatcher<BuildServerListener> serverDispatcher) {
    myUserEventsRegistry = userEventsRegistry;
    serverDispatcher.addListener(this);
  }

  @Override
  public void buildUnpinned(@NotNull SBuild build, @Nullable User user, @Nullable String comment) {
    super.buildUnpinned(build, user, comment);

    if (user != null) {
      registerUserEvent(user, AchievementEvents.buildUnpinned.name());
    }
  }

  @Override
  public void testsMuted(@NotNull MuteInfo muteInfo) {
    super.testsMuted(muteInfo);

    User user = muteInfo.getMutingUser();
    if (user == null) return;

    if (muteInfo.getAutoUnmuteOptions().getUnmuteByTime() == null) return;

    for (STest ignored : muteInfo.getTests()) {
      registerUserEvent(user, AchievementEvents.testBombed.name());
    }
  }

  @Override
  public void responsibleChanged(@NotNull SProject project, @NotNull Collection<BuildProblemInfo> buildProblems, @Nullable ResponsibilityEntry entry) {
    super.responsibleChanged(project, buildProblems, entry);

    if (entry == null) return;
    if (!notifyInvestigationAssigned(entry)) return;

    User responsible = entry.getResponsibleUser();

    for (BuildProblemInfo problem: buildProblems) {
      if (problem instanceof BuildProblem) {
        BuildProblem bp = (BuildProblem) problem;
        if (BuildProblemTypes.TC_COMPILATION_ERROR_TYPE.equals(bp.getBuildProblemData().getType())) {
          registerUserEvent(responsible, AchievementEvents.compilationBroken.name());
          break;
        }
      }
    }
  }

  @Override
  public void responsibleChanged(@NotNull SProject project, @NotNull Collection<TestName> testNames, @NotNull ResponsibilityEntry entry, boolean isUserAction) {
    super.responsibleChanged(project, testNames, entry, isUserAction);

    notifyInvestigationAssigned(entry);
  }

  @Override
  public void responsibleChanged(@NotNull SBuildType bt, @NotNull ResponsibilityEntry oldValue, @NotNull ResponsibilityEntry newValue) {
    super.responsibleChanged(bt, oldValue, newValue);

    notifyInvestigationAssigned(newValue);
  }

  @Override
  public void changeAdded(@NotNull VcsModification modification, @NotNull VcsRoot root, @Nullable Collection<SBuildType> buildTypes) {
    super.changeAdded(modification, root, buildTypes);

    SVcsModification mod = (SVcsModification) modification;

    if (!mod.getRelatedIssues().isEmpty()) {
      for (SUser committer: mod.getCommitters()) {
        registerUserEvent(committer, AchievementEvents.bugFixed.name());
      }
    }
  }

  @Override
  public void buildTagsChanged(@NotNull SBuild build, User user, @NotNull List<String> oldTags, @NotNull List<String> newTags) {
    super.buildTagsChanged(build, user, oldTags, newTags);

    if (user != null && !newTags.isEmpty()) {
      registerUserEvent(user, AchievementEvents.buildTagged.name());
    }
  }

  private boolean notifyInvestigationAssigned(@NotNull ResponsibilityEntry entry) {
    User responsible = entry.getResponsibleUser();
    User reporter = entry.getReporterUser();
    if (reporter != null && reporter.getId() != responsible.getId()) {
      registerUserEvent(reporter, AchievementEvents.investigationAssigned.name());
      return true;
    }

    return false;
  }

  private void registerUserEvent(@NotNull User user, @NotNull String eventName) {
    myUserEventsRegistry.getUserEvents(user).registerEvent(eventName);
    myEventDispatcher.getMulticaster().userEventsPublished(user);
  }

  public void addListener(@NotNull UserEventsListener listener) {
    myEventDispatcher.addListener(listener);
  }

  public void removeListener(@NotNull UserEventsListener listener) {
    myEventDispatcher.removeListener(listener);
  }
}
