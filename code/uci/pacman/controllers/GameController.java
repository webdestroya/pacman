package code.uci.pacman.controllers;

import java.awt.Point;
import java.util.Collection;

import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.game.PacManGame;
import code.uci.pacman.game.ScreenMode;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * This class is central to the game and is primarily responsible for performing
 * actions on the game board. Most of the game functionality is the result of
 * calling methods in this class in order to affect the GameState. Note that the
 * GameState is not really intended to be accessed directly for anything but
 * rather any changes should be routed through this controller.
 * 
 * Controls all game functionality such as: starting the game, drawing the game
 * state, moving the objects each tick, handling collisions between objects,
 * updating the score checking when the stage is clear, handling individual
 * collision events and more
 * 
 * @author The Game Team controls the action of the game
 */
public class GameController {

	private static GameController gControl;
	private static final Point PACMANSTART = new Point(290, 440);

	public static GameController getInstance() {
		return gControl;
	}

	public static GameController setInstance(PacManGame pacManGame) {
		gControl = new GameController(pacManGame);
		return gControl;
	}

	// Static Singleton Methods

	private GameState state;

	private PacManGame game;

	// Instance Methods

	private GameController(PacManGame pacManGame) {
		GameState.setInstance(new GameState());
		state = GameState.getInstance();
		this.game = pacManGame;
	}

	/**
	 * Invokes the draw method on every object within the game. This function
	 * should only be called within another draw function or within the final
	 * draw function within the main game class PacManGame. Drawing is used to
	 * actually display the different objects including walls, ghosts, and
	 * others onto the board.
	 * 
	 */
	public void drawState() {
		state.draw();
	}

	/**
	 * Handles the event of a fruit being eaten on screen by PacMan. This method
	 * should be called from within an "eaten" implementation within a game
	 * object that implements Eatable.
	 * 
	 * When invoked, this method will adjust PacMan's score with the point value
	 * for the fruit as well as hide the fruit because it has been eaten.
	 * 
	 */
	public void fruitEaten(Fruit fruit) {
		SoundController.fruitEaten();
		state.addToScore(fruit.getValue()); // update score
		fruit.hide();
	}

	/**
	 * 
	 * Handles the event of a ghost being eaten on screen by PacMan. This method
	 * should be called from within an "eaten" implementation within a game
	 * object that implements Eatable.
	 * 
	 * When invoked, this method will respawn the ghost back into the cage as
	 * well as add the ghost's score value to the game score.
	 * 
	 */
	public void ghostEaten(Ghost ghost) {
		SoundController.ghostEaten();
		ghost.respawnInCage(); // restart in cage
		state.addToScore(ghost.getValue());
		// TODO make ghosts worth more if you eat them in the same round
	}

	/**
	 * Hides the fruit object on the screen when called. Used when the fruit
	 * object no longer needs to be displayed on screen. When hidden, PacMan can
	 * no longer eat the fruit or collide with it until the fruit is shown once
	 * more via controller method 'showWithTimer()'
	 * 
	 */
	public void hideFruit() {
		state.getFruit().hide();
	}

	/**
	 * Handles the events that occur each game "tick". Each tick, a variety of
	 * events need to take place including moving PacMan, moving the ghosts,
	 * handling collisions between game objects and checking to see if the stage
	 * has been cleared. This function when called handles all these events for
	 * a single game tick. This is usually called within a draw method because
	 * this should be invoked right before drawing the GameState.
	 * 
	 */
	public void nextMove() {
		moveActors(); // moves the actors for tick
		handleActorCollisions(); // handles the actors colliding
		handleItemCollisions(); // handle item collisions
		checkStageClear(); // handle stage being clear (loading next stage)
	}

	/**
	 * Handles the event of PacMan being eaten on screen by a ghost. This method
	 * should be called from within an "eaten" implementation within a game
	 * object that implements Eatable.
	 * 
	 * When invoked, this method will subtract a life from PacMan, hide the
	 * fruit if it is visible, and respawn PacMan at the starting position.
	 * 
	 * If PacMan has no more lives, this function will also automatically show
	 * the Gameover screen to indicate that the game has been finished.
	 * 
	 */
	public void pacManEaten(PacMan pacMan) {
		SoundController.pacmanEaten();
		if (state.getLives() > 0) { // if Pac-man has more lives
			state.lifeLost();
			state.getFruit().hide();
			state.getPacMan().position(PACMANSTART);
			state.getGhosts().respawn();
		} else { // if Pac-man has died one too many times
			game.showGameOverScreen();
		}
	}

