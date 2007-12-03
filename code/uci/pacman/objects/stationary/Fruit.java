package code.uci.pacman.objects.stationary;


import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;
import ucigame.Image;

/**
 * @author Team Objects/AI
 *
 */
public class Fruit extends StationaryObject implements Eatable {
	
	public static double ONSCREENLENGTH = 8000;
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
		this(randomFruit(), x, y, initialScore);
		super.hide();
	}
	
	public Fruit(Image fruit, int x, int y, int initialScore) {
		super(fruit, x, y);
	    score = initialScore;
		fruitEaten = 0;
	}
	

	private static Image randomFruit() {
		Image randomFruit = getImage("cherry.png");
		return randomFruit;
	}
	
	public void showWithTimer() {
		control.getPacInstance().startFruitTimer();
		super.show();
	}

	public void eaten() {
		fruitEaten++;
		score += 100; //increase score when eaten
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
