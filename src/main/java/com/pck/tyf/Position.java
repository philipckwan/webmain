package com.pck.tyf;

public class Position {

	public int x;
	public int y;

	Position(Position origin) {
		this.x = origin.x;
		this.y = origin.y;
	}

	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
