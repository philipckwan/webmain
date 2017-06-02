package com.pck.base.webmain.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pck.tyf.Game;
import com.pck.tyf.GameManager;

@Controller
@RequestMapping(value = "/tyf")
public class TYFController {

	private final static Logger logger = LoggerFactory.getLogger(TYFController.class);

	@RequestMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getStatus() {
		logger.debug("getStatus: 1.0");

		GameManager gm = GameManager.getInstance();

		Gson gson = new Gson();
		String response = gson.toJson(gm.getStatus());
		return response;
	}

	@RequestMapping(value = "/game", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String newGame() {
		logger.debug("newGame: 1.0");

		GameManager gm = GameManager.getInstance();
		String gameId = gm.newGame();

		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("gameId", gameId);
		Gson gson = new Gson();
		String response = gson.toJson(responseMap);
		return response;
	}

	@RequestMapping(value = "/game/{gameId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getGame(@PathVariable String gameId) {
		GameManager gm = GameManager.getInstance();
		Game game = gm.getGame(gameId);

		String[] gameBoard = game.getGameBoardArray();
		Gson gson = new Gson();
		String response = gson.toJson(gameBoard);
		return response;

	}

	@RequestMapping(value = "/game/{gameId}/{command}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String makeMove(@PathVariable String gameId, @PathVariable String command) {
		GameManager gm = GameManager.getInstance();
		Game game = gm.getGame(gameId);

		game.makeMove(command);

		String[] gameBoard = game.getGameBoardArray();
		Map<String, Object> gameStatus = new HashMap<String, Object>();
		gameStatus.put("gameBoard", gameBoard);
		gameStatus.put("status", "running");
		//gameStatus.add("status: running");
		Gson gson = new Gson();
		String response = gson.toJson(gameStatus);
		return response;
	}

}
