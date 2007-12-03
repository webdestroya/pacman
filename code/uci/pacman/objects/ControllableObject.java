package code.uci.pacman.objects;

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
	public ControllableObject(Image img, int[] frames, int width, int height, int framerate, int x, int y) {
		super(width, height);
		position(x, y);
		addFrames(img, frames);
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
	
	/**
	 * this is called when you want to move an object (locally, remotely, AI)
	 * this only sets the motion and does not do any collision detection
	 * @param d
	 */
	public void step(Direction d){
		spriteDirection(d);
		if (d == Direction.UP)
			motion(0,0-speed);
		if (d == Direction.DOWN)
			motion(0,speed);
		if (d == Direction.LEFT)
			motion(0-speed, 0);
		if (d == Direction.RIGHT)
			motion(speed, 0);
	}

	protected abstract void spriteDirection(Direction d);
}
