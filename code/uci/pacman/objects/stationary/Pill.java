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
	
	public Pill(Image img, int x, int y) {
		super(img, x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * the point value of this object
	 */
	private int scoreValue = 10;

	public void eaten() {
		// TODO Auto-generated method stub
		control.pillEaten(this);
	}

	/**
	 * returns the score value
	 */
	public int getValue() {
		return scoreValue;
	}
	

}
