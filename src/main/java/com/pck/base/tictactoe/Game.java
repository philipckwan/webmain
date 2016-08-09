package com.pck.base.tictactoe;

import com.pck.base.webmain.common.User;

public class Game {

	public static final String EMPTY = "-";
	public static final String X = "X";
	public static final String O = "O";

	public enum GameStatus {
		Initializing, Waiting_for_game, Game_in_progress_pending_player1_move, Game_in_progress_pending_player2_move, Game_finished_player1_win, Game_finished_player2_win
	}

	public enum BoxStatus {
		Empty("-"), Player1("O"), Player2("X");

		String display;

		BoxStatus(String display) {
			this.display = display;
		}

		@Override
		public String toString() {
			return this.display;
		}
	}

	BoxStatus[][] board = new BoxStatus[][] { { BoxStatus.Empty, BoxStatus.Empty, BoxStatus.Empty },
			{ BoxStatus.Empty, BoxStatus.Empty, BoxStatus.Empty },
			{ BoxStatus.Empty, BoxStatus.Empty, BoxStatus.Empty } };

	private GameStatus status = GameStatus.Initializing;
	private User player1 = null;
	private User player2 = null;

	public boolean setPlayer(User aPlayer) {
		boolean results = true;

		if (player1 == null) {
			player1 = aPlayer;
			this.status = GameStatus.Waiting_for_game;
		} else if (player2 == null && !aPlayer.getUserID().equals(player1.getUserID())) {
			player2 = aPlayer;
			this.status = GameStatus.Game_in_progress_pending_player1_move;
		} else {
			results = false;
		}

		return results;
	}

	public boolean makeAMove(User aPlayer, int position) {

		BoxStatus updateBoxStatus = null;
		GameStatus updateGameStatus = null;

		if (aPlayer.getUserID().equals(player1.getUserID())
				&& status == GameStatus.Game_in_progress_pending_player1_move) {
			updateBoxStatus = BoxStatus.Player1;
			updateGameStatus = GameStatus.Game_in_progress_pending_player2_move;
		} else if (aPlayer.getUserID().equals(player2.getUserID())
				&& status == GameStatus.Game_in_progress_pending_player2_move) {
			updateBoxStatus = BoxStatus.Player2;
			updateGameStatus = GameStatus.Game_in_progress_pending_player1_move;
		} else {
			return false;
		}

		switch (position) {
		case 1:
			if (board[2][0] != BoxStatus.Empty) {
				return false;
			} else {
				board[2][0] = updateBoxStatus;
				break;
			}
		case 2:
			if (board[2][1] != BoxStatus.Empty) {
				return false;
			} else {
				board[2][1] = updateBoxStatus;
				break;
			}
		case 3:
			if (board[2][2] != BoxStatus.Empty) {
				return false;
			} else {
				board[2][2] = updateBoxStatus;
				break;
			}
		case 4:
			if (board[1][0] != BoxStatus.Empty) {
				return false;
			} else {
				board[1][0] = updateBoxStatus;
				break;
			}
		case 5:
			if (board[1][1] != BoxStatus.Empty) {
				return false;
			} else {
				board[1][1] = updateBoxStatus;
				break;
			}
		case 6:
			if (board[1][2] != BoxStatus.Empty) {
				return false;
			} else {
				board[1][2] = updateBoxStatus;
				break;
			}
		case 7:
			if (board[0][0] != BoxStatus.Empty) {
				return false;
			} else {
				board[0][0] = updateBoxStatus;
				break;
			}
		case 8:
			if (board[0][1] != BoxStatus.Empty) {
				return false;
			} else {
				board[0][1] = updateBoxStatus;
				break;
			}
		case 9:
			if (board[0][2] != BoxStatus.Empty) {
				return false;
			} else {
				board[0][2] = updateBoxStatus;
				break;
			}
		default:
			return false;
		}

		status = updateGameStatus;

		// Check if anyone has won the game
		// there are 8 ways a game is won
		if (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != BoxStatus.Empty) {
			status = (board[2][0] == BoxStatus.Player1) ? GameStatus.Game_finished_player1_win
					: GameStatus.Game_finished_player2_win;
		}

		if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != BoxStatus.Empty) {
			status = (board[1][0] == BoxStatus.Player1) ? GameStatus.Game_finished_player1_win
					: GameStatus.Game_finished_player2_win;
		}

