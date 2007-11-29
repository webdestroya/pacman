package code.uci.pacman.controllers;

import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.*;

//controls the action of the game
public class GameController {

	private static GameController gControl = new GameController();
	private GameState state;

	private GameController() {
		state = GameState.getInstance();
	}

	public static GameController getInstance() {
		return gControl;
	}

	/**
	 * updateScore(it's own points) delete itself
	 */
	public void fruitEaten(Fruit fruit) {
		// TODO Auto-generated method stub

	}

	/**
	 * scatter ghosts update score delete itself
	 */
	public void pelletEaten(PowerPellet powerPellet) {
		// TODO Auto-generated method stub

	}

	/**
	 * update score delete itself
	 */
	public void pillEaten(Pill pill) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * change/reset position make not scattered update score
	 * ghost returns to home box and comes out as not a scattered
	 * 
	 */
	public void ghostEaten(Ghost ghost) {
		// TODO Auto-generated method stub

	}

	/**
	 * life lost change position reset ghosts position
	 */
	public void pacManEaten(PacMan pacMan) {
		// TODO Auto-generated method stub

	}

	/**
	 * returns true if a ghost has collided with pac man
	 * @return boolean
	 */
	public boolean hasCollidedWithGhost() {
		return false;
	}
	
	/**
	 * returns true if pac man has eaten something good like a pill, pp, or fruit
	 * @return boolean
	 */
	public boolean hasEatenItem() {
		return false;
	}

}
