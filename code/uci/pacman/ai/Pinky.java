package code.uci.pacman.ai;

import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;

/**
 * This class contains the AI for the pink ghost, Pinky.
 * @author Team Objects/AI
 *
 */
public class Pinky extends Ghost{

	private Direction lastDirection;
	private Direction curDirection;
	
	private final static int SPEED = 6;
	
	public Pinky(int x, int y, boolean isPlayer) {
		super("pac-man ghost images/pinkyFINAL.png", x, y, SPEED, isPlayer);
	}

	/* (non-Javadoc)
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He wants to move in the same direction as you, but only 
	 * when he's close enough to you. In other words, he tries 
	 * to get to your area first, then when he's close enough, 
	 * he will try to run in the same direction as you. This can 
	 * be tested by running right up to him and when he gets right 
	 * in front of you he will turn away (provided there is a 
	 * hallway to turn into)
	 */
	@Override
	protected Direction getAIMove() {
		// as of now, this ghost just tries to get to you as fast as possible
		// with some work, it could end up being very smart
		// so for now this is just an example for one way of doing this
		
		// first check to see if in scatter mode
		if (this.isScattered()) {
			
		} else {
			int curX = this.x();
			int curY = this.y();
			// check to see if in center (just spawned)
			if ((curY > 215 && curY <= 250) && (curX >= 250 && curX <= 325)) {
				this.position(this.x(), 205);
				lastDirection = Direction.LEFT;
				curDirection = Direction.UP;
			} else {
				PacMan pm = GameState.getInstance().getPacMan();
				int pmX = pm.x();
				int pmY = pm.y();
				int horizontalDifference = curX - pmX;
				int verticalDifference = curY - pmY;
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
		
		return null;
	}

}
