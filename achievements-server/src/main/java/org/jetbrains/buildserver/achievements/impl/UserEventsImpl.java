package org.jetbrains.buildserver.achievements.impl;

import jetbrains.buildServer.util.Dates;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.UserEvents;

import java.util.*;

class UserEventsImpl implements UserEvents {
  private Map<String, ArrayList<Long>> myEventLog = new HashMap<String, ArrayList<Long>>(); // event name => list of timestamps when event happened

  public synchronized void registerEvent(@NotNull String eventName) {
    ArrayList<Long> eventLog = myEventLog.get(eventName);
    if (eventLog == null) {
      eventLog = new ArrayList<Long>();
      myEventLog.put(eventName, eventLog);
    }

    long mostRecentEvent = new Date().getTime();
    long oldestEvent = mostRecentEvent - Dates.ONE_DAY;
    eventLog.add(mostRecentEvent);

    Iterator<Long> it = eventLog.iterator();
    while (it.hasNext()) {
      if (it.next() > oldestEvent) continue;
      it.remove();
    }

    eventLog.trimToSize();

    System.out.println("New event: " + eventName + ", num events: " + eventLog.size());
  }

  public synchronized int getNumberOfEvents(@NotNull String eventName) {
    ArrayList<Long> eventLog = myEventLog.get(eventName);
    if (eventLog == null) {
      return 0;
    }

    return eventLog.size();
  }

  public synchronized float getEventsRate(@NotNull String eventName, int timeIntervalSeconds) {
    ArrayList<Long> eventLog = myEventLog.get(eventName);
    if (eventLog == null || eventLog.isEmpty()) {
      return 0;
    }

    int count = 1;
    Long startTime = eventLog.get(eventLog.size() - 1);

    for (int i = eventLog.size() - 2; i >= 0; i--) {
      Long time = eventLog.get(i);
      if (time - startTime > timeIntervalSeconds) break;
      count++;
    }

    return 1.0f * count / timeIntervalSeconds;
  }
}
