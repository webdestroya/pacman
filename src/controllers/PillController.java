package controllers;

import objects.stationary.Pill;
import controllers.utilities.ArtifactController;

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
