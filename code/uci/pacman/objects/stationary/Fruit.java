package code.uci.pacman.objects.stationary;

import java.util.ArrayList;
import java.util.Random;

import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;
import code.uci.pacman.game.*;
import code.uci.pacman.multiplayer.*;

/**
 * This class represents the fruit available in the game at various intervals
 * for extra points
 * 
 * @author Team Objects/AI
 * 
 */
public class Fruit extends StationaryObject implements Eatable {

	public static double SHOW_FRUIT_DURATION = 8000;
	/**
	 * the point value of this object
	 */
	private int score;
	private int fruitEaten;

	/**
	 * constructor picks random fruit image sets the value automatically hides
	 * itself
	 * 
	 */
	public Fruit(int x, int y, int initialScore) {
		this(randomFruitPath(), x, y, initialScore);
		super.hide();
	}

	public Fruit(String fruitImagePath, int x, int y, int initialScore) {
		super(fruitImagePath, x, y);
		score = initialScore;
		fruitEaten = 0;
	}

	/**
	 * @return a random fruit to be associated with the current level
	 */
	private static String randomFruitPath() {
		ArrayList<String> fruits = new ArrayList<String>();
		fruits.add("cherry.png");
		fruits.add("lemon.png");
		fruits.add("peach.png");
		Random r = new Random();
		return fruits.get(r.nextInt(fruits.size()));
	}

	/**
	 * Shows the fruit in the game Starts the timer that when elapsed will
	 * remove the fruit from the game if not already eaten
	 */
	public void showWithTimer() {
		control.getPacInstance().startFruitTimer();
		if( PacManGame.gameType == 1)
		{
			Server.send(PType.AFRUIT, x(), y() );
		}
		super.show();
	}

	/**
	 * Called when a fruit is eaten Updates the number of fruit eaten and the
	 * score
	 */
	public void eaten() {
		fruitEaten++;
		score += 100; // increase score when eaten
		if(PacManGame.gameType==1)
		{
			Server.send( PType.DFRUIT, x(), y() );
		}
		control.fruitEaten(this);
	}

	/**
	 * Returns the score value of the fruit
	 * @return the score value of the fruit
	 */
	public int getValue() {
		return score;
	}

	/**
	 * Returns the total number of fruit eaten
	 * @return the total number of fruit eaten
	 */
	public int getFruitEaten() {
		return fruitEaten;
	}

	/**
	 * Sets the score value of the fruit
	 * @param scoreValue
	 *            is the value to change it to
	 */
	public void setValue(int scoreValue) {
		this.score = scoreValue;
	}

}
