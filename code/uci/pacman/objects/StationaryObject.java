package code.uci.pacman.objects;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.game.PacManGame;
import ucigame.Image;
import ucigame.Sprite;

/**
 * @author Team Objects/AI
 *
 */
public abstract class StationaryObject extends Sprite {
    protected GameController control;
    protected Image objectGraphic;
    
	public StationaryObject(Image img, int x, int y) {
		super(img.width(), img.height());
		super.addFrame(img, 0, 0);
		this.position(x, y);
		this.objectGraphic = img;
		control = GameController.getInstance();
	}
	
	public StationaryObject(int width, int height) {
		super(width, height);
		
	}
	
	public Image getGraphic() {
		return objectGraphic;
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
}
