package com.pck.simulation;

import java.util.ArrayList;
import java.util.List;

import com.pck.simulation.actor.Actor;
import com.pck.simulation.actor.Deer;
import com.pck.simulation.actor.Grass;

/*
 * The playgound
 */
public class Wilderness {

	private int xSpan;
	private int ySpan;
	private int day = 0;

	private List<Actor> occupants = new ArrayList<Actor>();

	private Actor[][] plain;

	public Wilderness(int xSpan, int ySpan) {
		this.xSpan = xSpan;
		this.ySpan = ySpan;

		plain = new Actor[xSpan][ySpan];
	}

	public boolean putActor(Actor actor, int xPos, int yPos) {
		if (xPos >= xSpan || yPos >= ySpan) {
			return false;
		}

		if (plain[xPos][yPos] != null) {
			return false;
		}

		plain[xPos][yPos] = actor;
		occupants.add(actor);

		return true;
	}

	public void printPlain() {
		System.out.println("----------");
		for (int y = ySpan - 1; y >= 0; y--) {
			for (int x = 0; x < xSpan; x++) {
				String toPrint = " ";
				Actor anActor = plain[x][y];
				if (anActor != null) {
					toPrint = anActor.getInitial();
				}
				if ((x == 0 || y == 0 || x == xSpan - 1 || y == ySpan - 1) && toPrint.equals(" ")) {
					toPrint = ".";
				}
				System.out.print(toPrint);
			}
			System.out.println();
		}

		System.out.println("------total [" + occupants.size() + "] occupants-----");
	}

	public static void main(String[] args) {
		Wilderness wild = new Wilderness(40, 10);

		wild.putActor(new Grass(), 0, 0);
		wild.putActor(new Deer(), 9, 4);

		wild.putActor(new Grass(), 1, 1);
		wild.putActor(new Grass(), 2, 2);
		wild.putActor(new Grass(), 3, 3);
		wild.putActor(new Grass(), 4, 4);

		wild.putActor(new Deer(), 5, 4);
		wild.putActor(new Deer(), 9, 0);
		wild.putActor(new Deer(), 0, 4);

		wild.printPlain();
	}
}
