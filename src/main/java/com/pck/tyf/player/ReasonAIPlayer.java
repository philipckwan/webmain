package com.pck.tyf.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.pck.tyf.Actor;
import com.pck.tyf.Game;
import com.pck.tyf.Game.Direction;
import com.pck.tyf.Position;

public class ReasonAIPlayer extends Player implements AIPlayer {
	private boolean isChase;

	public ReasonAIPlayer(boolean isChase) {
		this.isChase = isChase;
	}

	@Override
	public Direction calculateNextMove(Actor target, Actor me, Game game) {
		Direction nextMove = null;

		List<Direction> availableMoves = game.listAvailableMoves(me.getPosition());
		List<Direction> toPickMoves = new ArrayList<Direction>();
		int currentDistance = getDistance(target.getPosition(), me.getPosition());
		if (availableMoves.size() > 0) {
			for (Direction aDirection : availableMoves) {
				Position myNewPosition = game.checkMove(me, aDirection);
				if (isChase && getDistance(target.getPosition(), myNewPosition) < currentDistance) {
					toPickMoves.add(aDirection);
				} else if (!isChase && getDistance(target.getPosition(), myNewPosition) > currentDistance) {
					toPickMoves.add(aDirection);
				}
			}
		}

		if (toPickMoves.size() > 0) {
			Random random = new Random();
			int pick = random.nextInt(toPickMoves.size());
			nextMove = toPickMoves.get(pick);
		}
		return nextMove;
	}

	private int getDistance(Position targetPos, Position myPos) {
		return Math.abs(targetPos.x - myPos.x) + Math.abs(targetPos.y - myPos.y);
	}
}
