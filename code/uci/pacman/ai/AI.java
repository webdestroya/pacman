
package code.uci.pacman.ai;

import code.uci.pacman.game.Direction;


/**
 * This class is the interface for the AI's in the game. This consists of only 
 * one public method, getAIMove(), which each AI has its own version of. 
 * This method is what defines the personality of the ghosts. It ties in 
 * with the networking part well because as a result of getAIMove(), a 
 * "Direction" is returned. So the AI can be easily swapped with a human
 *  player which sends directions using the arrow keys. Some things that 
 *  are built in to all the Ghost AIs: bobbing up and down until it is their 
 *  turn to leave the cage; they go through various attack waves, starting 
 *  with "Scatter" mode so that PacMan has a chance to get away; each AI has 
 *  a personality that both helps to trap PacMan and to make the game easier 
 *  and can change strategies depending on how close they are to PacMan.
 * @author Team Objects/AI
 *
 */
public interface AI {

	
	/**
	 * @return The Direction the ghost will go
	 * The AI is an interface because a separate AI will need to be
	 * made for each ghost since each has its own personality.
	 */
	
	public Direction getMove();
	
}
