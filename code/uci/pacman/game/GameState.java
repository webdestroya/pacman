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
	private Wall wall;
	private Fruit bonusItem;
	private int score;
	private int level;
	
	public void initialize() {
		score = 0;
		lives = INITIAL_LIVES;
		level = 1;
		setupLevel();
	}

	public void setupLevel() {
		pacMan = new PacMan(290, 440);
		pills = new PillController();
		walls = new WallController();
		pellets = new PowerPelletController();
		ghosts = new GhostController();
		bonusItem = new Fruit(300, 330, 100);
		SoundController.startAmbient();
	}

	public static GameState getInstance() {
		return gameInstance;
	}

	public static void setInstance(GameState gameState) {
		gameInstance = gameState;
	}

	public void draw() {
		walls.drawObjects();
		pacMan.draw();
		pills.drawObjects();
		ghosts.drawObjects();
		pellets.drawObjects();
		bonusItem.draw();
	}
	
	public boolean levelIsFinished() {
		return this.getPills().getPillCount() == 0;
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

	public WallController getWalls() {
		return walls;
	}

	public void nextLevel() {
		level++;
	}

	public int getLevel() {
		return level;
	}

	public Wall getWall() {
		return wall;
	}
}
