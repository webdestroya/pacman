package code.uci.pacman.objects.stationary;

import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;

import code.uci.pacman.game.PacManGame;
import code.uci.pacman.multiplayer.*;


/**
 * 
 * Power Pellets are the larger pellets (only 4 on board)
 * When eaten by Pac Man, the ghosts go into Scatter Mode
 * 
 * @author Team Objects/AI
 *
 */
public class PowerPellet extends StationaryObject implements Eatable {
	
	private static final String PELLET_IMAGE_PATH = "pellet.png";
	
	/**
	 * 
	 * constructor creates the Power Pellet sprite in the game
	 * 
	 */
	public PowerPellet(int x, int y) {
		super(PELLET_IMAGE_PATH, x, y);
		if( PacManGame.gameType==1)
		{
			Server.send( PType.PPILLA, x(), y() );
		}

	}
	/**
	 * the point value of this object
	 */
	public static final int SCOREVALUE = 50;

	/**
	 * Called when a power pellet is eaten
	 * Tells the game controller that it's been eaten
	 */
	public void eaten() {
		if( PacManGame.gameType==1)
		{
			Server.send( PType.PPILLD, x(), y() );
		}

		control.pelletEaten(this);
	}

	
}
