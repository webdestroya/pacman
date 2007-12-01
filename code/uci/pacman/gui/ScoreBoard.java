package code.uci.pacman.gui;

import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.stationary.Fruit;
import ucigame.*;

/**
 * 
 * @author GUI Team
 * this will display the score, lives, and current fruit
 *
 */
public class ScoreBoard{

	private int score = 0;
	private int remainingLives = 0;
	private Fruit currentFruit = null;
	
	public ScoreBoard() {
		// Hey we dicked with your shit. 
		// Scorebaord can't extend sprite it's gotta be a collection of sprites
		// so you need to make multiple sprites in here
		// TODO Auto-generated constructor stub
	}
	
	public void draw(){
		//this needs to draw all the stuff that you make
		// to get all of the information use GameState.getInstance().getTheItemYouWant()
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
	private int getLifeCount(){
		return GameState.getInstance().getLives();
	}
	
	/**
	 * 
	 * @param Fruit newFruit
	 * sets currentFruit to newFruit
	 */
	public void setFruit(Fruit newFruit){
		
	}
}
