package code.uci.pacman.objects;

import java.awt.Point;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.game.PacManGame;
import ucigame.Image;
import ucigame.Sprite;

/**
 * Represents all objects that are stationary (Fruit, Pill, PowerPellet, Wall).
 * @author Team Objects/AI
 *
 */
public abstract class StationaryObject extends Sprite {
	
    protected GameController control;
    protected String objectGraphicPath;
    
    /**
     * @param img
     * @param x
     * @param y
     */
    private StationaryObject(Image img, int x, int y) {
		super(img.width(), img.height());
		super.addFrame(img, 0, 0);
		this.position(x, y);
		control = GameController.getInstance();
	}
	
	/**
	 * @param path
	 * @param x
	 * @param y
	 */
	public StationaryObject(String path, int x, int y) {
		this(getImage(path), x, y);
		this.objectGraphicPath = path;
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public StationaryObject(int width, int height) {
		super(width, height);
		
	}
	
	/**
	 * @return
	 */
	public String getGraphicPath() {
		return objectGraphicPath;
	}
	
	/**
	 * @param c
	 * @return
	 */
	public boolean collidedWith(ControllableObject c){
		super.checkIfCollidesWith(c, PacManGame.PIXELPERFECT);
		return super.collided() && super.isShown();
	}
	
	/**
	 * @param p
	 */
	public void position(Point p) {
		super.position(p.x, p.y);
	}
	
	/**
	 * @param imgPath
	 * @param x
	 * @param y
	 */
	public void addFrame(String imgPath, int x, int y){
		super.addFrame(getImage(imgPath), x, y);
	}
	
	/**
	 * @param stringPath
	 * @return
	 */
	private static Image getImage(String stringPath){
		String resources = "images/final/";
		return GameController.getInstance().getPacInstance().getImage(resources+stringPath);
	}
}
