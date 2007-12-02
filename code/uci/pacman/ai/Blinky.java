package code.uci.pacman.ai;

import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.controllable.Ghost;

/**
 * @author Team Objects/AI
 *
 */
public class Blinky extends Ghost{

	public Blinky(int x, int y, boolean isPlayer) {
		super(getImage("blinky.png"), x, y, isPlayer);
		// TODO Auto-generated constructor stub
	}

	/* 
	 * He tries to get you by your relative position. 
	 * He takes the fastest route to find you. I believe he 
	 * tries to line up with you horizontally first, then vertically.
	 */
	@Override
	public Direction getMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
