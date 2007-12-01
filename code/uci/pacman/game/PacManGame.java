package code.uci.pacman.game;
import com.sun.corba.se.spi.ior.MakeImmutable;

import code.uci.pacman.gui.ScoreBoard;
import ucigame.Image;
import ucigame.Sprite;
import ucigame.Ucigame;
/**
 * 
 * @author The Game Team
 * The main game class
 */
public class PacManGame extends Ucigame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -917916728311505169L;
	private GameState state;
	private ScoreBoard scoreBoard;

	public void setup() {
		GameState.setInstance(new GameState());
		state = GameState.getInstance();
		state.setupLevel();
		window.size(600, 600);
		window.title("Pac Man Fever");
		scoreBoard = new ScoreBoard();
	}
	
	public void draw(){
		canvas.clear();
		state.nextMove();
		state.drawState();
		scoreBoard.draw();
		//canvas.putText("Score: " + state.getScore(), 200, 500);
	}
	
	public static Sprite makeGameSprite(String image){
		PacManGame instance = new PacManGame();
		Image i = getPacImage(image);
		Sprite s = instance.makeSprite(i.width(), i.height());
		s.addFrame(i, 0, 0);
		return s;
	}

	
	public void onKeyPress() {
//		// Arrow keys and WASD keys move the paddle
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

	public static Image getPacImage(String path) {
		PacManGame instance = new PacManGame();
		return instance.getImage(path);
	}

}
