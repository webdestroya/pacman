package code.uci.pacman.controllers;

import code.uci.pacman.controllers.utilities.ActorController;
import code.uci.pacman.objects.controllable.Ghost;


/**
 * 
 * @author The Game Team
 * responsible for and controls ghosts
 */
public class GhostController extends ActorController<String, Ghost> {

	public GhostController() {
		this.constructActors();
	}

	public Ghost getGhost(String name) {
	   return super.getObjectAt(name);
    }
	/**
	 * calls move on all ghosts that have isPlayer to false
	 */
	public void moveAIGhosts(){
		
	}
	
	/**
	 * iterates through all the ghosts and calls scatter on dem
	 */
	public void scatter(){
		
	}

	public void constructActors() {
	  // Ghost blinky = new Ghost(PacManGame.getPacImage("blinky.png"), 300, 300, );
	   //Ghost pinky = new Ghost("Blinky", 400, 400, ghostImages.getImage("pinky"));
	   //this.addObject("Blinky", blinky);
	   //this.addObject("Pinky", pinky);
	   //TODO add ghost construction
	}
}
