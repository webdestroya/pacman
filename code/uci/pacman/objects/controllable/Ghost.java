package code.uci.pacman.objects.controllable;


import java.util.Random;

import code.uci.pacman.ai.AI;
import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.ControllableObject;
import ucigame.Image;


/**
 * 
 * @author Team Objects/AI
 * 
 */

public abstract class Ghost extends ControllableObject implements AI {

	private static final int GHOSTWIDTH = 22;
	private static final int GHOSTHEIGHT = 22;
	private static final int GHOSTFRAMERATE = 5;
	private static final int CAGEPOS = 250;
	
	private boolean isPlayer;

	public Ghost(String imgPath, int x, int y, boolean isPlayer) {
		super(imgPath, new int[] {0,0}, GHOSTWIDTH, GHOSTHEIGHT, GHOSTFRAMERATE, x, y);
		this.isPlayer = isPlayer;
		super.speed = 7;
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
		scatter = true;
		// TODO change sprite? yes
	}
	
	/**
	 * tells the ghost to stop scattering and begin attack
	 */
	public void unScatter() {
		scatter = false;
		// TODO change sprite? yes
	}
	
	/***
	 * Respawns this ghost back within the cage and disables scatter for this ghost
	 */
	public void respawnInCage() {
		Random r = new Random();
		int randomOffset = r.nextInt(50);
		this.position(CAGEPOS + randomOffset, CAGEPOS);
	}
	
	public boolean isPlayer(){
		return isPlayer;
	}

	public int getValue() {
		return scoreValue;
	}

	@Override
	//this is for changing the sprite based on direction
	protected void spriteForDirection(Direction d) {
		// TODO Auto-generated method stub
		
	}

	public abstract Direction getMove();


}
