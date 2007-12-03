package code.uci.pacman.objects.controllable;


import java.util.Random;

import code.uci.pacman.ai.AI;
import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.ControllableObject;


/**
 * 
 * @author Team Objects/AI
 * 
 */

public abstract class Ghost extends ControllableObject implements AI {

	private static final int GHOST_WIDTH = 22;
	private static final int GHOST_HEIGHT = 22;
	private static final int GHOST_FRAMERATE = 5;
	private static final int CAGE_POS = 250;
	
	private boolean isPlayer;

	public Ghost(String imgPath, int x, int y, int speed, boolean isPlayer) {
		super(imgPath, new int[] {0,0}, GHOST_WIDTH, GHOST_HEIGHT, GHOST_FRAMERATE, x, y);
		super.speed = speed;
		this.isPlayer = isPlayer;
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
		System.out.println("UNSCATTER");
		scatter = false;
		// TODO change sprite? yes
	}
	
	/***
	 * Respawns this ghost back within the cage and disables scatter for this ghost
	 */
	public void respawnInCage() {
		Random r = new Random();
		int randomOffset = r.nextInt(50);
		super.position(CAGE_POS + randomOffset, CAGE_POS);
		this.unScatter();
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
