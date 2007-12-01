package code.uci.pacman.objects.stationary;


import code.uci.pacman.objects.StationaryObject;
import ucigame.Image;

/**
 * 
 * @author Team Objects/AI
 * this is one square image
 *
 */
public class Wall extends StationaryObject {

	public Wall(int x, int y) {
		super(getImage("wall.jpg"), x, y);
		// TODO Auto-generated constructor stub
	}

}
