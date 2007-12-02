package code.uci.pacman.game;

import java.io.IOException;

import com.sun.corba.se.spi.ior.MakeImmutable;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.controllers.GhostController;
import code.uci.pacman.gui.ScoreBoard;
import code.uci.pacman.gui.TopScores;
import code.uci.pacman.objects.stationary.Fruit;
import ucigame.Image;
import ucigame.Sprite;
import ucigame.Ucigame;

/**
 * 
 * @author The Game Team The main game class
 */
public class PacManGame extends Ucigame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -917916728311505169L;
	private GameState state;
	private ScoreBoard scoreBoard;
	private TopScores topScores;
	private GameController control;

	public void setup() {
		
		//generatePositions(); EVERYBODY LEAVE THIS COMMENTED OUT! Call Thomas if you want to know what it does.
		control = GameController.setInstance(this);
		state = GameState.getInstance();
		state.setupLevel();
		framerate(20);
		window.size(600, 650);
		canvas.background(0, 0, 0);
		window.title("Pac Man Fever");
		scoreBoard = new ScoreBoard();
		topScores = new TopScores();
		startScene("Game");
	}

	private void generatePositions() {
		try {
			Grid.grid();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drawScores(){
		canvas.clear();
		topScores.draw();
	}
	
	public void drawMenu(){
		
	}
	
	public void startFruitTimer(){
		startTimer("removeFruit",Fruit.ONSCREENLENGTH);
	}
	
	public void removeFruitTimer(){
		stopTimer("removeFruit");
		state.getFruit().hide();
	}
	
	public void startScatterTimer(){
		startTimer("unScatterGhosts",GhostController.SCATTERSECONDS);
	}
	
	public void unScatterGhostsTimer(){
		stopTimer("unScatterGhosts");
		state.getGhosts().unScatter();
	}

	public void drawGame() {
		canvas.clear();
		control.nextMove();
		state.drawState();
		scoreBoard.draw();
	}


	public void onKeyPressGame() {
		// // Arrow keys and WASD keys move the paddle
		if (keyboard.isDown(keyboard.UP, keyboard.W))
			state.getPacMan().step(Direction.UP);
		if (keyboard.isDown(keyboard.DOWN, keyboard.S))
			state.getPacMan().step(Direction.DOWN);
		if (keyboard.isDown(keyboard.LEFT, keyboard.A))
			state.getPacMan().step(Direction.LEFT);
		if (keyboard.isDown(keyboard.RIGHT, keyboard.D))
			state.getPacMan().step(Direction.RIGHT);
	}

	public PacManGame() {

	}

	public Sprite makeSpriteFromPath(String string) {
		return makeSprite(getImage(string));
	}

}
