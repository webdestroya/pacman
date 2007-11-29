package code.uci.pacman.game;
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
		state.drawState();
	}
	
	public void onKeyPress(){
		
	}

	public PacManGame() {

	}

	public static Image getPacImage(String path) {
		PacManGame instance = new PacManGame();
		return instance.getImage(path);
	}

}
