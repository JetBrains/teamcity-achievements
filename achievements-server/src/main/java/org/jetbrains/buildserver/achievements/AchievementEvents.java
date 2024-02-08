

package org.jetbrains.buildserver.achievements;

public enum AchievementEvents {
  buildUnpinned,
  buildTagged,
  testBombed,
  testDisarmed,
  compilationBroken,
  investigationDelegated,
  investigationTaken,
  issueMentioned,
  changeAdded,
  userAction // with 5 minutes resolution
}