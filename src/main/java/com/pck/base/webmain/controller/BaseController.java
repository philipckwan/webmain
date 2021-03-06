package com.pck.base.webmain.controller;

import java.util.List;
import java.util.function.BiFunction;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pck.base.heartbeat.HeartBeat;
import com.pck.base.heartbeat.HeartBeatEnvironment;
import com.pck.base.webmain.common.APIResponse;
import com.pck.base.webmain.common.ApplicationContext;
import com.pck.base.webmain.common.RegisterUser;
import com.pck.base.webmain.common.User;
import com.pck.base.webmain.common.Util;
import com.pck.base.webmain.service.UserService;
import com.pck.tictactoe.AIPlayer;
import com.pck.tictactoe.Game;

@Controller
public class BaseController {

	private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	private static Game aGame = null;
	private static User player1 = null;
	private static User player2 = null;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getStatus() {
		logger.debug("BaseController.getStatus: 1.0");

		return Util.toGson(new ApplicationContext());
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String register(@RequestBody RegisterUser registerUser) {
		logger.debug("BaseController.register: 1.0");

		User user = userService.registerUser(registerUser);

		return Util.toGson(user);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getUserStatus(@RequestBody User request) {
		logger.debug("BaseController.getUserStatus: 1.1");

		User user = userService.getUser(request);

		return Util.toGson(user);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getUsers(@RequestBody String secret) {
		logger.debug("BaseController.getUsers: 1.1");
		secret = secret.trim();
		logger.debug("__secret:" + secret + ";");
		List<User> users = userService.getUsers(secret);

		return Util.toGson(users);
	}

	@RequestMapping(value = "/startGame", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String startGame() {
		aGame = new Game();

		player1 = new User();
		player1.setUserID("You");

		player2 = new User();
		player2.setUserID("Computer");

		aGame.setPlayer(player1);
		aGame.setPlayer(player2);

		return Util.toGson(aGame.boardToJson());
	}

	@RequestMapping(value = "/checkGame", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String checkGame() {

		return Util.toGson(aGame.boardToJson());

	}

	@RequestMapping(value = "/move/{position}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String makeAMove(@PathVariable String position) {
		int positionInt = Integer.parseInt(position);
		
		if (aGame == null) {
			return Util.toGson(APIResponse.newError("Game is not initialized yet"));
		}
		
		APIResponse response = APIResponse.newInstance();
		aGame.makeAMove(player1, positionInt, response);

		int npcMove = AIPlayer.getANextMove(aGame);
		aGame.makeAMove(player2, npcMove);

		return Util.toGson(aGame.boardToJson(), response);
	}

	@RequestMapping(value = "/startEnv")
	public @ResponseBody String startEnvironment() {
		HeartBeatEnvironment t4Env = HeartBeatEnvironment.getInstance();
		t4Env.setEnvironmentTimeIncrement(1);
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

		t4Env.addEvent(22, "At 22 ddd");
		t4Env.addEvent(21, "At 21 ccc");
		t4Env.addEvent(11, "At 11");

		JobDetail job = JobBuilder.newJob(HeartBeat.class).withIdentity("heartBeat").build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

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

		String results = "Test4 scheduled successfully";
		return results;
	}

	@RequestMapping(value = "/checkEnv")
	public @ResponseBody String checkEnvironment() {
		HeartBeatEnvironment t4Env = HeartBeatEnvironment.getInstance();
		String lastEventsStr = t4Env.lastEventsToString();
		return lastEventsStr;
	}

	@RequestMapping(value = "/testLambda/{str1}/{str2}")
	public @ResponseBody String testLambda(@PathVariable String str1, @PathVariable String str2) {
		logger.debug("BaseController.testLambda: 1.0");
		BiFunction<String, String, String> stringAdderWithExclamation = (String s1, String s2) -> s1 + s2 + "!";
		//IAddable<String> stringAdder = (String s1, String s2) -> s1 + s2;
		return stringAdderWithExclamation.apply(str1, str2);
	}

}