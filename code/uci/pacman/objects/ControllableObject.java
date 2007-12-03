package code.uci.pacman.objects;

import java.awt.Point;

import code.uci.pacman.multiplayer.*;
import code.uci.pacman.controllers.GameController;
import code.uci.pacman.game.Direction;
import ucigame.Image;
import ucigame.Sprite;

/**
 * 
 * @author Team Objects/AI
 * this is the class represents all objects that can be controlled (pacman and ghosts)
 *
 */

public abstract class ControllableObject extends Sprite implements Eatable {
	protected int speed;
    protected GameController control;
	public ControllableObject(String imgPath, int[] frames, int width, int height, int framerate, int x, int y) {
		super(width, height);
		position(x, y);
		addFrames(getImage(imgPath), frames);
		framerate(framerate);
		control = GameController.getInstance();
	}
	
	protected static Image getImage(String stringPath){
		String resources = "images/final/";
		return GameController.getInstance().getPacInstance().getImage(resources+stringPath);
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
	 * this only sets the motion and does not do any collision detection
	 * @param d
	 */
	public void step(Direction d){
		spriteForDirection(d);
		if (d == Direction.UP)
		{
			motion(0,0-speed);
			sendStep("UP");
		}
		else if (d == Direction.DOWN)
		{
			motion(0,speed);
			sendStep("DOWN");
		}
		else if (d == Direction.LEFT)
		{
			motion(0-speed, 0);
			sendStep("LEFT");
		}
		else if (d == Direction.RIGHT)
		{
			motion(speed, 0);
			sendStep("RIGHT");
		}
	}

	public void position(Point p) {
		super.position(p.x, p.y);
	}
	
	protected abstract void spriteForDirection(Direction d);
}
