package code.uci.pacman.objects.controllable;


import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.ControllableObject;
import ucigame.Image;


/**
 * 
 * @author Team Objects/AI
 * 
 */

public class Ghost extends ControllableObject{

	private boolean isPlayer;
	public Ghost(Image img, int[] frames, int width, int height, int framerate, int x, int y, boolean isPlayer) {
		super(img, frames, width, height, framerate, x, y);
		this.isPlayer = isPlayer;
		// TODO make this constructor take only position and define the rest here
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
	
	/**
	 * ghosts returns to cage and is no longer scattered
	 */
	public void goHome(){
		
	}
	
	public boolean isPlayer(){
		return isPlayer;
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

	@Override
	protected void spriteDirection(Direction d) {
		// TODO Auto-generated method stub
		
	}


}
