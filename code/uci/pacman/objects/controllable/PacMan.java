package code.uci.pacman.objects.controllable;

import code.uci.pacman.controllers.WallController;
import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.ControllableObject;

/**
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

	public PacMan(int x, int y) {
		super(pacImagePath, new int[] {0,0,22,0,43,0,64,0}, PACWIDTH, PACHEIGHT, PACFRAMERATE, x, y);
		super.speed = PACSPEED;
		angle = 0;
	}

	public void eaten() {
		// TODO Auto-generated method stub
		control.pacManEaten(this);
	}

	public int getValue() {
		return 0;
	}
	
	public void draw(){
		super.rotate(angle);
		super.draw();
	}

	protected void spriteForDirection(Direction d) {
		if(d == Direction.UP)
			angle=270;
		if(d == Direction.DOWN)
			angle=90;
		if(d == Direction.LEFT)
			angle=180;
		if(d == Direction.RIGHT)
			angle=0;
		// TODO Auto-generated method stub	
	}
	
	public boolean moveIsAllowed(Direction d)
	{
		WallController walls = GameState.getInstance().getWalls();
		if(d == Direction.UP && walls.existsAtPos(this,0,-13))
			return true;
		if(d == Direction.DOWN && walls.existsAtPos(this,0,13 + this.height()))
			return true;
		if(d == Direction.LEFT && walls.existsAtPos(this,-10,0))
			return true;
		if(d == Direction.RIGHT && walls.existsAtPos(this,10 + this.width(),0))
			return true;
		else
			return false;
	}
}
