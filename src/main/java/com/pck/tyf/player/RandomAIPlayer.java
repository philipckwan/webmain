package com.pck.tyf.player;

import java.util.List;
import java.util.Random;

import com.pck.tyf.Actor;
import com.pck.tyf.Game;
import com.pck.tyf.Game.Direction;

public class RandomAIPlayer extends Player implements AIPlayer {

	@Override
	public Direction calculateNextMove(Actor target, Actor me, Game game) {
		Direction nextMove = null;

		List<Direction> availableMoves = game.listAvailableMoves(me.getPosition());
		if (availableMoves.size() > 0) {
			Random random = new Random();
			int pick = random.nextInt(availableMoves.size());
			nextMove = availableMoves.get(pick);
		}

		return nextMove;
	}

}
