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
	private int mod = 7;
	private int deathTimer = 40;
	private int minDistance = 100;

	private final static int SPEED = 6;

	public Pinky(int x, int y, boolean isPlayer) {
		super("pac-man ghost images/pinkyFINAL.png", x, y, SPEED, isPlayer);
	}

	/**
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He wants to move in the same direction as you, but only 
	 * when he's close enough to you. In other words, he tries 
	 * to get to your area first, then when he's close enough, 
	 * he will try to run in the same direction as you. This can 
	 * be tested by running right up to him and when he gets right 
	 * in front of you he will turn away (provided there is a 
	 * hallway to turn into)
	 */

	protected Direction getAIMove()
	{

		int curX = this.x();
		int curY = this.y();
		
		// check to see if the game just started
		if(countdownTimer > 0){
			if(countdownTimer%mod==0){
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
				lastDirection = Direction.LEFT;
				this.position(getInitialOutOfCagePos());
			}
		} else {
			// check to see if in center (just spawned)
			if ((curY > 215 && curY <= 250) && (curX >= 250 && curX <= 325)) {
				countdownTimer = deathTimer;
			}
			PacMan pm = GameState.getInstance().getPacMan();
			int targetX = 250, targetY = 350;
			if(this.isScattered()){
				targetX = 558 - pm.x();
				targetY = 551 - pm.y();
			} else {
				targetX = pm.x();
				targetY = pm.y();
			}
			// if pinky is within a set distance from pacman
			if(getDistanceToPacman(curX, curY, targetX, targetY) < minDistance){
				// it tries to go the same direction as pacman
				try{
					curDirection = getPacmanDirection(pm.xspeed(), pm.yspeed());
				}
				// just incase something goes wrong, it sets the direction as the last direction
				catch(NullPointerException NPE){
					curDirection = lastDirection;
				}
				// and if it can't go that direction, it'll just move according to the standard
				// ai and try to eat pacman
				if(!this.moveIsAllowed(curDirection)){
					tryMove(curX, curY, targetX, targetY);
				}
			}
			else{
				tryMove(curX, curY, targetX, targetY);
			}

		}
		lastDirection = curDirection;
		return curDirection;
	}
	
	private void tryMove(int curX, int curY, int targetX, int targetY){
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
	
	private double getDistanceToPacman(int Gx, int Gy, int Px, int Py){
		double distance = 0;
		distance = Math.sqrt(Math.pow((Px - Gx), 2) + Math.pow((Py - Gy), 2));
		return distance;
	}

	private Direction getPacmanDirection(double XSpeed, double YSpeed){
		Direction direction = Direction.LEFT;
		if(XSpeed == 0 ){
			if(YSpeed > 0 ){
				direction = Direction.DOWN;
			}
			else if(YSpeed < 0 ){
				direction = Direction.UP;
			}
			else{
				direction = null;
			}
		}
		else{
			if(XSpeed > 0 ){
				direction = Direction.RIGHT;
			}
			else if(XSpeed < 0 ){
				direction = Direction.LEFT;;
			}
			else{
				direction = null;
			}
		}
		return direction;
	}
}
