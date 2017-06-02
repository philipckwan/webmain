package com.pck.tyf.player;

import com.pck.tyf.Actor;
import com.pck.tyf.Game;
import com.pck.tyf.Game.Direction;

public interface AIPlayer {

	public Direction calculateNextMove(Actor target, Actor me, Game game);

}
