package code.uci.pacman.objects;

import java.awt.Point;

import code.uci.pacman.multiplayer.*;
import code.uci.pacman.controllers.GameController;
import code.uci.pacman.game.Direction;
import ucigame.Image;
import ucigame.Sprite;

/**
 * this is the class represents all objects that can be controlled (pacman and ghosts)
 * @author Team Objects/AI
 *
 */

public abstract class ControllableObject extends Sprite implements Eatable {
	protected double speed;
    protected GameController control;
    protected Direction currentDirection;
    
	public ControllableObject(String imgPath, int[] frames, int width, int height, int framerate, int x, int y) {
		super(width, height);
		position(x, y);
		addFrames(getImage(imgPath), frames);
		framerate(framerate);
		control = GameController.getInstance();
	}	
	
	/**
	 * Returns whether the current object has collided with the specified sprite.
	 * 
	 * @param a the actor with which to check collisions
	 * @return true if this object collided with the specified sprite
	 */
	public boolean collidedWith(Sprite a) {
		super.checkIfCollidesWith(a);
		return super.collided();
	}
	
	public abstract void eaten();
	
	public void sendStep(String dir)
	{
		if( this instanceof code.uci.pacman.objects.controllable.PacMan )
		{
			Client.send("PacMan", dir);
		} 
		else if( this instanceof code.uci.pacman.ai.Blinky )
		{
			Client.send("Blinky", dir);
		}
		else
		{
			Client.send("Inky", dir);
		}
	}
	
	/**
	 * this is called when you want to move an object (locally, remotely, AI)
	 * this only sets the motion and does not do any collision detection. It will do a
	 * collision detection to block unallowable movements.
	 * @param d
	 */
	public void step(Direction d){
		if(moveIsAllowed(d))
			currentDirection = d;
		
		spriteForDirection(currentDirection);
		
		if (currentDirection == Direction.UP)
		{
			motion(0,0-speed);
			sendStep("UP");
		}
		else if (currentDirection == Direction.DOWN)
		{
			motion(0,speed);
			sendStep("DOWN");
		}
		else if (currentDirection == Direction.LEFT)
		{
			motion(0-speed, 0);
			sendStep("LEFT");
		}
		else if (currentDirection == Direction.RIGHT)
		{
			motion(speed, 0);
			sendStep("RIGHT");
		}
	}
	
	public void adjustSpeed(int speedAdjust) {
		this.speed += speedAdjust;
	}

	public void position(Point p) {
		super.position(p.x, p.y);
	}
	
	protected abstract void spriteForDirection(Direction d);
	
	public boolean moveIsAllowed(Direction d)
	{
		return true;
	}
	
	private static Image getImage(String stringPath){
		String resources = "images/final/";
		return GameController.getInstance().getPacInstance().getImage(resources+stringPath);
	}
}
