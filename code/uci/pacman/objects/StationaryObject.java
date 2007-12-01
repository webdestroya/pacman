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
		super(img.width(), img.height());
		super.addFrame(img, 0, 0);
		this.position(x, y);
		control = GameController.getInstance();
	}
	
	public StationaryObject(int width, int height) {
		super(width, height);
		
	}

	protected static Image getImage(String stringPath){
		String resources = "images/final/";
		return GameController.getInstance().getPacInstance().getImage(resources+stringPath);
	}
	
	public boolean collidedPerfect(ControllableObject c){
		super.checkIfCollidesWith(c, PacManGame.PIXELPERFECT);
		return super.collided() && super.isShown();
	}
	
	public boolean collided(ControllableObject c){
		super.checkIfCollidesWith(c);
		return super.collided() && super.isShown();
	}

	@Override
	public void eaten() {
		// TODO Auto-generated method stub
		
	}

}
