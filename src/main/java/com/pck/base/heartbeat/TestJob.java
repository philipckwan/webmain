package com.pck.base.heartbeat;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJob implements Job {

	private final static Logger logger = LoggerFactory.getLogger(TestJob.class);

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.debug("TestJob.execute: 1.0");
	}

}
