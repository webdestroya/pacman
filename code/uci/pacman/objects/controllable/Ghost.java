package code.uci.pacman.objects.controllable;


import code.uci.pacman.ai.AI;
import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.ControllableObject;
import ucigame.Image;


/**
 * 
 * @author Team Objects/AI
 * 
 */

public abstract class Ghost extends ControllableObject implements AI{
	private static int ghostWidth=22;
	private static int ghostHeight=22;
	private boolean isPlayer;
	public Ghost(Image img, int x, int y, boolean isPlayer) {
		super(img, new int[] {0,0}, ghostWidth, ghostHeight, 5, x, y);
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

	/**
	 * tells the ghost to unscatter after the timer is set (called from the ghosts controller)
	 */
	public void unScatter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public abstract Direction getMove();


}
