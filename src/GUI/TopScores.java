package GUI;
import ucigame.*;
import java.util.*;

/**
 * 
 * @author GUI Team
 * this will display the score, lives, and current fruit
 *
 */
public class TopScores extends Sprite{

	private ArrayList<String> nameList;
	private ArrayList<Integer> scoreList;
	
	
	public TopScores(int arg0, int arg1) {
		super(arg0, arg1);
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