	/**
	 * Handles the event of a pellet being eaten on screen by PacMan. This
	 * method should be called from within an "eaten" implementation within a
	 * game object that implements Eatable.
	 * 
	 * When invoked, this method will adjust the score by the value of the
	 * pellet, destroy the pellet to remove it from the game world, scatter all
	 * the ghosts, and play the appropriate sound.
	 * 
	 */
	public void pelletEaten(PowerPellet powerPellet) {
		state.addToScore(PowerPellet.SCOREVALUE);
		state.getPellets().destroy(powerPellet);
		state.getGhosts().scatter();
		game.startScatterTimer();
		SoundController.pelletEaten();
	}

	/**
	 * Handles the event of a pill being eaten on screen by PacMan. This method
	 * should be called from within an "eaten" implementation within a game
	 * object that implements Eatable.
	 * 
	 * When invoked, this method will adjust the score by the value of the pill,
	 * destroy the pill to remove it from the game world, and play the
	 * appropriate sound.
	 * 
	 */
	public void pillEaten(Pill pill) {
		state.addToScore(Pill.SCOREVALUE);
		state.getPills().destroy(pill);
		SoundController.pillEaten();
	}

	/**
	 * 
	 * Commands PacMan to move in the specified direction if he is able to do so
	 * without colliding into a wall. Note that if PacMan is commanded to move
	 * into a direction he cannot go in then the direction will be unchanged.
	 * This function is invoked primarily based on the keyboard input on the
	 * game by the user.
	 * 
	 * @param d
	 *            The direction in which PacMan will now move.
	 * 
	 */
	public void setPacManDirection(Direction d) {
		state.getPacMan().step(d);
	}

	/**
	 * Starts the game by setting the score and lives to default values, setting
	 * the game to level 1, and completely reinitializing all the different
	 * objects within the game. This method is intended to start the game in the
	 * beginning but can also be used to restart the game at any time.
	 * 
	 */
	public void startGame() {
		state.initialize();
		// we need to wait 5 seconds for the intro music to stop playing like in real pacman.
		SoundController.gameStarted();
	}

	/**
	 * Commands all the ghosts to attack PacMan and no longer run in scatter
	 * mode. This command calls the "unscatter()" method on each ghost on the
	 * screen which means ghosts can again chase and kill PacMan.
	 * 
	 */
	public void unscatterGhosts() {
		state.getGhosts().unscatter();
	}

	/**
	 * 
	 * Returns to the caller the current instance of the PacManGame that is
	 * being played by the user. This is typically needed when a function needs
	 * to be called that exists directly on the game screen itself.
	 * 
	 * @return the current instance of the PacManGame
	 */
	public PacManGame getPacInstance() {
		return game;
	}

	/* Private Methods */

	private void checkStageClear() {
		if (state.stageHasBeenCleared()) {
			state.nextStage();
			if (state.getLevel() <= 3) {
				state.setupLevel();
			} else {
				game.showScoresScreen();
			}
		}
	}

	private void handleActorCollisions() {
		GhostController ghosts = state.getGhosts();
		PacMan pac = state.getPacMan();
		Collection<Ghost> collidingGhosts = ghosts.getCollidedWith(pac);

		if (ghosts.haveCollidedWithPacMan(pac)) { // if the ghosts and Pac-man
			// have collided
			for (Ghost ghost : collidingGhosts) { // for each ghost that
				// collided
				if (ghost.isScattered()) { // ghost is running in scatter mode
					ghost.eaten(); // ghost has been eaten by Pac-man
				} else { // if the ghost is on attack
					pac.eaten(); // ghosts have eaten Pac-man and he is dead
					return; // once Pac-man has been eaten stop the loop
				}
			}
		}
	}

	private void handleItemCollisions() {
		// handle pill collisions
		Pill pill = state.getPills().getCollidingPill(state.getPacMan());
		if (pill != null) {
			pill.eaten();
		}
		// handle pellet collisions
		PowerPellet pellet = state.getPellets().getCollidingPellet(state.getPacMan());
		if (pellet != null) {
			pellet.eaten();
		}
		// handle fruit collisions and visibility
		Fruit fruit = state.getFruit();
		if (fruit.collidedWith(state.getPacMan())) {
			fruit.eaten();
		}
		if (shouldShowFruit()) {
			fruit.showWithTimer();
		}
	}

	private void moveActors() {
		// move the actors
		state.getGhosts().moveAIGhosts();
		state.getPacMan().move();
		// stop their collisions with walls NOTE this must go after the move
		// statements
		state.getWalls().stopCollisions(state.getPacMan());
		state.getGhosts().stopWallCollisions(state.getWalls());
	}

	private boolean shouldShowFruit() {
		int initialPills = state.getPills().getInitialCount();
		if (initialPills - state.getPills().getPillCount() == initialPills / 3 && state.getFruit().getFruitEaten() == 0) {
			return true;
		} else if (initialPills - state.getPills().getPillCount() == (initialPills / 3) * 2 && state.getFruit().getFruitEaten() <= 1) {
			return true;
		} else
			return false;
	}

}
