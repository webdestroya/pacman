package code.uci.pacman.ai;

import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.controllable.Ghost;

/**
 * @author Team Objects/AI
 *
 */
public class Pinky extends Ghost{

	public Pinky(int x, int y, boolean isPlayer) {
		super(getImage("pinky.png"), x, y, isPlayer);
		// TODO Auto-generated constructor stub
	}

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
