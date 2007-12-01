package code.uci.pacman.controllers;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.stationary.*;


/**
 * 
 * @author The Game Team
 * responsible for and controls wall locations
 */
public class WallController extends ArtifactController<Wall> {
	public WallController() {
	   this.constructArtifacts("wall");
    }
	
	public Wall getWallAt(int x, int y) {
		return super.getObjectAt(x, y);
	}

	@Override
	public void addArtifact(int x, int y) {
		super.addObject(x, y, new Wall(x, y));
	}
}
