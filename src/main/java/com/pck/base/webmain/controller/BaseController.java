package com.pck.base.webmain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pck.base.webmain.common.ApplicationContext;
import com.pck.base.webmain.common.RegisterUser;
import com.pck.base.webmain.common.User;
import com.pck.base.webmain.service.UserService;

@Controller
public class BaseController {

	private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/status")
	public @ResponseBody String getStatus(ModelMap model) {
		logger.debug("BaseController.getStatus: 1.0");

		Gson gson = new Gson();
		String response = gson.toJson(new ApplicationContext());
		return response;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestBody RegisterUser registerUser) {
		logger.debug("BaseController.register: 1.0");

		User user = userService.registerUser(registerUser);

		Gson gson = new Gson();
		String response = gson.toJson(user);
		return response;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody String getUserStatus(@RequestBody User request) {
		logger.debug("BaseController.getUserStatus: 1.1");

		User user = userService.getUser(request);

		Gson gson = new Gson();
		String response = gson.toJson(user);
		return response;

	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public @ResponseBody String getUsers(@RequestBody String secret) {
		logger.debug("BaseController.getUsers: 1.1");
		secret = secret.trim();
		logger.debug("__secret:" + secret + ";");
		List<User> users = userService.getUsers(secret);

		Gson gson = new Gson();
		String response = gson.toJson(users);
		return response;
	}

}