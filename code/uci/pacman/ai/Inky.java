package code.uci.pacman.ai;

import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.controllable.Ghost;

/**
 * @author Team Objects/AI
 *
 */
public class Inky extends Ghost{

	
	public Inky(int x, int y, boolean isPlayer) {
		super(getImage("inky.png"), x, y, isPlayer);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He's the tough one. The only thing I've been able to figure 
	 * out about him is that he seems to be able to take on the 
	 * personality of any of the other three at a given point in time.
	 */
	@Override
	public Direction getMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
