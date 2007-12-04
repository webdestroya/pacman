package code.uci.pacman.game;

import code.uci.pacman.controllers.*;
import code.uci.pacman.objects.controllable.*;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * This is the model that encompasses all information about the current state of
 * the game world. This game state contains all the objects within the game
 * world, the score, lives, and level along with all information about the
 * current state of these objects.
 * 
 * This class is a singleton and is usually the place where the actual changes
 * are taking place when a controller invokes an action. The game controller
 * usually is the object that should be used to interact with this state.
 * 
 * @author The Game Team
 * 
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

	public static GameState getInstance() {
		return gameInstance;
	}

	public static void setInstance(GameState gameState) {
		gameInstance = gameState;
	}

	/**
	 * Initializes all the various game related objects and defaults the score,
	 * lives and level to their initial values. This method is generally used
	 * when the game is about to begin or to restart the game. This method
	 * should not be invoked directly but instead should be used through
	 * invoking methods in the game controller.
	 * 
	 */
	public void initialize() {
		score = 0;
		lives = INITIAL_LIVES;
		level = 1;
		setupLevel();
	}

	/**
	 * Constructs all the game objects that exist for the upcoming game stage.
	 * This is called at the beginning of the game and on each subsequent level.
	 */
	public void setupLevel() {
		pacMan = new PacMan(290, 440);
		pills = new PillController();
		walls = new WallController();
		pellets = new PowerPelletController();
		ghosts = new GhostController();
		bonusItem = new Fruit(300, 330, 100);
		SoundController.startAmbient();
	}

	/**
	 * Draws every object within the game onto the screen. Invoked through the
	 * game controller and should only be called in a draw function.
	 */
	public void draw() {
		walls.drawObjects();
		pacMan.draw();
		pills.drawObjects();
		ghosts.drawObjects();
		pellets.drawObjects();
		bonusItem.draw();
	}

	/**
	 * Determines if the level has been completed which is when all the pills on
	 * the stage have been eaten by PacMan. This method is used in the game
	 * controller.
	 * 
	 * @return if the stage has been cleared.
	 */
	public boolean stageHasBeenCleared() {
		return this.getPills().getPillCount() == 0;
	}

	/**
	 * 
	 * Adds the specified value to the existing score.
	 * 
	 * @param score
	 *            amount of points to add
	 */
	public void addToScore(int score) {
		this.score += score;
	}

	/**
	 * Gets the current game score.
	 * 
	 * @return the score for the game.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Removes a life from PacMan. Called from the controller when PacMan is
	 * eaten by a ghost.
	 */
	public void lifeLost() {
		lives--;
	}

	/**
	 * Gets the total number of remaining lives for PacMan.
	 * 
	 * @return the number of lives PacMan has remaining.
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * 
	 * Gets the controller responsible for all actions relating
	 * to the ghosts within the game world.
	 * 
	 * @return the ghosts controller
	 */ 
	public GhostController getGhosts() {
		return ghosts;
	}

	/**
	 * 
	 * Gets the controller responsible for all actions relating
	 * to the pills within the game world.
	 * 
	 * @return the pills controller
	 */ 
	public PillController getPills() {
		return pills;
	}

	/**
	 * 
	 * Gets the controller responsible for all actions relating
	 * to the pellets within the game world.
	 * 
	 * @return the pellets controller
	 */ 
	public PowerPelletController getPellets() {
		return pellets;
	}

	/**
	 * 
	 * Gets the instance of the fruit object currently within the game world.
	 * 
	 * @return the fruit inside the game world.
	 */
	public Fruit getFruit() {
		return bonusItem;
	}

	/**
	 * 
	 * Gets the instance of PacMan currently within the game world.
	 * 
	 * @return the PacMan instance inside the game world.
	 */
	public PacMan getPacMan() {
		return pacMan;
	}


	/**
	 * 
	 * Gets the controller responsible for all actions relating
	 * to the walls within the game world.
	 * 
	 * @return the walls controller
	 */ 
	public WallController getWalls() {
		return walls;
	}

	/**
	 * Increments the game to the next stage.
	 */
	public void nextStage() {
		level++;
	}

	/**
	 * Gets the current stage number for the game.
	 * @return the current stage
	 */
	public int getLevel() {
		return level;
	}
}
