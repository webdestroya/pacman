package code.uci.pacman.objects.controllable;

import game.Direction;
import objects.ControllableObject;
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
	
	/**
	 * 
	 * change/reset position
	 * make not scattered
	 * update score
	 * 
	 */
	public void eaten(){
		
	}
	
	public boolean isScattered() {
		return false;
	}
	/**
	 * sets scatter to true
	 * scatters the ghosts
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
