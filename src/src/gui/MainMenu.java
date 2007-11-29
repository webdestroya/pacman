package src.gui;
import ucigame.*;
import java.util.*;

/**
 * 
 * @author GUI Team
 * this will keep track of top 10 stores and names
 *
 */
public class MainMenu extends Sprite{

	Sprite singlePlayerButton;
	Sprite multiplayerButton;
	Sprite topScores;
	Sprite quit;
	
	public MainMenu(int arg0, int arg1) {
		super(arg0, arg1);

		this.pin(singlePlayerButton, x, y);
		this.pin(multiplayerButton, x, y);
		this.pin(topScores, x, y);
		this.pin(quit, x, y);
	}

	/**
	 * 
	 * this method is responsible for drawing the MainMenu sprites,
	 * and any other sprites that are pinned to it.
	 * 
	 */
	public void draw(){
		
	}
}
