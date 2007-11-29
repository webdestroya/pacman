package objects;

import ucigame.Sprite;

/**
 * 
 * @author Object Team
 * power pellets are the larger pellets (only 4 on board)
 *
 */
public class PowerPellet extends Sprite implements Eatable {
	
	/**
	 * the point value of this object
	 */
	private int scoreValue = 50;
	/**
	 * scatter ghosts
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
