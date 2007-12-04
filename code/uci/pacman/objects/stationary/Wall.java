package code.uci.pacman.objects.stationary;


import code.uci.pacman.objects.StationaryObject;

/**
 * 
 * @author Team Objects/AI
 * this is one square image
 *
 */
public class Wall extends StationaryObject {

	/**
	 * 
	 * @author Objects Team
	 * constructor sets the wall dimensions and position in the game
	 * overlays the wall with the correct level image
	 * 
	 */
	public Wall(int x, int y, int width, int height) {
		super(width, height);
		super.addFrame("level1.png", x, y);
		super.position(x, y);
		// TODO Auto-generated constructor stub
	}

}
