package code.uci.pacman.ai;

import java.util.Random;

import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;

/**
 * This class contains the AI for the orange ghost, Clyde.
 * @author Team Objects/AI
 *
 */
public class Clyde extends Ghost{

	private Direction lastDirection;
	private Direction curDirection;
	private int targetScatterX = 0, targetScatterY = 0;
	
	private final static int SPEED = 4;
	
	public Clyde(int x, int y, boolean isPlayer) {
		super("pac-man ghost images\\clydeFINAL.png", x, y, SPEED, isPlayer);
	}

	/* (non-Javadoc)
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He seems to want to be in the same general area as you, 
	 * but doesn't seem to directly chase you. Don't let this fool 
	 * you; he will not turn away if you are in his path. If you stay 
	 * in one area, he seems to have a set pattern.
	 */
	//@Override
	protected Direction getAIMove() {
		// as of now, this ghost just tries to get to you as fast as possible
		// with some work, it could end up being very smart
		// so for now this is just an example for one way of doing this
		
		int curX = this.x();
		int curY = this.y();
		// check to see if in center (just spawned)
		if ((curY > 215 && curY <= 250) && (curX >= 250 && curX <= 325)) {
			this.position(this.x(), 205);
			lastDirection = Direction.LEFT;
			curDirection = Direction.UP;
		} else {
			PacMan pm = GameState.getInstance().getPacMan();
			int targetX = 250, targetY = 350;
			// first check to see if in scatter mode
			if(this.isScattered()){
					targetX = pm.x();
					targetY = pm.y();
				targetX = targetScatterX;
				targetY = targetScatterY;
			} else {
				targetX = pm.x();
				targetY = pm.y();
			}			
				
				int horizontalDifference = curX - targetX;
				int verticalDifference = curY - targetY;
				Direction preferredHorizontal = horizontalDifference > 0 ? Direction.LEFT : Direction.RIGHT;
				Direction preferredVertical = verticalDifference > 0 ? Direction.UP : Direction.DOWN;
				boolean verticalMoreImportant = Math.abs(verticalDifference) > Math.abs(horizontalDifference);
				if (verticalMoreImportant)
					curDirection = preferredVertical;
				else
					curDirection = preferredHorizontal;
				if (!this.moveIsAllowed(curDirection)) {
					if (verticalMoreImportant) {
						if (lastDirection == Direction.LEFT || lastDirection == Direction.RIGHT) {
							curDirection = lastDirection;
							if (!this.moveIsAllowed(curDirection))
								curDirection = curDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
						} else {
							curDirection = preferredHorizontal;
							if (!this.moveIsAllowed(curDirection)) {
								curDirection = preferredHorizontal == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
								if (!this.moveIsAllowed(curDirection))
									curDirection = preferredVertical == Direction.UP ? Direction.DOWN : Direction.UP;
							}
						}
					} else {
						if (lastDirection == Direction.UP || lastDirection == Direction.DOWN) {
							curDirection = lastDirection;
							if (!this.moveIsAllowed(curDirection))
								curDirection = curDirection == Direction.UP ? Direction.DOWN : Direction.UP;
						} else {
							curDirection = preferredVertical;
							if (!this.moveIsAllowed(curDirection)) {
								curDirection = preferredVertical == Direction.UP ? Direction.DOWN : Direction.UP;
								if (!this.moveIsAllowed(curDirection))
									curDirection = preferredHorizontal == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
							}
						}
					}
				}
			}
			lastDirection = curDirection;
			return curDirection;

	}

}
