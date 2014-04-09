package org.jetbrains.buildserver.achievements;

public enum AchievementEvents {
  buildUnpinned,
  buildTagged,
  testBombed,
  testDisarmed,
  compilationBroken,
  investigationDelegated,
  investigationTaken,
  bugFixed,
  longCommentAdded,
  userAction // with 10 minutes resolution
}
