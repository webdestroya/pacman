package Objects;

import ucigame.Sprite;
import Game.*;
/**
 * 
 * @author Object Team
 * this is the class represents all objects that can be controlled (pacman and ghosts)
 *
 */
public abstract class ControllableObject extends Sprite implements Eatable{
	
	public ControllableObject(int arg0, int arg1) {
		super(arg0, arg1);
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
