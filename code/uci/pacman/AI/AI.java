
package code.uci.pacman.AI;

import code.uci.pacman.game.Direction;


/**
 * @author Team Objects/AI
 *
 */
public interface AI {

	
	/**
	 * @return The Direction the ghost will go
	 * The AI is an interface because a seperate AI will need to be
	 * made for each ghost since each has its own personality.
	 */
	
	public Direction getMove();
	
}
