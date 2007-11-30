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
		this.createObjects();
	}
	
	private void createObjects() {

	}
	
	
	public static GameState getInstance() {
		return gameInstance;
	}
	
	public void drawState() {
		this.pacMan.draw();
		this.ghosts.drawObjects();	
		this.bonusItem.draw();

		this.pills.drawObjects();
		this.pellets.drawObjects();
	}

	/**
	 * 
	 * @param score
	 * takes in a value and adds it to the current score
	 */
	public void addToScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}


	/**
	 * called when Pac-man is eaten
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


	public void generateRandomFruit() {
		//pick new fruit
	    this.bonusItem.hide(); //hides the new fruit
	}


	public Fruit getFruit() {
		return bonusItem;
	}

	public PacMan getPacMan() {
		return pacMan;
	}
}
