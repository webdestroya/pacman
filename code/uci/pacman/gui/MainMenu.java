package code.uci.pacman.gui;
import code.uci.pacman.controllers.GameController;
import ucigame.*;

/**
 * 
 * @author GUI Team
 * this will handle the display for Main Menu, and additional
 * graphics on it.
 *
 */
public class MainMenu{

	private Sprite singlePlayerButton;
	private Sprite multiplayerButton;
	private Sprite topScores;
	private Sprite quit;
	private int canvasWidth = 600;
	private int canvasHeight = 650;
	
	
	public MainMenu() {
		singlePlayerButton = GameController.getInstance().getPacInstance().makeButton("SinglePlay",GameController.getInstance().getPacInstance().getImage("images/final/singleplayerButton.png"),
                249, 76);
		singlePlayerButton.position(canvasWidth/2 - singlePlayerButton.width()/2,
				canvasHeight/2 - singlePlayerButton.height()/2);
		
	}

	/**
	 * 
	 * this method is responsible for drawing the MainMenu sprites,
	 * and any other sprites that are pinned to it.
	 * 
	 */
	public void draw(){
		//Sprite currentFrame =  GameController.getInstance().getPacInstance().makeSpriteFromPath(frameLocation);
		//Thread.sleep(1525);	
		//currentFrame.draw();
	}
}
