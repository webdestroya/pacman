package Objects1;
import game.*;

import ucigame.Sprite;

public class Fruit extends Sprite implements Eatable{
	
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
	public Fruit(int arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gamestate.updateScore(it's own points)
	 * delete itself
	 */
	public void eaten() {
		// TODO Auto-generated method stub
		GameState.getInstance().fruitEaten(this);
	}

	public int getValue() {
		return scoreValue;
	}


}
