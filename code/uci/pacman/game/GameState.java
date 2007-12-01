package code.uci.pacman.game;

import code.uci.pacman.controllers.*;
import code.uci.pacman.objects.controllable.*;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * @author The Game Team holds all the information about the current state of
 *         the game
 */
public class GameState {

	private static final int INITIAL_LIVES = 3;
	private static GameState gameInstance;
	private PacMan pacMan;
	private int lives;
	private GhostController ghosts;
	private PillController pills;
	private PowerPelletController pellets;
	private WallController walls;
	private Fruit bonusItem;
	private int score;
	private int level;

	public GameState() {
		score = 0;
		lives = INITIAL_LIVES;
		level = 1;
	}

	public void setupLevel() {
		pacMan = new PacMan(50, 50);
		pills = new PillController();
		bonusItem = new Fruit(300, 300, 100);
		//walls = new WallController();
		// pellets = new PowerPelletController(level);
	}

	public static GameState getInstance() {
		return gameInstance;
	}

	public static void setInstance(GameState gameState) {
		gameInstance = gameState;
	}

	public void drawState() {
		pacMan.draw();
		pills.drawObjects();
		//walls.drawObjects();
		// this.ghosts.drawObjects();
		bonusItem.draw();
		//
		// this.pellets.drawObjects();
	}

	/**
	 * 
	 * @param score
	 *            takes in a value and adds it to the current score
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

	public Fruit getFruit() {
		return bonusItem;
	}

	public PacMan getPacMan() {
		return pacMan;
	}

	private WallController getWalls() {
		return walls;
	}

	public void nextLevel() {
		level++;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
