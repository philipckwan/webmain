package com.pck.simulation;

import com.pck.simulation.actor.Deer;
import com.pck.simulation.actor.Grass;

public final class SimEngine {

	private Wilderness wild;

	public static void main(String[] args) {
		SimEngine sim = new SimEngine();

		sim.run1();
	}

	public void run1() {
		System.out.println("SimEnginer.run1: START;");

		wild = new Wilderness(40, 10);

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

		System.out.println("SimEnginer.run1: END;");
	}
}
