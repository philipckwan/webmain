package com.pck.test4;

public class EnvironmentEvent {

	private int eventTime;
	private String eventDescription;

	public EnvironmentEvent(String description) {
		this(-1, description);
	}

	public EnvironmentEvent(int time, String description) {
		this.eventTime = time;
		this.eventDescription = description;
	}

	public int getEventTime() {
		return eventTime;
	}

	public void setEventTime(int eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
}
