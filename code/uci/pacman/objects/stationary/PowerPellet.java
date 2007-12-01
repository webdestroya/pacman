package code.uci.pacman.objects.stationary;

import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;
import ucigame.Image;

/**
 * 
 * @author Team Objects/AI
 * power pellets are the larger pellets (only 4 on board)
 *
 */
public class PowerPellet extends StationaryObject implements Eatable {
	
	public PowerPellet(int x, int y) {
		super(getImage("pellet.png"), x, y);
		// TODO Auto-generated constructor stub
	}
	/**
	 * the point value of this object
	 */
	public static final int SCOREVALUE = 50;

	public void eaten() {
		// TODO Auto-generated method stub
		control.pelletEaten(this);
	}

	
}
