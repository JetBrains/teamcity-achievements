

package org.jetbrains.buildserver.achievements.impl;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.util.Dates;
import jetbrains.buildServer.util.TimeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.buildserver.achievements.UserEvents;
import org.jfree.data.time.TimeSeries;

import java.util.*;

public abstract class UserEventsImpl implements UserEvents {
  private final static Logger LOG = Logger.getInstance(UserEventsImpl.class.getName());
  private Map<String, ArrayList<Long>> myEventLog = new HashMap<String, ArrayList<Long>>(); // event name => list of timestamps when event happened
  private final TimeService myTimeService;

  protected UserEventsImpl(@NotNull TimeService timeService) {
    myTimeService = timeService;
  }

  public synchronized void registerEvent(@NotNull String eventName) {
    ArrayList<Long> eventLog = myEventLog.get(eventName);
    if (eventLog == null) {
      eventLog = new ArrayList<Long>();
      myEventLog.put(eventName, eventLog);
    }

    long mostRecentEvent = myTimeService.now();
    long oldestEvent = mostRecentEvent - Dates.ONE_DAY;
    eventLog.add(mostRecentEvent);

    Iterator<Long> it = eventLog.iterator();
    while (it.hasNext()) {
      if (it.next() > oldestEvent) continue;
      it.remove();
    }

    eventLog.trimToSize();

    LOG.debug("New event registered: " + eventName + ", num events: " + eventLog.size());
  }

  public synchronized long getLastEventTime(@NotNull String eventName) {
    ArrayList<Long> eventLog = myEventLog.get(eventName);
    return eventLog == null || eventLog.isEmpty() ? -1 : eventLog.get(eventLog.size()-1);
  }

  @NotNull
  public synchronized List<Long> getEventTimes(@NotNull String eventName) {
    ArrayList<Long> eventLog = myEventLog.get(eventName);
    if (eventLog == null) return Collections.emptyList();
    ArrayList<Long> result = new ArrayList<Long>(eventLog);
    Collections.reverse(result);
    return result;
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