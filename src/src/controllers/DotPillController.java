package controllers;

import objects.actors.*;
import utilities.*;

public class DotPillController extends ArtifactController<DotPill> {
	public DotPillController(SpriteImages sprites) {
	   super.artifactImage = sprites.getImage("dotpill");
	   this.constructArtifacts();
    }
	
	private void addPill(int x, int y) {
		super.addObject(x, y, new DotPill(super.artifactImage, x, y));
	}
	
	public DotPill getPillAt(int x, int y) {
		return super.getObjectAt(x, y);
	}

	@Override
	public void constructArtifacts() {
		addPill(100, 400); //TODO fill in pills here
	}
}
