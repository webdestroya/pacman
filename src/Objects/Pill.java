package Objects;

import ucigame.Sprite;

/**
 * 
 * @author Object Team
 * Pills are the small dots that get eaten
 *
 */
public class Pill extends Sprite implements Eatable{
	
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
