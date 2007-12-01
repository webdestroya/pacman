package code.uci.pacman.objects.stationary;


import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;
import code.uci.pacman.objects.controllable.PacMan;
import ucigame.Image;

/**
 * @author Team Objects/AI
 *
 */
public class Fruit extends StationaryObject implements Eatable{
	
	/**
	 * the point value of this object
	 */
	private int score;
	private int fruitEaten;

	/**
	 * 
	 * @author Objects Team
	 * constructor picks random fruit image
	 * sets the value
	 * automatically hides itself
	 * 
	 */
	public Fruit(int x, int y, int initialScore) {
		super(randomFruit(), x, y); //we assumed it always starts at 100
		super.hide();
		fruitEaten = 0;
		score = initialScore;
		// TODO Auto-generated constructor stub
	}
	

	private static Image randomFruit() {
		return getImage("cherry.jpg");
	}

	public void eaten() {
		fruitEaten++;
		// TODO Auto-generated method stub
		control.fruitEaten(this);
	}

	/**
	 * returns the value
	 */
	public int getValue() {
		return score;
	}
	public int getFruitEaten(){
		return fruitEaten;
	}
	
	public void setValue(int scoreValue){
		this.score = scoreValue;
	}


}
