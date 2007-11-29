package code.uci.pacman.objects.stationary;

import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;
import ucigame.Image;
import ucigame.Sprite;

/**
 * 
 * @author Object Team
 * power pellets are the larger pellets (only 4 on board)
 *
 */
public class PowerPellet extends StationaryObject implements Eatable {
	
	public PowerPellet(Image img, int x, int y) {
		super(img, x, y);
		// TODO Auto-generated constructor stub
	}
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
