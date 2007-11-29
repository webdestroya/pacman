package controllers;

import objects.actors.PowerPellet;
import utilities.ArtifactController;
import utilities.SpriteImages;

public class PowerPelletController extends ArtifactController<PowerPellet> {
	public PowerPelletController(SpriteImages images) {
		super.artifactImage = images.getImage("powerpellet");
		this.constructArtifacts();
	}

	@Override
	public void constructArtifacts() {
		addPellet(200, 400);		
	}
	
	public void addPellet(int x, int y) {
		super.addObject(x, y, new PowerPellet(super.artifactImage, x, y));
	}
	
	public PowerPellet getPelletAt(int x, int y) {
		return super.getObjectAt(x, y);
	}
}
