package code.uci.pacman.ai;

import ucigame.Image;
import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.controllable.Ghost;

/**
 * @author Team Objects/AI
 *
 */
public class Clyde extends Ghost{

	public Clyde(int x, int y, boolean isPlayer) {
		super(getImage("clyde.png"), x, y, isPlayer);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He seems to want to be in the same general area as you, 
	 * but doesn't seem to directly chase you. Don't let this fool 
	 * you; he will not turn away if you are in his path. If you stay 
	 * in one area, he seems to have a set pattern.
	 */
	@Override
	public Direction getMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
