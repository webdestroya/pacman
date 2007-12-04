package code.uci.pacman.controllers;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * This controller is responsible for all actions and functionality related to
 * the many pills located all over the game world. This controller handles
 * constructing pills, colliding with pills, and accessing pill information such
 * as the total pills left in the game world.
 * 
 * @author The Game Team
 */
public class PillController extends ArtifactController<Pill> {
	private int totalPills;

	public PillController() {
		totalPills = 0;
		this.constructArtifactsFromFile("pill");
	}

	/**
	 * 
	 * Retrieves a pill that is currently colliding with PacMan. If no pill is
	 * colliding with PacMan at this time then the function returns null.
	 * 
	 * @param p
	 *            the PacMan instance to collide
	 * @return the pill currently colliding with PacMan
	 */
	public Pill getCollidingPill(PacMan p) {
		return super.getCollidedWith(p);
	}

	/**
	 * 
	 * Gets the total number of pills that were originally on the screen for
	 * this round before PacMan began chomping. This method is used to determine
	 * if a fruit should be displayed on the screen.
	 * 
	 * @return the total pills placed on screen
	 */
	public int getInitialCount() {
		return totalPills;
	}

	/**
	 * 
	 * Gets the current number of pills located within the game world. This
	 * method is useful in determining when all the pills are gone and the stage
	 * has been cleared.
	 * 
	 * @return the total remaining pills
	 */
	public int getPillCount() {
		return super.getObjects().size();
	}
	
	

	protected void addArtifact(int x, int y) {
		super.addObject(x, y, new Pill(x, y));
		totalPills++;
	}

}
