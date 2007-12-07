package code.uci.pacman.objects.controllable;


import java.util.Random;

import code.uci.pacman.ai.AI;
import code.uci.pacman.controllers.WallController;
import code.uci.pacman.game.*;
import code.uci.pacman.objects.ControllableObject;

import code.uci.pacman.multiplayer.*;


/**
 * Represents a Ghost character that chases PacMan.  A Ghost can be controlled by human
 * or by an AI as denoted by the boolean isPlayer.  Ghosts can chase PacMan and try to
 * eat him by colliding with him.  If PacMan eats a Power Pellet, scatter mode is 
 * initiated and a ghost can be eaten if touched by PacMan.  
 * @author Team Objects/AI
 * 
 */

public abstract class Ghost extends ControllableObject implements AI {

	private static final int GHOST_WIDTH = 22;
	private static final int GHOST_HEIGHT = 22;
	public static final int GHOST_FRAMERATE = 5;
	private static final int CAGE_POS = 250;

	// MITCH
	protected Direction lastDirection = Direction.LEFT;
	protected Direction curDirection;
	//isPlayer means the same thing...
	//protected boolean isBeingControlled = false;
	
	private boolean isPlayer;

	/**
	 * Constructs a ghost with a sprite, initial position, speed, and a boolean denoting if it
	 * is controlled by a human or by AI.  
	 * @param imgPath the image/sprite associated with the ghost
	 * @param x it's initial x coordinate on the level
	 * @param y it's initial y coordinate on the level
	 * @param speed the speed at which the ghost moves
	 * @param isPlayer true if the ghost is controlled by a human; false if it's controlled by AI
	 */
	public Ghost(String imgPath, int x, int y, int speed, boolean isPlayer) {
		super(imgPath, new int[] {0,0}, GHOST_WIDTH, GHOST_HEIGHT, GHOST_FRAMERATE, x, y);
		super.addFramesForAnimation("scatter", "ghost_scatter.png", 0, 0); //TODO put in real scatter animation and frames
		super.speed = speed;
		this.isPlayer = isPlayer;
	}

	/**
	 * the point value of this object
	 */
	private int scoreValue;
	private boolean scatter;
	


	/**
	 * This returns control of the ghost back to the AI functions
	 *
	 */
	public void returnAI()
	{
		isPlayer = false;
	}

	/**
	 * This allows the direction of the ghost to be set, it automatically
	 * sets the ghost as a playable object in the process
	 * @param dir The direction of the ghost
	 */
	public void setDirection(Direction dir)
	{
		isPlayer = true;
		lastDirection = curDirection;
		curDirection = dir;
	}

	/**
	 * Tells the game controller that this ghost has been eaten.
	 */
	public void eaten(){
		control.ghostEaten(this);
	}
	
	/**
	 * Tests if the ghost is in Scatter Mode.
	 * @return true if the ghost is currently in Scatter Mode.
	 */
	public boolean isScattered() {
		return scatter;
	}
	
	/**
	 * Tells the ghost to go into Scatter Mode.
	 */
	public void scatter(){
		scatter = true;
		if( PacManGame.gameType==1)
		{
			Server.send(PType.DGHOST);
		}
		super.setAnimationMode("scatter"); //this will tell the ghost to switch to scatter mode animations
		/* 
		 * 
		NOTE: Me and Thomas added got Ucigame source code and then I added some useful new functions
		now each sprite can have multiple "modes" of animation. Sprites can now have their 
		default animation mode just like before with a set of frames. Now though, you can add
		alternate animation modes with different frames as well.
		
		A ghost has "default" mode which is for his attacking frames and 
		"scatter" animation mode which shows a different set of animations for scatter. 
		Sprites can switch back and forth easily using the command above. 
		Look in the constructor for where I set the mode for scatter
		
		Also NOTE this doesn't require any API changes because I am simply
		modifying Ucigame of which we are free to modify since its not part
		of our application directly
		
		If you have any question, or problems ask me - Nathan
		
		*/
	}
	
	/**
	 * Tells the ghost to come out of Scatter Mode.
	 */
	public void unScatter() {
		scatter = false;
		if( PacManGame.gameType==1)
		{
			Server.send(PType.AGHOST);
		}

		setAnimationMode("default"); //this will tell the ghost to switch back to his regular animations
		//see above in scatter for explanation or just ask me --> nathan
	}
	
	/***
	 * Respawns the ghost back within the cage and disables scatter for the ghost.
	 */
	public void respawnInCage() {
		Random r = new Random();
		int randomOffset = r.nextInt(50);
		super.position(CAGE_POS + randomOffset, CAGE_POS);
		this.unScatter();
	}
	
	/**
	 * Returns true if the ghost is controlled by a player.
	 * @return true if the ghost is controlled by a human; false if it is controlled by AI 
	 */
	public boolean isPlayer(){
		return isPlayer;
	}

	/**
	 * Gets the score of the ghost.
	 * @return The score of the ghost.
	 */
	public int getValue() {
		return scoreValue;
	}

	/**
	 * This changes the sprite based on its direction.
	 * @param d direction of the ghost.
	 */
	@Override
	//this is for changing the sprite based on direction
	protected void spriteForDirection(Direction d) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This gets a move direction using the ghost's AI.
	 * @return Direction
	 */
	protected abstract Direction getAIMove();
	
	
	/* 
	 * He tries to get you by your relative position. 
	 * He takes the fastest route to find you. I believe he 
	 * tries to line up with you horizontally first, then vertically.
	 */
	public Direction getMove()
	{
		if(isPlayer)
		{
			int curX = this.x();
			int curY = this.y();
			if ((curY > 215 && curY <= 250) && (curX >= 250 && curX <= 325))
			{
				this.position(this.x(), 205);
				lastDirection = Direction.LEFT;
				curDirection = Direction.UP;
			}
			return curDirection;
		}
		else
		{
			// TODO: TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
			// MUST RESET THIS BACK TO GETAIMOVE()
			//
			// * DONT EDIT THIS FOR NOW, MITCH NEEDS IT FOR NETWORK DEVELOPMENT
			//
			if(PacManGame.gameType == 2)
			{
				return curDirection;
			}
			else
			{
				return curDirection;
				//return getAIMove();
			}
		}
	}


	public boolean moveIsAllowed(Direction d)
	{
		WallController walls = GameState.getInstance().getWalls();
		if(d == Direction.UP && walls.willCollideAtPos(this,0,1))
			return true;
		if(d == Direction.DOWN && walls.willCollideAtPos(this,0,1))
			return true;
		if(d == Direction.LEFT && walls.willCollideAtPos(this,-1,0))
			return true;
		if(d == Direction.RIGHT && walls.willCollideAtPos(this,1,0))
			return true;
		else
			return false;
	}

}
