package code.uci.pacman.gui;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.game.GameState;
import code.uci.pacman.game.PacManGame;
import code.uci.pacman.objects.stationary.Fruit;
import ucigame.*;
import java.util.*;

/**
 * 
 * This will display the score, lives, and current fruit
 * @author GUI Team (Rick, MikeY, Cameron) 
 *
 */
public class ScoreBoard{

	private int remainingLives = 0;
	private Sprite score;
	private Sprite lives; 
	private Fruit fruit;
	private int scorePosX = 0;
	private int scorePosY = 610;
	private int livesPosX = 300;
	private int livesPosY = 610;
	private int lifePosX = 355;
	private int lifePosY = 610;
	private int lifePosAdj = 30;
	private int fruitPosX = 550;
	private int fruitPosY = 610;
	ArrayList<Sprite> lifeList = new ArrayList<Sprite>();
	
	public ScoreBoard() {
		//create score sprite
		score = GameController.getInstance().getPacInstance().makeSpriteFromPath("images/final/score.png");
		score.position(scorePosX, scorePosY);
		score.font(PacManGame.font, PacManGame.BOLD, 24, 255, 255, 255);
		
		//create lives sprite
		lives = GameController.getInstance().getPacInstance().makeSpriteFromPath("images/final/lives.png");
		lives.position(livesPosX, livesPosY);

		//set remainLives
		remainingLives = GameState.getInstance().getLives();
		for(int x = 1; x <= remainingLives; x++){
			lifeList.add(GameController.getInstance().getPacInstance().makeSpriteFromPath("images/final/life.png"));
		}
		
		// set fruit
		String fruitImagePath = GameState.getInstance().getFruit().getGraphicPath();
		fruit = new Fruit(fruitImagePath, fruitPosX, fruitPosY, 0);
	}
	
	/**
	 * 
	 * Shows the scoreboard and the fruit at the bottom of the screen during gameplay
	 * 
	 */
	public void draw(){
		//this needs to draw all the stuff that you make
		// to get all of the information use GameState.getInstance().getTheItemYouWant()
		
		//add scores from GameState and draw score sprite onto canvas
		score.putText(GameState.getInstance().getScore() + "", 120, 24);
		score.draw();
		
		//add lives from GameState and draw life sprite onto canvas
		lives.draw();
		remainingLives = GameState.getInstance().getLives();
		for(int x = 1; x <= remainingLives; x++){
			lifeList.get(x-1).position(lifePosX + x*lifePosAdj, lifePosY);
			lifeList.get(x-1).draw();
		}
		
		//add fruit to the scoreBoard.
		fruit.draw();
	}
}
