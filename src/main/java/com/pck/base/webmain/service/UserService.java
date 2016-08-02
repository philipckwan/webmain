package com.pck.base.webmain.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pck.base.webmain.common.RegisterUser;
import com.pck.base.webmain.common.User;

@Service
public class UserService {

	private String adminSecret = "happy";

	private Map<String, User> registeredUsers = new HashMap<String, User>();

	public User registerUser(RegisterUser request) {
		User resultUser = new User();

		resultUser.setUsername(request.getUsername());
		resultUser.setSecret(request.getSecret());
		resultUser.setDescription(request.getDescription());

		Random rand = new Random();
		String preID = Integer.toString(1 + rand.nextInt(999));

		while (registeredUsers.containsKey(preID)) {
			preID = Integer.toString(1 + rand.nextInt(999));
		}
		registeredUsers.put(preID, resultUser);
		resultUser.setUserID(preID);
		resultUser.setStatus("JUST REGISTERED");

		return resultUser;
	}

	public User getUser(User request) {
		User invalidUser = new User();

		if (StringUtils.isEmpty(request.getUserID()) || StringUtils.isEmpty(request.getSecret())) {
			invalidUser.setStatus("Error - invalid user request!");
			return invalidUser;
		}

		User checkUser = registeredUsers.get(request.getUserID());
		if (checkUser == null) {
			invalidUser.setStatus("Error - user does not existed!");
			return invalidUser;
		}

		if (!checkUser.getSecret().equals(request.getSecret())) {
			invalidUser.setStatus("Error - secret does not match!");
			return invalidUser;
		}

		if (checkUser.getStatus().equals("JUST REGISTERED")) {
			checkUser.setStatus("INITIALIZED");
		}
		return checkUser;
	}

	public List<User> getUsers(String requestAdminSecret) {
		List<User> users = null;

		if (adminSecret.equals(requestAdminSecret)) {
			users = new ArrayList<User>(registeredUsers.values());
		}

		return users;
	}
}
