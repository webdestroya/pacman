package code.uci.pacman.objects.stationary;

import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;

/**
 * 
 * @author Team Objects/AI
 * power pellets are the larger pellets (only 4 on board)
 *
 */
public class PowerPellet extends StationaryObject implements Eatable {
	
	private static final String PELLET_IMAGE_PATH = "pellet.png";
	
	/**
	 * 
	 * @author Objects Team
	 * constructor creates the Power Pellet sprite in the game
	 * 
	 */
	public PowerPellet(int x, int y) {
		super(PELLET_IMAGE_PATH, x, y);
	}
	/**
	 * the point value of this object
	 */
	public static final int SCOREVALUE = 50;

	/**
	 * Called when a power pellet is eaten
	 * Tells the game controller that it's been eaten
	 */
	public void eaten() {
		control.pelletEaten(this);
	}

	
}
