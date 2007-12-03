package code.uci.pacman.objects.stationary;


import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;

/**
 * 
 * @author Object Team
 * Pills are the small dots that get eaten
 *
 */

public class Pill extends StationaryObject implements Eatable {
	
	private static final String PILL_IMAGE_PATH = "pill.png";
	
	/**
	 * the point value of this object
	 */
	public static final int SCOREVALUE = 10;
	
	public Pill(int x, int y) {
		super(PILL_IMAGE_PATH, x, y);
	}

	public void eaten() {
		// TODO Auto-generated method stub
		control.pillEaten(this);
	}

}
