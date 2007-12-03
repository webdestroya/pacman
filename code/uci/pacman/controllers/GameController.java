package code.uci.pacman.controllers;

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

	public void nextMove() {
		moveActors(); //moves the actors for tick
		handleItemCollisions(); //handle item collisions
		checkStageClear(); //handle stage being clear (loading next stage)
	}

	private void moveActors() {
		state.getGhosts().moveAIGhosts();
		state.getPacMan().move();
		state.getWalls().stopCollision(state.getPacMan());
	}
	
	private void handleItemCollisions() {
		Pill p = state.getPills().getCollidingPill(state.getPacMan());
		if (p != null) { p.eaten(); }
		
		PowerPellet pellet = state.getPellets().getCollidingPellet(state.getPacMan());
		if (pellet != null) { pellet.eaten(); }
		
		if (shouldShowFruit()) {
			state.getFruit().show();
			game.startFruitTimer();
		}
		
		if (state.getFruit().collidedPerfect(state.getPacMan())) {
			state.getFruit().eaten();
		}
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
		ghost.position(300, 300);
		ghost.unScatter();
		state.addToScore(ghost.getValue());//TODO make ghosts worth more if you eat them in the same round

	}

	/**
	 * life lost change position reset ghosts position
	 */
	public void pacManEaten(PacMan pacMan) {
			state.lifeLost();
			state.getPacMan().position(290, 440);
			state.getGhosts().respawn();
	}


	public PacManGame getPacInstance() {
		return game;
	}

}
