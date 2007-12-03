package code.uci.pacman.ai;

import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;

/**
 * @author Team Objects/AI
 *
 */
public class Clyde extends Ghost{

	private Direction lastDirection;
	private Direction curDirection;
	
	private final static int SPEED = 4;
	
	public Clyde(int x, int y, boolean isPlayer) {
		super("clyde.png", x, y, SPEED, isPlayer);
	}

	/* (non-Javadoc)
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He seems to want to be in the same general area as you, 
	 * but doesn't seem to directly chase you. Don't let this fool 
	 * you; he will not turn away if you are in his path. If you stay 
	 * in one area, he seems to have a set pattern.
	 */
	@Override
	public Direction getMove() {
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
				if (curX < 275)
					return Direction.RIGHT;
				else if (curX > 300)
					return Direction.LEFT;
				// otherwise, right in between
				this.nextY(205);
				lastDirection = Direction.LEFT;
				curDirection = Direction.UP;
			} else if ((curY >= 190 && curY <= 215) && (curX >= 250 && curX <= 325)) {
				//temporary until starting wall doesn't allow coming in
				curDirection = Direction.LEFT;
			} else {
				PacMan pm = GameState.getInstance().getPacMan();
				int pmX = pm.x();
				int pmY = pm.y();
				int horizontalDifference = curX - pmX;
				int verticalDifference = curY - pmY;
				Direction preferredHorizontal = horizontalDifference > 0 ? Direction.LEFT : Direction.RIGHT;
				Direction preferredVertical = verticalDifference > 0 ? Direction.UP : Direction.DOWN;
				boolean verticalMoreImportant = Math.abs(verticalDifference) > Math.abs(horizontalDifference);
				if (verticalMoreImportant) {
					curDirection = preferredVertical;
				} else {
					curDirection = preferredHorizontal;
				}
				this.step(curDirection);
				this.move();
				// there has to be a better way of doing this next part with looping,
				// but for now it works...
				if (GameState.getInstance().getWalls().collidesWith(this)) {
					if (verticalMoreImportant) {
						if (lastDirection == Direction.LEFT || lastDirection == Direction.RIGHT) {
							curDirection = lastDirection;
							this.step(curDirection);
							this.move();
							if (GameState.getInstance().getWalls().collidesWith(this)) {
								curDirection = curDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
								this.step(curDirection);
								this.move();
							}
						} else {
							curDirection = preferredHorizontal;
							this.step(curDirection);
							this.move();
							if (GameState.getInstance().getWalls().collidesWith(this)) {
								curDirection = preferredHorizontal == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
								this.step(curDirection);
								this.move();
								if (GameState.getInstance().getWalls().collidesWith(this)) {
									curDirection = preferredVertical == Direction.UP ? Direction.DOWN : Direction.UP;
									this.step(curDirection);
									this.move();
								}
							}
						}
					} else {
						if (lastDirection == Direction.UP || lastDirection == Direction.DOWN) {
							curDirection = lastDirection;
							this.step(curDirection);
							this.move();
							if (GameState.getInstance().getWalls().collidesWith(this)) {
								curDirection = curDirection == Direction.UP ? Direction.DOWN : Direction.UP;
								this.step(curDirection);
								this.move();
							}
						} else {
							curDirection = preferredVertical;
							this.step(curDirection);
							this.move();
							if (GameState.getInstance().getWalls().collidesWith(this)) {
								curDirection = preferredVertical == Direction.UP ? Direction.DOWN : Direction.UP;
								this.step(curDirection);
								this.move();
								if (GameState.getInstance().getWalls().collidesWith(this)) {
									curDirection = preferredHorizontal == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
									this.step(curDirection);
									this.move();
								}
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
