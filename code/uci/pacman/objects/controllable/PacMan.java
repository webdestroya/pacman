package code.uci.pacman.objects.controllable;

import code.uci.pacman.controllers.WallController;
import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.ControllableObject;

/**
 * Represents PacMan - the main character of the game.
 * PacMan is controlled by a human and can eat fruit,
 * pellets and power pellets.  PacMan can be eaten by
 * ghosts.  If he eats a power pellet, PacMan can eat
 * ghosts within a short window of time.
 * @author Team Objects/AI
 *
 */
public class PacMan extends ControllableObject {
	
	private static final String pacImagePath = "pacman.png";
	private static final int PACWIDTH = 20;
	private static final int PACHEIGHT = 21;
	private static final int PACFRAMERATE = 10;
	private static final int PACSPEED = 7;
	private double angle; //0,90,180,270

	/**
	 * Makes a new PacMan given it's initial coordinates on the level.
	 * @param x The initial x coordinate of PacMan.
	 * @param y The initial y coordinate of PacMan.
	 */
	public PacMan(int x, int y) {
		super(pacImagePath, new int[] {0,0,22,0,43,0,64,0}, PACWIDTH, PACHEIGHT, PACFRAMERATE, x, y);
		super.addFramesForAnimation("death", "pacman.png", 43, 0); //TODO add death animation frames
		super.speed = PACSPEED;
		angle = 0;
	}

	/**
	 * Tells the game controller that PacMan has been eaten.
	 */
	public void eaten() {
		control.pacManEaten(this);
	}
	
	/**
	 * Draws PacMan on the screen.
	 */
	public void draw(){
		super.rotate(angle);
		super.draw();
	}

	/**
	 * This changes the sprite based on its direction.  Specifically,
	 * it sets the angle at which the sprite should be rotated.
	 * @param d direction of PacMan.
	 */
	protected void spriteForDirection(Direction d) {
		if(d == Direction.UP)
			angle=270;
		if(d == Direction.DOWN)
			angle=90;
		if(d == Direction.LEFT)
			angle=180;
		if(d == Direction.RIGHT)
			angle=0;
	}
	
	/**
	 * Determines whether a move in a particular direction is allowed.
	 * @param d The direction PacMan may try to move towards.
	 * @return true if moving in the direction provided is allowed; false if that way is obstructed by a wall.
	 */
	public boolean moveIsAllowed(Direction d)
	{
		WallController walls = GameState.getInstance().getWalls();
		if(d == Direction.UP && walls.willCollideAtPos(this,0,-12))
			return true;
		if(d == Direction.DOWN && walls.willCollideAtPos(this,0,12 + this.height()))
			return true;
		if(d == Direction.LEFT && walls.willCollideAtPos(this,-9,0))
			return true;
		if(d == Direction.RIGHT && walls.willCollideAtPos(this,9 + this.width(),0))
			return true;
		else
			return false;
	}
}
