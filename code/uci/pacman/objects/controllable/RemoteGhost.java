package code.uci.pacman.objects.controllable;

import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.controllable.Ghost;

/**
 * @author Team Objects/AI
 *
 */
public class RemoteGhost extends Ghost{

	private Direction curDirection;
	
	private final static int SPEED = 5;
	
	/**
	 * Constructs a remote ghost given its inital position and its name. 
	 * @param x - the initial x coordinate of the ghost.
	 * @param y - the initial y coordinate of the ghost.
	 * @param ghostName - the name of the ghost.  For example, "Blinky"
	 */
	public RemoteGhost(int x, int y, String ghostName) {
		super(ghostName + ".png", x, y, SPEED, true);
	}

	/**
	 * Determines the direction this remote ghost will go.
	 * @return Direction in which this ghost will move.
	 */
	@Override
	public Direction getMove() {
		if (this.isScattered()) {
		}
		else
		{
		return curDirection;
		}
		
		return null;
	}
	
	/**
	 * Sets the ghost in a direction.
	 * @param d - The direction the ghost will be set to move towards.
	 */
	public void setDirection(Direction d)
	{
		curDirection = d;
	}
}