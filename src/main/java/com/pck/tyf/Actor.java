package com.pck.tyf;

public class Actor {

	private String id;
	private Position position;

	Actor(String id, int startX, int startY) {
		this.id = id;
		this.position = new Position(startX, startY);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
