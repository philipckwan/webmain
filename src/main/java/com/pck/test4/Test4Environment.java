package com.pck.test4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test4Environment {
	private final static Logger logger = LoggerFactory.getLogger(Test4Environment.class);
	private int environmentTime = 0;
	private int environmentTimeIncrement = 0;
	private Map<Integer, List<EnvironmentEvent>> environmentQueue = new HashMap<Integer, List<EnvironmentEvent>>();
	private List<EnvironmentEvent> lastEvents = null;

	private static Test4Environment instance = new Test4Environment();

	// This class is a singleton
	private Test4Environment() {
		logger.debug("Test4Environment.Test4Environment();");
	}

	public static Test4Environment getInstance() {
		return instance;
	}

	public void setEnvironmentTimeIncrement(int incrementTime) {
		this.environmentTimeIncrement = incrementTime;
	}

	/*
	private int getNextEnvironmentTime() {
		return getNextEnvironmentTime(environmentTimeIncrement);
	}
	*/

	private int getNextEnvironmentTime(int preAdvanceTime) {
		this.environmentTime += preAdvanceTime;
		return this.getEnvironmentTime();
	}

	private int getEnvironmentTime() {
		return this.environmentTime;
	}

	public int advanceEnvironmentTime() {
		return this.advanceEnvironmentTime(this.environmentTimeIncrement);
	}

	public int advanceEnvironmentTime(int incrementTime) {
		int oldEnvTime = this.getEnvironmentTime();
		int newEnvTime = this.getNextEnvironmentTime(incrementTime);

		for (int time = oldEnvTime + 1; time <= newEnvTime; time++) {
			List<EnvironmentEvent> eventsAtThisTime = environmentQueue.remove(time);
			if (eventsAtThisTime != null && !eventsAtThisTime.isEmpty()) {
				//System.out.println("Test4Environment.advanceEnvironmentTime: Event(s) fired at envTime: [" + time
				//		+ "], START -------");
				lastEvents = eventsAtThisTime;
				for (EnvironmentEvent anEvent : eventsAtThisTime) {
					logger.debug(
							"---time:" + anEvent.getEventTime() + "; event:" + anEvent.getEventDescription() + ";");
				}
				//System.out.println("Test4Environment.advanceEnvironmentTime: Event(s) fired at envTime: [" + time
				//		+ "], END -------");
				logger.debug("environmentQueue - number of pending eventTime:" + environmentQueue.size() + ";");
			}

		}

		//System.out.println("%%% Time is at:" + newEnvTime + ";");

		return newEnvTime;
	}

	public String lastEventsToString() {
		String results = "";
		if (lastEvents != null) {
			int count = 0;
			for (EnvironmentEvent anEvent : lastEvents) {
				count++;
				results += "Event [" + count + "]; time:" + anEvent.getEventTime() + "; event: "
						+ anEvent.getEventDescription() + ";\n";
			}
		}
		return results;
	}

	public void addEvent(int eventEnvTime, String description) {
		// cannot add event if envTime has already passed
		if (eventEnvTime <= environmentTime) {
			logger.debug(
					"Test4Environment.addEvent: ERROR - cannot add event with eventEnvTime <= environmentTime; eventEnvTime:["
							+ eventEnvTime + "], environmentTime:[" + environmentTime + "]");
			return;
		}

		List<EnvironmentEvent> eventsAtThisTime = environmentQueue.get(eventEnvTime);
		if (eventsAtThisTime == null) {
			eventsAtThisTime = new ArrayList<EnvironmentEvent>();
			environmentQueue.put(eventEnvTime, eventsAtThisTime);
		}
		eventsAtThisTime.add(new EnvironmentEvent(eventEnvTime, description));
	}

}
