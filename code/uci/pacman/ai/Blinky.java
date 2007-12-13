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

	private final int COUNTDOWN = 8;
	private int cageTimer = 0;
	private boolean directionUP = true;
	private final static int SPEED = 6;
	private int mod = 7;
	private int deathTimer = 40;
	private int pacLives = 5;	

	public Blinky(int x, int y, boolean isPlayer) {
		super("pac-man ghost images/blinkyFINAL.png", x, y, SPEED, isPlayer);
	}


	/**
	 * @see code.uci.pacman.objects.controllable.Ghost#getAIMove()
	 * He tries to get you by your relative position.
	 * He takes the fastest route to find you. 
	 */
	protected Direction getAIMove()
	{
		// as of now, this ghost just tries to get to you as fast as possible
		// with some work, it could end up being very smart
		// so for now this is just an example for one way of doing this

		int curX = this.x();
		int curY = this.y();
		GameState state = GameState.getInstance();
		int currentLives = state.getLives();
		if(currentLives != pacLives){
			pacLives = currentLives;
			cageTimer = COUNTDOWN;
			isAttacking = false;
			countdownTimer = SCATTER;
		}
		if(cageTimer > 0){
			if(cageTimer%mod==0){
				if(directionUP){
					curDirection = Direction.UP;
				}
				else{
					curDirection = Direction.DOWN;
				}
				directionUP = !directionUP;
			}
			cageTimer --;
			if(cageTimer == 0){
				this.position(getInitialOutOfCagePos());
			}
		} else {
			if ((curY > 215 && curY <= 250) && (curX >= 250 && curX <= 325)) {
				cageTimer = deathTimer;
			}
			PacMan pm = state.getPacMan();
			int targetX = 250, targetY = 350;
			if(this.isScattered()){
				targetX = 558 - pm.x();
				targetY = 551 - pm.y();
			} else if(!isAttacking){
				targetX = 558 - pm.x();
				targetY = 551 - pm.y();
				countdownTimer --;
			}else {
				targetX = pm.x();
				targetY = pm.y();
				countdownTimer --;
			}
			
			if(countdownTimer <= 0){
				flipAttack();
			}

			tryMove(curX, curY, targetX, targetY);

		}
		lastDirection = curDirection;
		return curDirection;

	}

	
}
