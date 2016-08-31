package com.pck.test4;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartBeat implements Job {

	private static int count = 0;

	private static final long startTimeInSeconds = System.currentTimeMillis() / 1000;

	private final static Logger logger = LoggerFactory.getLogger(HeartBeat.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		long currentTimeInSeconds = System.currentTimeMillis() / 1000;
		long timeSinceStartInSeconds = currentTimeInSeconds - startTimeInSeconds;

		Test4Environment t4Env = Test4Environment.getInstance();
		int environmentTime = t4Env.advanceEnvironmentTime();

		logger.debug("HeartBeat.execute: A heartbeat with count[" + count++ + "]; real time:[" + timeSinceStartInSeconds
				+ "]; env time:[" + environmentTime + "]");

	}

}
