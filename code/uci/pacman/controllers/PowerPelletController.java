package code.uci.pacman.controllers;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.PowerPellet;

/**
 * 
 * This controller is responsible for all actions and functionality related to
 * the pellets located in the game world. This controller handles
 * constructing pellets, and colliding with pellets.
 * 
 * @author The Game Team
 * 
 */
public class PowerPelletController extends ArtifactController<PowerPellet> {
	public PowerPelletController() {
		this.constructArtifacts("powerpellet");
	}

	/**
	 * Calculates and determines the pellet that has currently collided
	 * with PacMan. This is used to handle collisions with pellets
	 * within the game controller.
	 * 
	 * @param pacMan the instance of PacMan
	 * @return the pellet that has collided with PacMan
	 
	 */
	public PowerPellet getCollidingPellet(PacMan pacMan) {
		return super.getCollidedWith(pacMan);
	}
	
	protected void addArtifact(int x, int y) {
		super.addObject(x, y, new PowerPellet(x,y));
		
	}
}
