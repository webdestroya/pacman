package game;

import controllers.*;
import objects.controllable.*;
import objects.stationary.*;

//holds all the information about the current state of the game
public class GameState {
	private static GameState gameInstance;
	PacMan pacMan;
	GhostController ghosts;
	PillController pills;
	PowerPelletController pellets;
	Fruit bonusItem;
	
	
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
}
