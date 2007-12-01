package code.uci.pacman.controllers;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.PowerPellet;

/**
 * 
 * @author The Game Team
 * responsible for and controls Power Pellets locations and actions
 */
public class PowerPelletController extends ArtifactController<PowerPellet> {
	public PowerPelletController() {
		this.constructArtifacts("powerpellet");
	}

	public PowerPellet getPelletAt(int x, int y) {
		return super.getObjectAt(x, y);
	}

	@Override
	public void addArtifact(int x, int y) {
		super.addObject(x, y, new PowerPellet(x,y));
		
	}

	public PowerPellet getCollidingPellet(PacMan pacMan) {
		return super.getCollidedWith(pacMan);
	}
}
