package code.uci.pacman.controllers2;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.stationary.PowerPellet;


public class PowerPelletController extends ArtifactController<PowerPellet> {
	public PowerPelletController() {
		this.constructArtifacts();
	}

	@Override
	public void constructArtifacts() {
		addPellet(200, 400);		
	}
	
	public void addPellet(int x, int y) {
	//	super.addObject(x, y, new PowerPellet(super.artifactImage, x, y));
	}
	
	public PowerPellet getPelletAt(int x, int y) {
		return super.getObjectAt(x, y);
	}
}
