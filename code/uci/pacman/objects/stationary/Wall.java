package code.uci.pacman.objects.stationary;


import code.uci.pacman.objects.StationaryObject;

/**
 * These are the square images making up the walls of the game
 * 
 * @author Team Objects/AI
 *
 */
public class Wall extends StationaryObject {

	/**
	 * 
	 * constructor sets the wall dimensions and position in the game
	 * overlays the wall with the correct level image
	 * 
	 */
	public Wall(int l, int x, int y, int width, int height) {
		super(width, height);
		String level = "level" + l + ".png";
		super.addFrame(level, x, y);
		super.position(x, y);
	}

}
