package code.uci.pacman.objects.controllable;

import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;

/**
 * @author Team Objects/AI
 *
 */
public class RemoteGhost extends Ghost{

	private Direction curDirection;
	
	private final static int SPEED = 5;
	
	public RemoteGhost(int x, int y, String ghostName) {
		super(ghostName + ".png", x, y, SPEED, true);
	}

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
	
	public void setDirection(Direction d)
	{
		curDirection = d;
	}
}