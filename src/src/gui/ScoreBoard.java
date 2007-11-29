package src.gui;
import src.objects.Fruit;
import ucigame.*;

/**
 * 
 * @author GUI Team
 * this will display the score, lives, and current fruit
 *
 */
public class ScoreBoard extends Sprite{

	private int score = 0;
	private int remainingLives = 0;
	private Fruit currentFruit = null;
	
	public ScoreBoard(int arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param int points
	 * adds points to the score
	 */
	public void addPoints(int points){
		
	}
	
	/**
	 * 
	 * removes a life from remainingLives
	 * 
	 */
	public void removeLife(){
		
	}
	
	/**
	 * 
	 * adds a life to remainingLives
	 * 
	 */
	public void addLife(){
		
	}
	
	/***
	 * 
	 * this method returns the lives left for the player,
	 * to determine whether the game is finished or not.
	 * @return int remainingLives.
	 * 
	 */
	public int getLifeCount(){
		return remainingLives;
	}
	
	/**
	 * 
	 * @param Fruit newFruit
	 * sets currentFruit to newFruit
	 */
	public void setFruit(Fruit newFruit){
		
	}
}
