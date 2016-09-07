package com.pck.base.tictactoe;

import java.util.Random;

import com.pck.base.tictactoe.Game.BoxStatus;
import com.pck.base.tictactoe.Game.GameStatus;

public class AIPlayer {

	// A very dumb strategy, just randomly pick an empty spot
	public static int getANextMove(Game gameBoard) {
		System.out.println("1.0;");
		int nextMove = 0;

		BoxStatus[][] board = gameBoard.board;
		Random rand = new Random();

		System.out.println("---" + gameBoard.boardToString());
		if (gameBoard.getStatus() == GameStatus.Game_finished_draw ||
			gameBoard.getStatus() == GameStatus.Game_finished_player1_win ||
			gameBoard.getStatus() == GameStatus.Game_finished_player2_win) {
			return 0;
		}
		
		while (nextMove == 0) {
			System.out.println("2.0; nextMove:" + nextMove + ";");
			int position = rand.nextInt(9) + 1;
			System.out.println("2.1; position:" + position + ";");
			switch (position) {
			case 1:
				if (board[2][0] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			case 2:
				if (board[2][1] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			case 3:
				if (board[2][2] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			case 4:
				if (board[1][0] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			case 5:
				if (board[1][1] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			case 6:
				if (board[1][2] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			case 7:
				if (board[0][0] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			case 8:
				if (board[0][1] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			case 9:
				if (board[0][2] == BoxStatus.Empty) {
					nextMove = position;
				}
				break;
			default:
				break;
			}
			System.out.println("3.0; nextMove:" + nextMove + ";");
		}

		return nextMove;
	}

}
