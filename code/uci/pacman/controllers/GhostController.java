package code.uci.pacman.controllers;

import code.uci.pacman.ai.*;
import code.uci.pacman.controllers.utilities.ActorController;
import code.uci.pacman.game.Direction;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.game.GameState;
import java.awt.Point;

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
	protected void constructActors() {
		addObject("Blinky", new Blinky(getStartPos(1).x, getStartPos(1).y, false));
		addObject("Pinky", new Pinky(getStartPos(2).x, getStartPos(2).y, false));
		addObject("Inky", new Inky(getStartPos(3).x, getStartPos(3).y, false));
		addObject("Clyde", new Clyde(getStartPos(4).x, getStartPos(4).y, false));

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
			// DO NOT DO if( !g.isPlayer) - you will mess up the game
			Direction nextMove = g.getMove();
			g.step(nextMove);
			g.move();
		}
	}

	/**
	 * Commands the ghosts to all respawn at their respective starting positions
	 * within the game world. This happens when all the ghosts need to be
	 * restarted suddenly such as when PacMan is eaten or if the game has been reset.
	 * 
	 */
	public void respawn() {
		unscatter();
		getObjectAt("Blinky").position(getStartPos(1));
		getObjectAt("Pinky").position(getStartPos(2));
		getObjectAt("Inky").position(getStartPos(3));
		getObjectAt("Clyde").position(getStartPos(4));
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
	public void unscatter() {
		for (Ghost g : getObjects()) {
			g.unScatter();
		}
	}
	
	private Point getStartPos(int ghost)
	{
		if(GameState.getInstance().getLevel() < 3)
		{
			if(ghost == 1)
				return new Point(250,250);
			else if(ghost == 2)
				return new Point(275,250);
			else if(ghost == 3)
				return new Point (300,250);
			else
				return new Point(325,250);
		}
		else
		{
			if(ghost == 1)
				return new Point(265,310);
			else if(ghost == 2)
				return new Point(275,270);
			else if(ghost == 3)
				return new Point (300,310);
			else
				return new Point(310,270);
		}
	}
}
