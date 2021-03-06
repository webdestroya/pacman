package code.uci.pacman.objects.stationary;

import code.uci.pacman.objects.Eatable;
import code.uci.pacman.objects.StationaryObject;

import code.uci.pacman.game.PacManGame;
import code.uci.pacman.multiplayer.*;

/**
 * Pills are the small dots that get eaten for points The level is completed by
 * successfully eating every pill
 * 
 * @author Object Team
 * 
 */

public class Pill extends StationaryObject implements Eatable {

	private static final String PILL_IMAGE_PATH = "pill.png";

	/**
	 * the point value of this object
	 */
	public static final int SCOREVALUE = 10;

	/**
	 * 
	 * constructor creates the Pill sprite in the game
	 * 
	 */
	public Pill(int x, int y) {
		super(PILL_IMAGE_PATH, x, y);
		if( PacManGame.gameType==1)
		{
			Server.send( PType.PILLA, x(), y() );
		}
	}

	/**
	 * Called when a pill is eaten Tells the game controller that it's been
	 * eaten
	 */
	public void eaten() {
		if( PacManGame.gameType==1)
		{
			Server.send( PType.PILLD, x(), y() );
		}
		control.pillEaten(this);
	}

}
