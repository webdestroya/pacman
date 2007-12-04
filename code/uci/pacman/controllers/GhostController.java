package code.uci.pacman.controllers;

import code.uci.pacman.ai.Blinky;
import code.uci.pacman.ai.Clyde;
import code.uci.pacman.ai.Inky;
import code.uci.pacman.ai.Pinky;
import code.uci.pacman.controllers.utilities.ActorController;
import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;

/**
 * 
 * This controller is responsible for handling any and all Ghost related actions
 * within the game. The aim of this controller is to abstract and isolate all
 * ghost functionality from the rest of the game. Whenever the game requires
 * anything of the ghosts, the command is routed through here and this
 * controller will make the appropriate adjustments.
 * 
 * This controller handles moving ghosts, checking ghost collisions, scattering
 * and unscattering ghosts, constructing the ghosts on the screen, and 
 * also holds any information about the ghosts that may need to be accessed.
 * 
 * @author The Game Team
 * 
 */
public class GhostController extends ActorController<String, Ghost> {

	public static final double SCATTERSECONDS = 8000;

	public GhostController() {
		this.constructActors();
	}

	/**
	 * Constructs each Ghost object and adds them to the collection
	 * of ghosts that exist within the game world. Each ghost
	 * is initialized and placed onto the game within the special 
	 * ghost cage. This method is generally invoked when the controller
	 * is created.
	 * 
	 */
	public void constructActors() {
		addObject("Blinky", new Blinky(250, 250, false));
		addObject("Pinky", new Pinky(275, 250, false));
		addObject("Inky", new Inky(300, 250, false));
		addObject("Clyde", new Clyde(325, 250, false));

	}

	/**
	 * 
	 * Retrieves a given ghost based on his name. Each ghost contains
	 * all the information for that particular object. Typically this is used
	 * when the game needs to access information about an individual ghost.
	 * 
	 * @param name the name of the ghost to retrieve
	 * @return the ghost that was specified
	 */
	public Ghost getGhost(String name) {
		return super.getObjectAt(name);
	}

	/**
	 * 
	 * Determines if any of the ghosts in the game world
	 * are currently in collision with PacMan.
	 * 
	 * @param p the instance of PacMan to check collision against
	 * @return true if a ghost has collided with PacMan; false otherwise.
	 */
	public boolean haveCollidedWithPacMan(PacMan p) {
		return super.getCollidedWith(p).size() > 0;
	}

	/**
	 * Commands each ghost that is computer-controlled to move in the direction
	 * that has been determined by the ghost's movement artificial intelligence. Note
	 * that this will only move computer-controlled ghosts and has no effect on
	 * network-controlled ghosts.
	 * 
	 * This method is invoked when the ghosts need to move for the "tick". Called
	 * by the game controller.
	 * 
	 */
	public void moveAIGhosts() {
		for (Ghost g : getObjects()) {
			if (!g.isPlayer()) {
				Direction nextMove = g.getMove();
				g.step(nextMove);
				g.move();
			}
		}
	}

	/**
	 * Commands the ghosts to all respawn at their respective starting positions
	 * within the game world. This happens when all the ghosts need to be
	 * restarted suddenly such as when PacMan is eaten or if the game has been reset.
	 * 
	 */
	public void respawn() {
		getObjectAt("Blinky").position(250, 250);
		getObjectAt("Pinky").position(275, 250);
		getObjectAt("Inky").position(300, 250);
		getObjectAt("Clyde").position(325, 250);
	}

	/**
	 * Command each ghost within the game world to move to scatter mode which
	 * means that the ghost should begin to run from PacMan and PacMan can now
	 * eat ghosts for points.
	 * 
	 * This method is invoked usually from the game controller when PacMan
	 * eats a power pellet item that scares the ghosts into scatter mode.
	 * 
	 */
	public void scatter() {
		for (Ghost g : getObjects()) {
			g.scatter();
		}
	}

	/**
	 * 
	 * Commands the ghost to recognize the walls as boundaries that 
	 * cannot be passed through. Once this has been invoked in a draw method,
	 * the ghosts can no longer move through walls.
	 * 
	 * @param walls
	 */
	public void stopWallCollisions(WallController walls) {
		walls.stopCollisions(super.getObjects());
	}

	/**
	 * 
	 * Commands the ghosts in the game world to all return to attack mode
	 * meaning that they are no longer scattered and can resume hunting PacMan.
	 * PacMan will now risk being eaten again by the ghosts.
	 * 
	 */
	public void unScatter() {
		for (Ghost g : getObjects()) {
			g.unScatter();
		}
	}
}
