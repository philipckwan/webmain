package com.pck.tyf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.pck.tyf.Board.Type;
import com.pck.tyf.player.AIPlayer;
import com.pck.tyf.player.Player;
import com.pck.tyf.player.RealPlayer;

public class Game {
	private final static Logger logger = LoggerFactory.getLogger(Game.class);

	public enum Direction {
		North("N"), East("E"), South("S"), West("W");

		private final String display;

		Direction(String display) {
			this.display = display;
		}

		@Override
		public String toString() {
			return this.display;
		}

		public static Direction parse(String direction) {
			if (North.display.equalsIgnoreCase(direction)) {
				return North;
			}
			if (East.display.equalsIgnoreCase(direction)) {
				return East;
			}
			if (South.display.equalsIgnoreCase(direction)) {
				return South;
			}
			if (West.display.equalsIgnoreCase(direction)) {
				return West;
			}
			return null;
		}
	}

	private Board board;
	private Map<String, Actor> actors = new HashMap<String, Actor>();
	private Map<Player, Actor> players = new HashMap<Player, Actor>();

	public Game() {

	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return this.board;
	}

	public void addActor(Actor actor) {
		actors.put(actor.getId(), actor);
	}

	private Position checkMove(final String actorId, final Direction direction) {
		Actor thisActor = actors.get(actorId);
		return checkMove(thisActor, direction);
	}

	public Position checkMove(final Actor thisActor, final Direction direction) {
		logger.debug("checkMove2: 1.0;");
		if (thisActor == null) {
			logger.warn("checkMove2: ERROR - invalid arguments;");
			return null;
		}

		return checkMove(thisActor.getPosition(), direction);

	}

	private Position checkMove(final Position from, final Direction direction) {
		logger.debug("checkMove3: 1.0;");
		if (from == null || StringUtils.isEmpty(direction)) {
			logger.warn("checkMove3: ERROR - invalid arguments;");
			return null;
		}

		Position to = updatePosition(from, direction);

		if (to == null) {
			logger.debug("checkMove3: invalid move, updatePosition returns null;");
			return null;
		}

		if (board.getBoard()[to.x][to.y] == Type.X) {
			logger.debug("checkMove3: invalid move, Position to is of Type.X;");
			return null;
		}

		return to;
	}

	private Position updatePosition(Position from, Direction direction) {
		logger.debug("updatePosition: 1.0;");
		if (from == null || direction == null) {
			logger.warn("updatePosition: ERROR - invalid arguments;");
			return null;
		}

		Position to = new Position(from);

		switch (direction) {
		case North:
			to.y--;
			break;
		case East:
			to.x++;
			break;
		case South:
			to.y++;
			break;
		case West:
			to.x--;
			break;
		default:
			logger.warn("updatePosition: ERROR - invalid direction:[" + direction + "];");
			to = null;
			break;
		}

		return to;
	}

	public List<Direction> listAvailableMoves(Position from) {
		List<Direction> availableMoves = new ArrayList<Direction>();

		Direction[] allDirections = Direction.values();
		for (Direction aDirection : allDirections) {
			if (checkMove(from, aDirection) != null) {
				availableMoves.add(aDirection);
			}
		}

		return availableMoves;
	}

	private boolean moveAnActor(String actorId, String direction) {
		Actor thisActor = actors.get(actorId);
		return moveAnActor(thisActor, direction);
	}

	private boolean moveAnActor(Actor thisActor, String direction) {
		logger.debug("moveAnActor: 1.0;");
		Position to = checkMove(thisActor, Direction.parse(direction));
		if (to == null) {
			return false;
		}

		thisActor.setPosition(to);
		return true;
	}

	/*
	 * public boolean move(String actorId, String direction) { logger.debug(
	 * "move: 1.0: actorId[" + actorId + "]; direction[" + direction + "]");
	 * Actor thisActor = actors.get(actorId); Position from =
	 * thisActor.getPosition(); Position to = new Position(from);
	 *
	 * if (direction.equalsIgnoreCase("N")) { to.y--; } else if
	 * (direction.equalsIgnoreCase("E")) { to.x++; } else if
	 * (direction.equalsIgnoreCase("S")) { to.y++; } else if
	 * (direction.equalsIgnoreCase("W")) { to.x--; } else { logger.warn(
	 * "move: ERROR - invalid move code;"); }
	 *
	 * // System.out.println("from:" + from.x + ";" + from.y + ";"); //
	 * System.out.println("to:" + to.x + ";" + to.y + ";");
	 *
	 * Type toType = board.getBoard()[to.x][to.y]; if (toType == Type.X) { //
	 * invalid move to 'X' logger.warn("move: ERROR - invalid move to 'X';");
	 * return false; } thisActor.setPosition(to); return true; }
	 */

