package code.uci.pacman.objects.stationary;


import code.uci.pacman.controllers.GameController;
import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;
import ucigame.Image;

public class Fruit extends StationaryObject implements Eatable{
	
	/**
	 * the point value of this object
	 */
	private int scoreValue;
	
	

	/**
	 * 
	 * @author Objects Team
	 * constructor picks random fruit image
	 * sets the value
	 * 
	 */
	public Fruit(Image fruit, int arg0, int arg1) {
		super(fruit, arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gamestate.updateScore(it's own points)
	 * delete itself
	 */
	public void eaten() {
		// TODO Auto-generated method stub
		GameController.getInstance().fruitEaten(this);
	}

	public int getValue() {
		return scoreValue;
	}


}
