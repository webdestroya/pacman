package code.uci.pacman.objects.controllable;

import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.ControllableObject;
import ucigame.Image;

/**
 * @author Team Objects/AI
 *
 */
public class PacMan extends ControllableObject{

	public PacMan(Image img, int[] frames, int width, int height, int framerate, int x, int y) {
		super(img, frames, width, height, framerate, x, y);
		// TODO make this constructor take only position and define the rest here
	}

	@Override
	public void eaten() {
		// TODO Auto-generated method stub
		control.pacManEaten(this);
	}

	@Override
	/**
	 * always called by local human
	 */
	public void step(Direction d) {
		// TODO Auto-generated method stub
		
	}

	public int getValue() {
		return 0;
	}
	
	

}
