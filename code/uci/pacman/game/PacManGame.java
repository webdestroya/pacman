package code.uci.pacman.game;
import code.uci.pacman.controllers.GameController;
import ucigame.Image;
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

	public void setup() {
		state = new GameState();
		window.size(600, 600);
	}
	
	public void draw(){
		canvas.clear();
		state.nextMove();
		state.drawState();
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
