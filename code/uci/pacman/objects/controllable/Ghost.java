package code.uci.pacman.objects.controllable;


import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.ControllableObject;
import ucigame.Image;


/**
 * 
 * @author Object Team
 * 
 */
public class Ghost extends ControllableObject{

	public Ghost(Image img, int[] frames, int width, int height, int framerate, int x, int y) {
		super(img, frames, width, height, framerate, x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * the point value of this object
	 */
	private int scoreValue;
	private boolean scatter;
	

	public void eaten(){
		control.ghostEaten(this);
	}
	
	public boolean isScattered() {
		return scatter;
	}
	/**
	 * sets scatter to true
	 * scatters this ghost
	 */
	public void scatter(){
		
	}

	@Override
	/**
	 * can be called by either AI or remote
	 */
	public void step(Direction d) {
		// TODO Auto-generated method stub
		
	}

	public int getValue() {
		return scoreValue;
	}


}
