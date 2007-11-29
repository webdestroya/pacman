package src.gui;
import ucigame.*;
import java.util.*;

/**
 * 
 * @author GUI Team
 * this will handle the display for Multiplayer Menu, and
 * additional buttons on it.
 *
 */
public class MultiplayerMenu extends Sprite{

	Sprite host;
	Sprite join;
	Sprite backToMainMenu;
	
	private int x, y;
	
	public MultiplayerMenu(int arg0, int arg1) {
		super(arg0, arg1);

		this.pin(host, x, y);
		this.pin(join, x, y);
		this.pin(backToMainMenu, x, y);
	}

	/**
	 * 
	 * this method is responsible for drawing the MultiplayreMenu
	 * sprites, and any other sprites that are pinned to it.
	 * 
	 */
	public void draw(){
		
	}
}
