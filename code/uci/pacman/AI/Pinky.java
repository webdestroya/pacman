package code.uci.pacman.AI;

import code.uci.pacman.game.Direction;

/**
 * @author Team Objects/AI
 *
 */
public class Pinky implements AI{

	/* (non-Javadoc)
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He wants to move in the same direction as you, but only 
	 * when he's close enough to you. In other words, he tries 
	 * to get to your area first, then when he's close enough, 
	 * he will try to run in the same direction as you. This can 
	 * be tested by running right up to him and when he gets right 
	 * in front of you he will turn away (provided there is a 
	 * hallway to turn into)
	 */
	@Override
	public Direction getMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
