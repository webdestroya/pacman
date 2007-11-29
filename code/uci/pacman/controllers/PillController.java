package code.uci.pacman.controllers;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.stationary.*;


/**
 * 
 * @author The Game Team
 * responsible for and controls pill locations and actions
 */
public class PillController extends ArtifactController<Pill> {
	public PillController() {
	   this.constructArtifacts();
    }
	
	private void addPill(int x, int y) {
		//super.addObject(x, y, new Pill(super.artifactImage, x, y));
	}
	
	public Pill getPillAt(int x, int y) {
		return super.getObjectAt(x, y);
	}

	@Override
	public void constructArtifacts() {
		addPill(100, 400); //TODO fill in pills here
	}
}
