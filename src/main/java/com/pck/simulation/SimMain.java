package com.pck.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pck.simulation.actor.Deer;
import com.pck.simulation.actor.Grass;

public class SimMain {

	private final static Logger logger = LoggerFactory.getLogger(SimMain.class);

	public static void main(String[] args) {
		logger.debug("SimMain.main: START;");

		SimMain.run1();

		logger.debug("SimMain.main: END;");
	}

	private static void run1() {
		logger.debug("SimMain.run1: START;");

		SimEngine sim = SimEngine.getInstance();
		sim.setWild(new Wilderness(40, 10));

		sim.getWild().putActor(new Grass(), 0, 0);
		sim.getWild().putActor(new Deer(), 9, 4);

		sim.getWild().putActor(new Grass(), 1, 1);
		sim.getWild().putActor(new Grass(), 2, 2);
		sim.getWild().putActor(new Grass(), 3, 3);
		sim.getWild().putActor(new Grass(), 4, 4);

		sim.getWild().putActor(new Deer(), 5, 4);
		sim.getWild().putActor(new Deer(), 9, 0);
		sim.getWild().putActor(new Deer(), 0, 4);

		//wild.printPlain();

		logger.debug(sim.getWild().plainToString());

		logger.debug("SimMain.run1: END;");
	}

}
