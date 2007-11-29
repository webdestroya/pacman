package objects.stationary;

import objects.Eatable;
import objects.StationaryObject;
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

	/**
	 * update score
	 * delete itself
	 */
	public void eaten() {
		// TODO Auto-generated method stub
		
	}

	public int getValue() {
		return scoreValue;
	}
	

}