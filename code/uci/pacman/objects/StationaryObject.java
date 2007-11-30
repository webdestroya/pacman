package code.uci.pacman.objects;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.game.PacManGame;
import ucigame.Image;
import ucigame.Sprite;

/**
 * @author Team Objects/AI
 *
 */
public class StationaryObject extends Sprite implements Eatable{
    protected GameController control;
	public StationaryObject(Image img, int x, int y) {
		super(img);
		this.position(x, y);
		control = GameController.getInstance();
	}
	
	protected static Image getImage(String stringPath){
		String resources = "images/final/";
		return PacManGame.getPacImage(resources+stringPath);
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
