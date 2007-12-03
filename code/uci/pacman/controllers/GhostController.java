package code.uci.pacman.controllers;

import code.uci.pacman.ai.Blinky;
import code.uci.pacman.ai.Clyde;
import code.uci.pacman.ai.Inky;
import code.uci.pacman.ai.Pinky;
import code.uci.pacman.controllers.utilities.ActorController;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;


/**
 * 
 * @author The Game Team
 * responsible for and controls ghosts
 */
public class GhostController extends ActorController<String, Ghost> {

	public static final double SCATTERSECONDS = 8000;

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
		for(Ghost g : getObjects()){
			if(!g.isPlayer()){
				g.step(g.getMove());
				g.move();
			}
		}
	}
	
	public boolean haveCollidedWithPacMan(PacMan p) {
		return super.getCollidedWith(p).size() > 0;
	}
	
	public void stopWallCollisions(WallController walls) {
		for(Ghost g : getObjects()){
			walls.stopCollision(g);
		}
	}
	
	/**
	 * iterates through all the ghosts and calls scatter on dem
	 */
	public void scatter(){
		for(Ghost g : getObjects()){
			g.scatter();
		}
	}
	
	public void unScatter(){
		for(Ghost g : getObjects()){
			g.unScatter();
		}
	}

	public void constructActors() {
	   addObject("Blinky", new Blinky(250, 250, false));
	   addObject("Pinky", new Pinky(275, 250, false));
	   addObject("Inky", new Inky(300, 250, false));
	   addObject("Clyde", new Clyde(325, 250, false));

	}

	public void respawn() {
		getObjectAt("Blinky").position(250, 250);
		getObjectAt("Pinky").position(275, 250);
		getObjectAt("Inky").position(300, 250);
		getObjectAt("Clyde").position(325, 250);
		
	}

	public boolean haveScattered() {
	    for (Ghost g : super.getObjects()) {
	    	if (g.isScattered()){
	    		return true;
	    	}
	    }
		return false;
	}
}
