package com.pck.base.tictactoe;

import com.pck.base.webmain.common.User;
import com.pck.tictactoe.Game;

import junit.framework.TestCase;

public class GameTest extends TestCase {

	private Game aGame;
	private User player1;
	private User player2;

	public GameTest(String name) {
		super(name);
	}

	private void setup() {
		aGame = new Game();

		player1 = new User();
		player1.setUserID("Philip");

		player2 = new User();
		player2.setUserID("Doris");

		aGame.setPlayer(player1);
		aGame.setPlayer(player2);
	}

	public void testGame1() {
		System.out.println("GameTest.testGame1: START");

		setup();
		assertTrue(aGame.getStatus() == Game.GameStatus.Game_in_progress_pending_player1_move);
		aGame.makeAMove(player1, 5);
		assertTrue(aGame.getStatus() == Game.GameStatus.Game_in_progress_pending_player2_move);
		aGame.makeAMove(player2, 8);
		assertTrue(aGame.getStatus() == Game.GameStatus.Game_in_progress_pending_player1_move);

		System.out.println(aGame.boardToString());
		System.out.println("GameTest.testGame1: END");
	}

	public void testGame2() {
		System.out.println("GameTest.testGame2: START");

		setup();
		aGame.makeAMove(player1, 5);
		aGame.makeAMove(player2, 8);
		aGame.makeAMove(player1, 4);
		aGame.makeAMove(player2, 1);
		System.out.println(aGame.boardToString());

		assertTrue(aGame.getStatus() == Game.GameStatus.Game_in_progress_pending_player1_move);
		assertTrue(aGame.board[0][1] == Game.BoxStatus.Player2);
		assertTrue(aGame.board[2][0] == Game.BoxStatus.Player2);

		System.out.println("GameTest.testGame2: END");
	}

	public void testGame3() {
		System.out.println("GameTest.testGame3: START");

		setup();
		aGame.makeAMove(player1, 5);
		aGame.makeAMove(player2, 8);
		aGame.makeAMove(player1, 4);
		aGame.makeAMove(player2, 2);
		aGame.makeAMove(player1, 6);

		System.out.println(aGame.boardToString());

		assertTrue(aGame.getStatus() == Game.GameStatus.Game_finished_player1_win);

		System.out.println("GameTest.testGame3: END");
	}

}
