package com.pck.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimEngine {

	private final static Logger logger = LoggerFactory.getLogger(SimEngine.class);

	// runtime related - START
	private int engineTime = 0;
	private int engineTimeIncrement = 0;
	// runtime related - END

	// attributes
	private Wilderness wild;

	private static SimEngine instance = new SimEngine();

	// This class is a singleton
	private SimEngine() {
		logger.debug("SimEngine.SimEngine();");
	}

	public static SimEngine getInstance() {
		return instance;
	}

	// runtime related - START
	public void setEngineTimeIncrement(int engineTimeIncrement) {
		this.engineTimeIncrement = engineTimeIncrement;
	}

	public int getEngineTime() {
		return engineTime;
	}

	public int advanceEngineTime() {
		return this.advaceEngineTime(this.engineTimeIncrement);
	}

	private int advaceEngineTime(int incrementTime) {
		int oldEngineTime = this.engineTime;
		int newEngineTime = this.engineTime + incrementTime;

		// TODO: Check the eventQueue here to see any events occur between oldEngineTime and newEngineTime

		this.engineTime = newEngineTime;
		return this.engineTime;
	}
	// runtime related - END

	public Wilderness getWild() {
		return wild;
	}

	public void setWild(Wilderness wild) {
		this.wild = wild;
	}

}
