package code.uci.pacman.ai;

import java.util.Random;

import code.uci.pacman.game.Direction;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;

/**
 * This class contains the AI for the teal ghost, Inky.
 * @author Team Objects/AI
 *
 */
public class Inky extends Ghost{
	private final int COUNTDOWN = 100;
	private int cageTimer = 0;
	private boolean directionUP = true;
	private final static int SPEED = 5;
	private int mod = 7;
	private int deathTimer = 40;
	private int pacLives = 5;

	public Inky(int x, int y, boolean isPlayer) {
		super("pac-man ghost images/inkyFINAL.png", x, y, SPEED, isPlayer);
	}

	/**
	 * @see code.uci.pacman.AI.AI#getMove()
	 * He's the tough one. The only thing I've been able to figure 
	 * out about him is that he seems to be able to take on the 
	 * personality of any of the other three at a given point in time.
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
		// check to see if in center (just spawned)
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
			if(cageTimer <= 0){
				lastDirection = Direction.LEFT;
				this.position(getInitialOutOfCagePos());
			}
		} else {
			if ((curY > 250 && curY <= 308) && (curX >= 231 && curX <= 368)) {
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
			} else {

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
