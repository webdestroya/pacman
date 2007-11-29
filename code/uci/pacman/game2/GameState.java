package code.uci.pacman.game2;

import code.uci.pacman.controllers1.*;
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
