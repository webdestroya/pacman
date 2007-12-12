package code.uci.pacman.ai;

import java.util.Random;

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
	private int countdownTimer = 150;
	private boolean directionUP = false;
	private int targetScatterX = 0, targetScatterY = 0;

	private final static int SPEED = 6;

	public Pinky(int x, int y, boolean isPlayer) {
		super("pac-man ghost images\\pinkyFINAL.png", x, y, SPEED, isPlayer);
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
	protected Direction getAIMove()
	{
		// as of now, this ghost just tries to get to you as fast as possible
		// with some work, it could end up being very smart
		// so for now this is just an example for one way of doing this

		int curX = this.x();
		int curY = this.y();
		// check to see if in center (just spawned)

		if(countdownTimer > 0){
			if(countdownTimer%7==0){
				if(directionUP){
					curDirection = Direction.UP;
				}
				else{
					curDirection = Direction.DOWN;
				}
				directionUP = !directionUP;
			}
			countdownTimer --;
			if(countdownTimer == 0){
				this.position(getInitialOutOfCagePos());
			}
		} else {
			PacMan pm = GameState.getInstance().getPacMan();
			int targetX = 250, targetY = 350;
			if(this.isScattered()){
				targetX = 600 - pm.x();
				targetY = 600 - pm.y();
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
			if (lastDirection == Direction.UP || lastDirection == Direction.DOWN) {
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
		}
		lastDirection = curDirection;
		return curDirection;

	}

	private Direction randomDirection(){
		Random rand = new Random();
		int num = rand.nextInt(3);
		Direction direction = Direction.LEFT;
		if(num == 0){
			direction = Direction.LEFT;
		}
		else if(num ==1){
			direction = Direction.UP;
		}
		else if(num ==2){
			direction = Direction.DOWN;
		}
		else {
			direction = Direction.RIGHT;
		}
		return direction;
	}
}
