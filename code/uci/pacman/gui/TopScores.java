package code.uci.pacman.gui;
import ucigame.*;
import java.util.*;

import code.uci.pacman.game.GameState;

/**
 * 
 * This will keep track of top 10 stores and names
 * @author GUI Team (Rick, MikeY, Cameron)
 *
 */
public class TopScores{

	private ArrayList<String> nameList;
	private ArrayList<Integer> scoreList;
	private int score;
	
	/**
	 * 
	 * Constructor for TopScores:
	 * This will load from a file top 10 players and their corresponding scores.
	 *
	 */
	public TopScores(){
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * Displays the top score screen.
	 * 
	 */
	public void draw(){
		
	}
	
	/**
	 * 
	 * 
	 * Retrieves player's score from GameState,
	 * and checks it against scoreList to see if it qualifies
	 * to be on the top score list.
	 * 
	 * Returns true if score qualifies to be on the list,
	 * false otherwise.
	 * 
	 */
	public boolean isTopList(){
		score = GameState.getInstance().getScore();
		return false;
	}
	
	/**
	 * 
	 * Retrieves player's score from GameState, and
	 * adds the score to the scoreList at the proper place.
	 * the method will also request the user for his/her name
	 * to be put into the nameList.
	 * 
	 * scoreList stores a limit of 10 scores, therefore when 1 is
	 * added the last one will be removed
	 * 
	 */
	public void addTopScore(){
		GameState.getInstance().getScore();		
	}
}
