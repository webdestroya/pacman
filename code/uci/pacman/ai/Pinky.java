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

	private Direction lastDirection;
	private Direction curDirection;
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
	protected Direction getAIMove() {
		// as of now, this ghost just tries to get to you as fast as possible
		// with some work, it could end up being very smart
		// so for now this is just an example for one way of doing this

		int curX = this.x();
		int curY = this.y();
		// check to see if in center (just spawned)
		if ((curY > 215 && curY <= 250) && (curX >= 250 && curX <= 325)) {
			this.position(getInitialOutOfCagePos());
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