		if (board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != BoxStatus.Empty) {
			status = (board[0][0] == BoxStatus.Player1) ? GameStatus.Game_finished_player1_win
					: GameStatus.Game_finished_player2_win;
		}

		if (board[2][0] == board[1][0] && board[1][0] == board[0][0] && board[2][0] != BoxStatus.Empty) {
			status = (board[2][0] == BoxStatus.Player1) ? GameStatus.Game_finished_player1_win
					: GameStatus.Game_finished_player2_win;
		}

		if (board[2][1] == board[1][1] && board[1][1] == board[0][1] && board[2][1] != BoxStatus.Empty) {
			status = (board[2][1] == BoxStatus.Player1) ? GameStatus.Game_finished_player1_win
					: GameStatus.Game_finished_player2_win;
		}

		if (board[2][2] == board[1][2] && board[1][2] == board[0][2] && board[2][2] != BoxStatus.Empty) {
			status = (board[2][2] == BoxStatus.Player1) ? GameStatus.Game_finished_player1_win
					: GameStatus.Game_finished_player2_win;
		}

		if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != BoxStatus.Empty) {
			status = (board[2][0] == BoxStatus.Player1) ? GameStatus.Game_finished_player1_win
					: GameStatus.Game_finished_player2_win;
		}

		if (board[2][2] == board[1][1] && board[1][1] == board[0][0] && board[2][2] != BoxStatus.Empty) {
			status = (board[2][2] == BoxStatus.Player1) ? GameStatus.Game_finished_player1_win
					: GameStatus.Game_finished_player2_win;
		}

		return true;
	}

	public GameStatus getStatus() {
		return status;
	}

	public String boardToString() {
		StringBuffer sb = new StringBuffer();
		sb.append("-----board status[" + status + "]-----\n");
		sb.append("Player1: [" + ((player1 != null) ? player1.getUserID() : "N/A") + "];\n");
		sb.append("Player2: [" + ((player2 != null) ? player2.getUserID() : "N/A") + "];\n");
		sb.append("     | " + board[2][0] + " " + board[2][1] + " " + board[2][2] + " |\n");
		sb.append("     | " + board[1][0] + " " + board[1][1] + " " + board[1][2] + " |\n");
		sb.append("     | " + board[0][0] + " " + board[0][1] + " " + board[0][2] + " |\n");
		sb.append("----- end -----\n");
		return sb.toString();
	}

	public BoardJson boardToJson() {
		BoardJson boardJson = new BoardJson();
		boardJson.lines[2] = "     | " + board[2][0] + " " + board[2][1] + " " + board[2][2] + " |";
		boardJson.lines[1] = "     | " + board[1][0] + " " + board[1][1] + " " + board[1][2] + " |";
		boardJson.lines[0] = "     | " + board[0][0] + " " + board[0][1] + " " + board[0][2] + " |";
		return boardJson;
	}

	public static void main(String args[]) {
		Game aGame = new Game();
		System.out.println(aGame.boardToString());

		User player1 = new User();
		player1.setUserID("Philip");

		User player2 = new User();
		player2.setUserID("Doris");

		aGame.setPlayer(player1);
		aGame.setPlayer(player2);

		System.out.println(aGame.boardToString());

		aGame.makeAMove(player1, 5);
		aGame.makeAMove(player2, 8);

		System.out.println(aGame.boardToString());

		aGame.makeAMove(player1, 1);
		aGame.makeAMove(player2, 6);

		System.out.println(aGame.boardToString());

		aGame.makeAMove(player1, 9);

		System.out.println(aGame.boardToString());

		aGame.makeAMove(player2, 3);

		System.out.println(aGame.boardToString());
	}

	class BoardJson {
		private String lines[] = new String[3];
	}

}
