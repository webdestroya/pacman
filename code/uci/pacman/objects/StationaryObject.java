package code.uci.pacman.objects;

import ucigame.Image;
import ucigame.Sprite;

public class StationaryObject extends Sprite implements Eatable{

	public StationaryObject(Image img, int x, int y) {
		super(img);
		this.position(x, y);
		
	}

	@Override
	public void eaten() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
