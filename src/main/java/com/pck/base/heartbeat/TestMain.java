package com.pck.base.heartbeat;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMain {

	private static final int realTimeSecondsPerHeartBeat = 1;
	private static final int environmentTimeSecondsPerHeartBeat = 1;

	private final static Logger logger = LoggerFactory.getLogger(TestMain.class);

	public static void main(String[] args) {
		logger.debug("Test4Main.main: 1.0");

		HeartBeatEnvironment t4Env = HeartBeatEnvironment.getInstance();
		t4Env.setEnvironmentTimeIncrement(environmentTimeSecondsPerHeartBeat);
		t4Env.addEvent(9, "At 9b");
		t4Env.addEvent(4, "At 4");
		t4Env.addEvent(7, "At 7");
		t4Env.addEvent(14, "At 14");
		t4Env.addEvent(22, "At 22a");
		t4Env.addEvent(22, "At 22b");
		t4Env.addEvent(21, "At 21");
		t4Env.addEvent(23, "At 23");
		t4Env.addEvent(9, "At 9a");
		t4Env.addEvent(16, "At 16");

		/*
		t4Env.advanceEnvironmentTime(5);
		t4Env.advanceEnvironmentTime(1);
		t4Env.advanceEnvironmentTime(1);
		t4Env.advanceEnvironmentTime(1);
		t4Env.advanceEnvironmentTime(1);
		t4Env.advanceEnvironmentTime(1);
		t4Env.advanceEnvironmentTime(10);
		*/
		t4Env.addEvent(22, "At 22 ddd");
		t4Env.addEvent(21, "At 21 ccc");
		t4Env.addEvent(11, "At 11");
		//t4Env.advanceEnvironmentTime(99);

		JobDetail job = JobBuilder.newJob(HeartBeat.class).withIdentity("heartBeat").build();

		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(realTimeSecondsPerHeartBeat).repeatForever()).build();

		/*
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger", "cronTriggerGroup1")
				.withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?")).build();
		*/

		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			scheduler.start();

			scheduler.scheduleJob(job, trigger);

			//scheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
