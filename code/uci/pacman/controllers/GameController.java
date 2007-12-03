package code.uci.pacman.controllers;

import java.awt.Point;
import java.util.Collection;

import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.game.PacManGame;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * @author The Game Team controls the action of the game
 */
public class GameController {

	private static GameController gControl;
	private static final Point PACMANSTART = new Point(290, 440);
	private GameState state;
	private PacManGame game;
	
	
	// Static Singleton Methods
	
	public static GameController setInstance(PacManGame pacManGame) {
		gControl = new GameController(pacManGame);
		return gControl;
	}

	public static GameController getInstance() {
		return gControl;
	}
	
	// Instance Methods

	private GameController(PacManGame pacManGame) {
		GameState.setInstance(new GameState());
		state = GameState.getInstance();
		this.game = pacManGame;
	}
	
	public void startGame() {
		state.initialize();
	}
	
	public void drawState() {
		state.draw();
	}

	public void nextMove() {
		moveActors(); //moves the actors for tick
		handleActorCollisions(); //handles the actors colliding
		handleItemCollisions(); //handle item collisions
		checkStageClear(); //handle stage being clear (loading next stage)
	}

	private void moveActors() {
		state.getGhosts().moveAIGhosts();
		state.getPacMan().move();
		state.getWalls().stopCollision(state.getPacMan());
		state.getGhosts().stopWallCollisions(state.getWalls());
	}
	
	private void handleActorCollisions() {
		GhostController ghosts = state.getGhosts();
		PacMan pac = state.getPacMan();
		Collection<Ghost> collidingGhosts = ghosts.getCollidedWith(pac);
		
		if (ghosts.haveCollidedWithPacMan(pac)) { //if the ghosts and Pac-man have collided			
			for (Ghost ghost : collidingGhosts) { //for each ghost that collided
				if (ghost.isScattered()) { //ghost is running in scatter mode
					ghost.eaten(); //ghost has been eaten by Pac-man
				} else {          //if the ghost is on attack
					pac.eaten();  //ghosts have eaten Pac-man and he is dead
					return;       //once Pac-man has been eaten stop the loop
				}
			}
		}
	}
	
	private void handleItemCollisions() {
		//handle pill collisions
		Pill pill = state.getPills().getCollidingPill(state.getPacMan());
		if (pill != null) { pill.eaten(); }
		//handle pellet collisions
		PowerPellet pellet = state.getPellets().getCollidingPellet(state.getPacMan());
		if (pellet != null) { pellet.eaten(); }
		//handle fruit collisions and visibility
		Fruit fruit = state.getFruit();
		if (fruit.collidedWith(state.getPacMan())) { fruit.eaten(); }
		if (shouldShowFruit()) { fruit.showWithTimer(); }
	}

	private void checkStageClear() {
		if (state.levelIsFinished()) {
			state.nextLevel();
			if (state.getLevel() <= 3) {
				state.setupLevel();
			}
			else {
				game.startScene("Scores");
			}
		}
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
	
	public void hideFruit() {
		state.getFruit().hide();
	}
	
	public void unscatterGhosts() {
		state.getGhosts().unScatter();
	}
	
	public void setPacManDirection(Direction d) {
		state.getPacMan().step(d);
	}

	/**
	 * updateScore(it's own points), hide for later
	 */
	public void fruitEaten(Fruit fruit) {
		state.addToScore(fruit.getValue()); // update score
		fruit.hide();
	}

	/**
	 * scatter ghosts, update score, delete itself
	 */
	public void pelletEaten(PowerPellet powerPellet) {
		state.addToScore(PowerPellet.SCOREVALUE);
		state.getPellets().destroy(powerPellet);
		state.getGhosts().scatter();
		game.startScatterTimer();
		SoundController.pelletEaten();
	}

	/**
	 * update score, delete itself
	 */
	public void pillEaten(Pill pill) {
		state.addToScore(Pill.SCOREVALUE);
		state.getPills().destroy(pill);
		SoundController.pillEaten();
	}

	/**
	 * 
	 * change/reset position make not scattered update score ghost returns to
	 * home box and comes out as not a scattered
	 * 
	 */
	public void ghostEaten(Ghost ghost) {
		ghost.respawnInCage();  //restart in cage
		state.addToScore(ghost.getValue());//TODO make ghosts worth more if you eat them in the same round
	}

	/**
	 * life lost change position reset ghosts position
	 */
	public void pacManEaten(PacMan pacMan) {
		if (state.getLives() > 0) { // if Pac-man has more lives
			state.lifeLost();
			state.getFruit().hide();
			state.getPacMan().position(PACMANSTART);
			state.getGhosts().respawn();
		}
		else { //if Pac-man has died one too many times
			game.startScene("GameOver");
		}	
	}


	public PacManGame getPacInstance() {
		return game;
	}

}
