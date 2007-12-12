package code.uci.pacman.ai;

import code.uci.pacman.game.*;
import code.uci.pacman.objects.controllable.*;
import java.util.Random;

/**
 * This class contains the AI for the red ghost, Blinky.
 * @author Team Objects/AI
 *
 */
public class Blinky extends Ghost{

	
	private final static int SPEED = 7;
	private boolean newScatter = true;
	private int targetScatterX = 0, targetScatterY = 0;

	public Blinky(int x, int y, boolean isPlayer) {
		super("pac-man ghost images/blinkyFINAL.png", x, y, SPEED, isPlayer);
	}

	
	

	protected Direction getAIMove()
	{
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
			int targetX = 250, targetY = 350;
			if(this.isScattered()){
				if(newScatter)
				{
					Random rand = new Random();
					targetScatterX = rand.nextBoolean() ? 600 : 0;
					targetScatterY = rand.nextBoolean() ? 600 : 0;
					newScatter = false;
				}
				targetX = targetScatterX;
				targetY = targetScatterY;
			} else {
				PacMan pm = GameState.getInstance().getPacMan();
				targetX = pm.x();
				targetY = pm.y();
				newScatter = true;
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
