package code.uci.pacman.game;

import code.uci.pacman.controllers.*;
import code.uci.pacman.objects.controllable.*;
import code.uci.pacman.objects.stationary.*;



//holds all the information about the current state of the game
public class GameState {
	private static GameState gameInstance;
	PacMan pacMan;
	GhostController ghosts;
	PillController pills;
	PowerPelletController pellets;
	Fruit bonusItem;
	private int score;
	
	public GameState(){
		score = 0;
	}
	
	
	public static GameState getInstance() {
		return gameInstance;
	}
	
	public void drawState() {
		pacMan.draw();
		ghosts.drawObjects();	
		bonusItem.draw();

		pills.drawObjects();
		pellets.drawObjects();
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
}
