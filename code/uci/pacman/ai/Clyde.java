package code.uci.pacman.ai;

import java.util.Random;

import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;

/**
 * This class contains the AI for the orange ghost, Clyde.
 * @author Team Objects/AI
 * top left (19,19)
 * lower right (558, 551)
 */
public class Clyde extends Ghost{

	private int countdownTimer = 50;
	private boolean directionUP = false;
	private final static int SPEED = 5;
	private int mod = 7;
	private int deathTimer = 40;
	private int minDistance = 100;

	public Clyde(int x, int y, boolean isPlayer) {
		super("pac-man ghost images\\clydeFINAL.png", x, y, SPEED, isPlayer);
	}

	/** 
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He seems to want to be in the same general area as you, 
	 * but doesn't seem to directly chase you. Don't let this fool 
	 * you; he will not turn away if you are in his path. If you stay 
	 * in one area, he seems to have a set pattern.
	 */

	protected Direction getAIMove()
	{
		// as of now, this ghost just tries to get to you as fast as possible
		// with some work, it could end up being very smart
		// so for now this is just an example for one way of doing this


		int curX = this.x();
		int curY = this.y();

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
				if(getDistanceToPacman(curX, curY, targetX, targetY) > minDistance){
					try{
						curDirection = getPacmanDirection(pm.xspeed(), pm.yspeed());
					}
					catch(NullPointerException NPE){
						curDirection = lastDirection;
					}
					if(curDirection == Direction.UP){
						targetY -= minDistance/2;
					}
					else if(curDirection == Direction.DOWN){
						targetY += minDistance/2;
					}
					else if(curDirection == Direction.LEFT){
						targetX -= minDistance/2;
					}
					else{
						targetX += minDistance/2;
					}
				}

			}			

			tryMove(curX, curY, targetX, targetY);

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
