package code.uci.pacman.objects;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.game.Direction;
import ucigame.Image;
import ucigame.Sprite;
/**
 * 
 * @author Object Team
 * this is the class represents all objects that can be controlled (pacman and ghosts)
 *
 */
public abstract class ControllableObject extends Sprite implements Eatable{
    protected GameController control;
	public ControllableObject(Image img, int[] frames, int width, int height, int framerate, int x, int y) {
		super(width, height);
		position(x, y);
		addFrames(img, frames);
		framerate(framerate);
		control = GameController.getInstance();
		// TODO Auto-generated constructor stub
	}
	
	public abstract void eaten();
	
	/**
	 * this is called when you want to move an object (locally, remotely, AI)
	 * this only sets the motion and does not do any collision detection
	 * @param d
	 */
	public abstract void step(Direction d);
}
