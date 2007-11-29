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
	public void updateScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}


	/**
	 * called when pacman is eaten
	 */
	public void lifeLost() {
		lives--;
	}


	public int getLives() {
		return lives;
	}


	public void setGhosts(GhostController ghosts) {
		this.ghosts = ghosts;
	}


	public GhostController getGhosts() {
		return ghosts;
	}

	public PillController getPills() {
		return pills;
	}


	public PowerPelletController getPellets() {
		return pellets;
	}


	public void pickRandomFruit() {
	}


	public Fruit getBonusItem() {
		return bonusItem;
	}

	public PacMan getPacMan() {
		return pacMan;
	}
}
