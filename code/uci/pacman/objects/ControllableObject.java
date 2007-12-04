package code.uci.pacman.objects;

import java.awt.Point;
import java.util.Collection;

import code.uci.pacman.multiplayer.*;
import code.uci.pacman.controllers.*;
import code.uci.pacman.game.Direction;
import ucigame.*;

/**
 * This is the class represents all objects that can be controlled (PacMan and
 * Ghosts) also implements eatable.
 * 
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
	 * Returns whether the current object has collided with the specified
	 * sprite.
	 * 
	 * @param a
	 *            the actor with which to check collisions
	 * @return true if this object collided with the specified sprite
	 */
	public boolean collidedWith(Sprite a) {
		super.checkIfCollidesWith(a);
		return super.collided();
	}

	/**
	 * @see code.uci.pacman.objects.Eatable#eaten()
	 */
	public abstract void eaten();

	/**
	 * Takes in a direction and sends the direction to the Client (for
	 * multiplayer use).
	 * 
	 * @param dir
	 *            the direction to send to the client
	 */
	public void sendStep(Direction dir) {
		/*if (this instanceof code.uci.pacman.objects.controllable.PacMan) {
			Client.send("PacMan", dir);
		} else if (this instanceof code.uci.pacman.ai.Blinky) {
			Client.send("Blinky", dir);
		} else {
			Client.send("Inky", dir);
		}*/
	}

	/**
	 * this is called when you want to move an object (locally, remotely, AI)
	 * this only sets the motion and does not do any collision detection. It
	 * will do a collision detection to block unallowable movements.
	 * 
	 * @param d
	 *            the direction to move
	 */
	public void step(Direction d) {
		if (moveIsAllowed(d))
			currentDirection = d;

		spriteForDirection(currentDirection);

		if (currentDirection == Direction.UP) {
			motion(0, 0 - speed);
			sendStep(d);
		} else if (currentDirection == Direction.DOWN) {
			motion(0, speed);
			sendStep(d);
		} else if (currentDirection == Direction.LEFT) {
			motion(0 - speed, 0);
			sendStep(d);
		} else if (currentDirection == Direction.RIGHT) {
			motion(speed, 0);
			sendStep(d);
		}
	}

	/**
	 * Used to adjust the speed of a controllable object from the current speed
	 * (does not replace the current speed but just adjusts it).
	 * 
	 * @param speedAdjust
	 *            the amount to adjust the speed by.
	 */
	public void adjustSpeed(int speedAdjust) {
		this.speed += speedAdjust;
	}

	/**
	 * Changes the position of the controllable object to a point on the board.
	 * 
	 * @param p
	 *            the position to move the object to
	 */
	public void position(Point p) {
		super.position(p.x, p.y);
	}

	/**
	 * This method is used to change the sprite, depending on the parameter that
	 * is passed.
	 * 
	 * @param d
	 *            the direction to change the sprite image to
	 */
	protected abstract void spriteForDirection(Direction d);

	/**
	 * This method checks to see whether the controllable object can move in the
	 * given direction.
	 * 
	 * @param d
	 *            the direction to check to for a valid move
	 * @return true if the move is allowed.
	 */
	public abstract boolean moveIsAllowed(Direction d);

	/**
	 * @param stringPath
	 *            the name for the image of the sprite.
	 * @return the sprite image.
	 */
	private static Image getImage(String stringPath) {
		String resources = "images/final/";
		return GameController.getInstance().getPacInstance().getImage(resources + stringPath);
	}

}
