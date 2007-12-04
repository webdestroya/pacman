package code.uci.pacman.gui;
import ucigame.*;
import java.util.*;

/**
 * 
 * This will keep track of top 10 stores and names
 * @author GUI Team (Rick, MikeY, Cameron)
 *
 */
public class TopScores extends Sprite{

	private ArrayList<String> nameList;
	private ArrayList<Integer> scoreList;
	
	
	public TopScores() {
		super(400,200);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param int score
	 * checks score against scoreList to see if it qualifies
	 * to be on the top score list
	 * 
	 * returns true if score qualifies to be on the list,
	 * false otherwise.
	 * 
	 */
	public boolean isTopList(int score){
		return false;
	}
	
	/**
	 * 
	 * @param int score
	 * adds the score to the scoreList at the proper place.
	 * the method will also request the user for his/her name
	 * to be put into the nameList
	 * 
	 * scoreList stores a limit of 10 scores, therefore when 1 is
	 * added the last one will be removed
	 * 
	 */
	public void addTopScore(int score){
		
	}
		
}