	public boolean makeMove(String command) {
		logger.debug("makeMove: 1.0:" + command + ";");
		boolean makeMoveResult = false;
		String actorId = command.substring(0, 1);
		String direction = command.substring(1, 2);

		boolean moveResult = moveAnActor(actorId, direction);
		if (moveResult) {
			makeMoveResult = npcPlayersMove();
		}
		logger.debug("makeMove: 2.0;");
		return makeMoveResult;
	}

	private boolean npcPlayersMove() {

		boolean result = true;
		// Position myPosition = null;
		Actor myActor = null;

		for (Player aPlayer : players.keySet()) {
			if (aPlayer instanceof RealPlayer) {
				myActor = players.get(aPlayer);
				// myPosition = myActor.getPosition();
			}
		}

		for (Player aPlayer : players.keySet()) {
			if (aPlayer instanceof AIPlayer) {
				AIPlayer aiPlayer = (AIPlayer) aPlayer;
				Actor aiActor = players.get(aPlayer);
				// Position aiPos = aiActor.getPosition();
				int speed = 1;
				while (speed > 0) {
					speed--;
					Direction nextMove = aiPlayer.calculateNextMove(myActor, aiActor, this);
					if (nextMove != null) {
						moveAnActor(aiActor, nextMove.display);
					}
					/*
					 * int xDiff = myPosition.x - aiPos.x; int yDiff =
					 * myPosition.y - aiPos.y; int range = 0; String move =
					 * null; if (xDiff == 0 && yDiff == 0) { break; } if (xDiff
					 * == 0 || yDiff == 0) { range = 2; } else { range = 3; }
					 * Random rand = new Random(); int direction =
					 * rand.nextInt(range); switch (direction) { case 0: break;
					 * case 1: if (range == 2) { if (xDiff == 0) { if
					 * (aiPlayer.isChase()) { move = "S"; } else { move = "N"; }
					 * } else { if (aiPlayer.isChase()) { move = "E"; } else {
					 * move = "W"; } } } else { if (aiPlayer.isChase() && xDiff
					 * > 0) { move = "S"; } else { move = "N"; } } break; case
					 * 2: if (aiPlayer.isChase()) { move = "E"; } else { move =
					 * "W"; } break; } if (move != null) {
					 * moveAnActor(aiActor.getId(), move); }
					 */
				}

			}
		}

		return result;
	}

	public void addPlayer(Player player, Actor actor) {
		players.put(player, actor);
	}

	private char[][] serializeGameBoard() {
		Type[][] gameBoard = board.getBoard();
		char[][] gameBoardChar = new char[gameBoard.length][gameBoard[0].length];
		for (int y = 0; y < gameBoard[0].length; y++) {
			for (int x = 0; x < gameBoard.length; x++) {
				gameBoardChar[x][y] = gameBoard[x][y].toDisplay();
			}
			for (Actor anActor : actors.values()) {
				gameBoardChar[anActor.getPosition().x][anActor.getPosition().y] = anActor.getId().charAt(0);
			}
		}
		return gameBoardChar;
	}

	public String[] getGameBoardArray() {
		char[][] gameBoardChar = serializeGameBoard();
		String[] gameBoardArray = new String[gameBoardChar[0].length];
		for (int y = 0; y < gameBoardChar[0].length; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < gameBoardChar.length; x++) {
				sb.append(gameBoardChar[x][y]);
			}
			gameBoardArray[y] = sb.toString();
		}
		return gameBoardArray;
	}

	public void printGame() {
		char[][] gameBoardChar = serializeGameBoard();

		for (int y = 0; y < gameBoardChar[0].length; y++) {
			for (int x = 0; x < gameBoardChar.length; x++) {
				System.out.print(gameBoardChar[x][y]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// @formatter:off
		String[] board = {"XXXXXXXXXXXXXXXX".toUpperCase(),
						  "XXXS      XXXXXX".toUpperCase(),
						  "XXX       XXXXXX".toUpperCase(),
						  "XXX       XXXXXX".toUpperCase(),
						  "XXXXXXXDXXXXXXXX".toUpperCase(),
						  "X   XX        XX".toUpperCase(),
						  "X    D        XX".toUpperCase(),
						  "X   XX        XX".toUpperCase(),
						  "X   XXXXXXXXXXXX".toUpperCase(),
						  "X   XXXXXXXXXXXX".toUpperCase(),
						  "XE  XXXXXXXXXXXX".toUpperCase(),
						  "XXXXXXXXXXXXXXXX".toUpperCase()};
		// @formatter:on
		Game game = new Game();

		Board level1 = new Board();
		level1.createBoard(board);
		game.setBoard(level1);

		Position start = level1.getStartPosition();
		Actor player1 = new Actor("1", start.x, start.y);
		game.addActor(player1);
		Actor player2 = new Actor("2", start.x + 2, start.y + 1);
		game.addActor(player2);

		game.printGame();
		game.moveAnActor("1", "E");
		game.moveAnActor("1", "E");
		game.printGame();

	}

}
