package code.uci.pacman.objects.controllable;

import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.ControllableObject;
import ucigame.Image;

/**
 * @author Team Objects/AI
 *
 */
public class PacMan extends ControllableObject{
	
	private static Image pacImage = getImage("pacman.png");
	private static int pacWidth = 32;
	private static int pacHeight = 32;
	private static int speed = 12;
	private double angle; //0,90,180,270

	public PacMan(int x, int y) {
		super(pacImage, new int[] {0,0,33,0,67,0,100,0}, pacWidth, pacHeight, 10, x, y);
		super.speed = speed;
		angle = 0;
	}

	@Override
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

	@Override
	protected void spriteDirection(Direction d) {
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
	
	

}
