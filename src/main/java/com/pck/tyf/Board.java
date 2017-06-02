package com.pck.tyf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Board {

	private final static Logger logger = LoggerFactory.getLogger(Board.class);

	public enum Type {
		X('X'), O(' '), S('S'), D('D'), E('E');

		private final char display;

		Type(char display) {
			this.display = display;
		}

		public char toDisplay() {
			return this.display;
		}

		public static Type convertCharToType(char typeChar) {
			if (typeChar == 'X') {
				return Type.X;
			}
			if (typeChar == ' ') {
				return Type.O;
			}
			if (typeChar == 'S') {
				return Type.S;
			}
			if (typeChar == 'D') {
				return Type.D;
			}
			if (typeChar == 'E') {
				return Type.E;
			}
			return Type.X;
		}

		@Override
		public String toString() {
			return String.valueOf(this.display);
		}
	};

	private Type[][] board;
	private Position start, end;

	public Board() {

	}

	public void createBoard(String[] boardStr) {
		int xLength = boardStr[0].length();
		int yLength = boardStr.length;
		board = new Type[xLength][yLength];
		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {
				board[x][y] = Type.convertCharToType(boardStr[y].charAt(x));
			}
		}

		// should do some validation of the board here, according to Board.txt

		// store the starting and ending positions
		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {
				if (board[x][y] == Type.S) {
					start = new Position(x, y);
				}
				if (board[x][y] == Type.E) {
					end = new Position(x, y);
				}
			}
		}

	}

	public Position getStartPosition() {
		return start;
	}

	public Position getEndPosition() {
		return end;
	}

	public Type getTypeFromBoard(Position pos) {
		if (pos.x + 1 >= board.length || pos.y + 1 >= board[0].length) {
			logger.warn("getTypeFromBoard: ERROR - invalid position[" + pos.x + "][" + pos.y + "]");
			return null;
		}
		return board[pos.x][pos.y];
	}

	public Type[][] getBoard() {
		return board;
	}

	public void printBoard() {
		for (int y = 0; y < board[0].length; y++) {
			for (int x = 0; x < board.length; x++) {
				System.out.print(board[x][y]);
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
		Board level1 = new Board();
		level1.createBoard(board);
		level1.printBoard();
	}

}
