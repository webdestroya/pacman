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

	public Blinky(int x, int y, boolean isPlayer) {
		super("blinky.png", x, y, SPEED, isPlayer);
	}

	
	

	protected Direction getAIMove()
	{
		// as of now, this ghost just tries to get to you as fast as possible
		// with some work, it could end up being very smart
		// so for now this is just an example for one way of doing this
		
		// first check to see if in scatter mode
		// In Scatter Mode, the ghost moves along until it comes to an intersection.
		// From here, it can continue on it's path or it can turn.  This is done
		// randomly.  They never go backwards.
		if (this.isScattered()) {
			//if going vertical and there is no intersection, continue on path
			if ((curDirection == Direction.UP) || (curDirection == Direction.DOWN))
			{
				if ((!this.moveIsAllowed(Direction.LEFT)) && (!this.moveIsAllowed(Direction.RIGHT)))
					return curDirection;
			}
			//if going horizontal and there is no intersection, continue on path
			else 
			{
				if ((!this.moveIsAllowed(Direction.UP)) && (!this.moveIsAllowed(Direction.DOWN)))
					return curDirection;
			}
			
			//If control continues here, no return was called.
			//Therefore, it is at an intersection.
			
			//If there is an intersection, the PacMan can choose to either turn in an open 
			//perpendicular direction, or it can continue along it's current direction.
			Random rand = new Random();
			Direction[] availableChoices = availableScatterDirections();
			int choice = rand.nextInt(availableChoices.length);
			
			if (availableChoices[choice] == curDirection)
				return curDirection;
			else{
				lastDirection = curDirection;
				curDirection = availableChoices[choice];
				return curDirection;
			}
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
		
		//return null; //unreachable code
	}
	
	private Direction[] availableScatterDirections() {
		Direction[] available = new Direction[4];
		available[0] = Direction.UP;
		available[1] = Direction.DOWN;
		available[2] = Direction.LEFT;
		available[3] = Direction.RIGHT;
		
		//Remove blocked paths from choices.
		for (Direction d: available)
			if (!this.moveIsAllowed(d))
				removeAvailableDirection(available, d);
		//Remove going backwards as a choice.
		removeAvailableDirection(available, oppositeDirection(curDirection));
		
		
		return available;
	}
	
	private Direction[] removeAvailableDirection(Direction[] available, Direction toBeRemoved) {
		//Find the direction that should be removed.
		int indexOfToBeRemoved = -1;
		for (int index = 0; index < available.length; index++)
			if (available[index] == toBeRemoved)
				indexOfToBeRemoved = index;
		//If it's already not there, just return the array as given.
		if (indexOfToBeRemoved == -1)
			return available;
		//Otherwise, remove the array
		Direction[] newAvailable = new Direction[available.length - 1];
		for (int index = 0; index < indexOfToBeRemoved; index++)
			newAvailable[index] = available[index];
		for (int index = indexOfToBeRemoved; index < newAvailable.length; index++)
			newAvailable[index] = available[index + 1];
		
	    return newAvailable;
	}
	
	private Direction oppositeDirection(Direction d) {
		if (d == Direction.UP)
			return Direction.DOWN;
		if (d == Direction.DOWN)
			return Direction.UP;
		if (d == Direction.RIGHT)
			return Direction.LEFT;
		return Direction.RIGHT;
	}

}
