package code.uci.pacman.game;

import java.io.IOException;

import com.sun.corba.se.spi.ior.MakeImmutable;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.gui.ScoreBoard;
import code.uci.pacman.gui.TopScores;
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
		
		try {
			Grid.grid();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void drawScores(){
		canvas.clear();
		topScores.draw();
	}
	
	public void drawMenu(){
		
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
