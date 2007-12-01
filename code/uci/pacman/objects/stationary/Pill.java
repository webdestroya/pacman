package code.uci.pacman.objects.stationary;


import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;
import ucigame.Image;

/**
 * 
 * @author Object Team
 * Pills are the small dots that get eaten
 *
 */

public class Pill extends StationaryObject implements Eatable{
	
	private static Image i = getImage("pill.jpg");
	
	/**
	 * the point value of this object
	 */
	public static final int SCOREVALUE = 10;
	
	public Pill(int x, int y) {
		super(i, x, y);
	}

	public void eaten() {
		// TODO Auto-generated method stub
		control.pillEaten(this);
	}

}
