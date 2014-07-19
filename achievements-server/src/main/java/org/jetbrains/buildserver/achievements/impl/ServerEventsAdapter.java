package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.BuildProblemTypes;
import jetbrains.buildServer.issueTracker.Issue;
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
import org.jetbrains.buildserver.achievements.UserEventsRegistry;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ServerEventsAdapter extends BuildServerAdapter {
  private final UserEventsRegistry myUserEventsRegistry;

  public ServerEventsAdapter(@NotNull UserEventsRegistry userEventsRegistry,
                             @NotNull EventDispatcher<BuildServerListener> serverDispatcher) {
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
  public void testsUnmuted(@Nullable SUser user, @NotNull Map<MuteInfo, Collection<STest>> unmutedGroups) {
    super.testsUnmuted(user, unmutedGroups);

    if (user == null) return;

    for (MuteInfo mi: unmutedGroups.keySet()) {
      for (STest ignored : unmutedGroups.get(mi)) {
        registerUserEvent(user, AchievementEvents.testDisarmed.name());
      }
    }
  }

  @Override
  public void responsibleChanged(@NotNull SProject project, @NotNull Collection<BuildProblemInfo> buildProblems, @Nullable ResponsibilityEntry entry) {
    super.responsibleChanged(project, buildProblems, entry);

    if (entry == null) return;
    if (notifyInvestigationTaken(entry)) return;
    if (!notifyInvestigationDelegated(entry)) return;

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

    notifyInvestigationDelegated(entry);
    notifyInvestigationTaken(entry);
  }

  @Override
  public void responsibleChanged(@NotNull SBuildType bt, @NotNull ResponsibilityEntry oldValue, @NotNull ResponsibilityEntry newValue) {
    super.responsibleChanged(bt, oldValue, newValue);

    notifyInvestigationDelegated(newValue);
    notifyInvestigationTaken(newValue);
  }

  @Override
  public void changeAdded(@NotNull VcsModification modification, @NotNull VcsRoot root, @Nullable Collection<SBuildType> buildTypes) {
    super.changeAdded(modification, root, buildTypes);

    SVcsModification mod = (SVcsModification) modification;

    for (Issue ignored: mod.getRelatedIssues()) {
      for (SUser committer: mod.getCommitters()) {
        registerUserEvent(committer, AchievementEvents.issueMentioned.name());
      }
    }

    for (SUser committer: mod.getCommitters()) {
      registerUserEvent(committer, AchievementEvents.changeAdded.name(), mod);
    }
  }

  @Override
  public void buildTagsChanged(@NotNull SBuild build, User user, @NotNull List<String> oldTags, @NotNull List<String> newTags) {
    super.buildTagsChanged(build, user, oldTags, newTags);

    if (user != null && !newTags.isEmpty()) {
      registerUserEvent(user, AchievementEvents.buildTagged.name());
    }
  }

  private boolean notifyInvestigationDelegated(@NotNull ResponsibilityEntry entry) {
    User responsible = entry.getResponsibleUser();
    User reporter = entry.getReporterUser();
    if (reporter != null && reporter.getId() != responsible.getId()) {
      registerUserEvent(reporter, AchievementEvents.investigationDelegated.name());
      return true;
    }

    return false;
  }

  private boolean notifyInvestigationTaken(@NotNull ResponsibilityEntry entry) {
    User responsible = entry.getResponsibleUser();
    User reporter = entry.getReporterUser();
    if (reporter != null && reporter.getId() == responsible.getId()) {
      registerUserEvent(reporter, AchievementEvents.investigationTaken.name());
      return true;
    }

    return false;
  }

  private void registerUserEvent(@NotNull User user, @NotNull String eventName) {
    myUserEventsRegistry.getUserEvents(user).registerEvent(eventName);
  }

  private void registerUserEvent(@NotNull User user, @NotNull String eventName, @NotNull Object additionalData) {
    myUserEventsRegistry.getUserEvents(user).registerEvent(eventName, additionalData);
  }
}
