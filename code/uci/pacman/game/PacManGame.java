package code.uci.pacman.game;
import ucigame.Image;
import ucigame.Ucigame;

public class PacManGame extends Ucigame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -917916728311505169L;

	public void setup() {
	}
	
	public void draw(){
		
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
