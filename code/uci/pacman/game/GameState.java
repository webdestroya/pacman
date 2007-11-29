package code.uci.pacman.game;

import code.uci.pacman.controllers.*;
import code.uci.pacman.objects.controllable.*;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * @author The Game Team
 * holds all the information about the current state of the game
 */
public class GameState {
	
	private static final int INITIAL_LIVES = 3;
	private static GameState gameInstance;
	private PacMan pacMan;
	private int lives;
	private GhostController ghosts;
	private PillController pills;
	private PowerPelletController pellets;
	private Fruit bonusItem;
	private int score;
	
	public GameState(){
		score = 0;
		lives = INITIAL_LIVES;
	}
	
	
	public static GameState getInstance() {
		return gameInstance;
	}
	
	public void drawState() {
		getPacMan().draw();
		getGhosts().drawObjects();	
		getBonusItem().draw();

		getPills().drawObjects();
		getPellets().drawObjects();
	}

	/**
	 * 
	 * @param score
	 * takes in a value and adds it to the current score
	 */
	void updateScore(int score) {
		this.score += score;
	}

	int getScore() {
		return score;
	}


	/**
	 * called when pacman is eaten
	 */
	void lifeLost() {
		lives--;
	}


	int getLives() {
		return lives;
	}


	void setGhosts(GhostController ghosts) {
		this.ghosts = ghosts;
	}


	GhostController getGhosts() {
		return ghosts;
	}

	PillController getPills() {
		return pills;
	}


	PowerPelletController getPellets() {
		return pellets;
	}


	void pickRandomFruit() {
	}


	Fruit getBonusItem() {
		return bonusItem;
	}

	PacMan getPacMan() {
		return pacMan;
	}
}
