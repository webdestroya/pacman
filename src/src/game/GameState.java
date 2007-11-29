package game;

import java.awt.Point;

import objects.*;

import ucigame.Sprite;


/**
 * 
 * @author Game Team
 * holds reference to entire board
 * this represents every behavior that can be public called
 *
 */
public class GameState {
	
	private static GameState game;
	private PacManGame pacManGame;
	
	public GameState(PacManGame g){
		pacManGame = g;
	}
	

	public static void setInstance(PacManGame g){
		game = new GameState(g);
	}
	
	public static GameState getInstance(){
		return game;
	}
	
	public void updateScore(){
		
	}


	public void fruitEaten(Fruit fruit) {
		// TODO Auto-generated method stub
		
	}
	
	public void ghostEaten(Ghost ghost) {
		// TODO Auto-generated method stub
		
	}
	public void pillEaten(Pill pill) {
		// TODO Auto-generated method stub
		
	}
	public void powerPelletEaten(PowerPellet powerPellet) {
		// TODO Auto-generated method stub
		
	}
	public void pacManEaten(PacMan pacMan) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @returns PacMan Position
	 */
	public Point getPacManPos(){
		return null;
	}
	
	public boolean canMove(Sprite s, Direction d){
		return false;
	}
}
