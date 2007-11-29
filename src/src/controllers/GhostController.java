package controllers;


import objects.actors.*;
import utilities.ActorController;
import utilities.SpriteImages;

//responsible for and controls ghosts
public class GhostController extends ActorController<String, Ghost> {
	SpriteImages ghostImages;

	public GhostController(SpriteImages ghostImages) {
		this.ghostImages = ghostImages;
		this.constructActors();
	}

	public Ghost getGhost(String name) {
	   return super.getObjectAt(name);
   }

	public void constructActors() {
	   Ghost blinky = new Ghost("Blinky", 300, 300, ghostImages.getImage("blinky"));
	   Ghost pinky = new Ghost("Blinky", 400, 400, ghostImages.getImage("pinky"));
	   this.addObject("Blinky", blinky);
	   this.addObject("Pinky", pinky);
	   //TODO add ghost construction
	}
}
