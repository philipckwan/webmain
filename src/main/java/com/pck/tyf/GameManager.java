package com.pck.tyf;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pck.tyf.Board.Type;
import com.pck.tyf.player.RealPlayer;
import com.pck.tyf.player.ReasonAIPlayer;

public enum GameManager {
	INSTANCE;

	private final static Logger logger = LoggerFactory.getLogger(GameManager.class);

	private Map<String, String> status;
	private Map<String, Game> games;

	GameManager() {
		status = new HashMap<String, String>();
		status.put("name", "TYF");
		status.put("version", "0.2");
		status.put("status", "ready");

		games = new HashMap<String, Game>();
	}

	public static GameManager getInstance() {
		return INSTANCE;
	}

	public Map<String, String> getStatus() {
		logger.debug("getStatus: 1.0");
		return status;
	}

	public String newGame() {
		Game game = new Game();

		Random rand = new Random();
		String gameId = Integer.toString(1 + rand.nextInt(999));
		while (games.containsKey(gameId)) {
			gameId = Integer.toString(1 + rand.nextInt(999));
		}

		games.put(gameId, game);

		// @formatter:off
		String[] board = {"XXXXXXXXXXXXXXXX".toUpperCase(),
				  		  "XS           X X".toUpperCase(),
				  		  "X X X X X X XX X".toUpperCase(),
				  		  "X              X".toUpperCase(),
				  		  "X X X X X X X  X".toUpperCase(),
				  		  "X X X X X X X  X".toUpperCase(),
				  		  "X              X".toUpperCase(),
				  		  "X X X X X X X  X".toUpperCase(),
				  		  "X              X".toUpperCase(),
				  		  "X X X X X X X  X".toUpperCase(),
				  		  "X              X".toUpperCase(),
				  		  "XXXXXXXXXXXXXXXX".toUpperCase()};
		// @formatter:on

		Board level1 = new Board();
		level1.createBoard(board);
		game.setBoard(level1);

		Position start = level1.getStartPosition();
		Actor player1 = new Actor("1", start.x, start.y);
		game.addActor(player1);
		game.addPlayer(new RealPlayer(), player1);

		int numAIPlayers = 4;
		Board gameBoard = game.getBoard();
		Type[][] theBoard = gameBoard.getBoard();
		int maxX = theBoard.length - 1;
		int maxY = theBoard[0].length - 1;
		for (int i = 0; i < numAIPlayers; i++) {
			Position newAIPos;
			do {
				newAIPos = new Position(rand.nextInt(maxX), rand.nextInt(maxY));
			} while (gameBoard.getTypeFromBoard(newAIPos) != Type.O);
			int newAIID = i + 2;
			Actor newAIActor = new Actor(Integer.toString(newAIID), newAIPos.x, newAIPos.y);
			game.addActor(newAIActor);
			game.addPlayer(new ReasonAIPlayer(false), newAIActor);
		}

		/*
		Actor player2 = new Actor("2", start.x + 12, start.y + 9);
		game.addActor(player2);
		game.addPlayer(new ReasonAIPlayer(true), player2);
		
		
		Actor player7 = new Actor("7", start.x + 7, start.y + 5);
		game.addActor(player7);
		game.addPlayer(new ReasonAIPlayer(false), player7);
		*/

		return gameId;
	}

	public Game getGame(String gameId) {
		return games.get(gameId);
	}

}
