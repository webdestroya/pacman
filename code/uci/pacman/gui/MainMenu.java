package code.uci.pacman.gui;
import code.uci.pacman.controllers.GameController;
import code.uci.pacman.*;
import ucigame.*;

/**
 * 
 * This will handle the display for Main Menu, and additional graphics on it.
 * @author GUI Team (Rick, MikeY, Cameron)
 *
 */
public class MainMenu{

	private Sprite singlePlayerButton;
	private Sprite multiplayerButton;
	private Sprite topScores;
	private Sprite quit;
	private int canvasWidth = 600;
	private int canvasHeight = 650;
	private Sound menuTheme;
	private int buttonStartHeight = 120;
	private int buttonHeight = 83;
	
	/**
	 * 
	 * Constructor for MainMenu, creates all the buttons in the menu 
	 * 
	 */
	
	public MainMenu() {
		//single player button creation
		menuTheme = GameController.getInstance().getPacInstance().getSound("sounds/final/MainMenu.mp3");
		
		singlePlayerButton = GameController.getInstance().getPacInstance().makeButton("SinglePlay",GameController.getInstance().getPacInstance().getImage("singleplayerButton.png"),
                249, 76);
		singlePlayerButton.position(canvasWidth/2 - singlePlayerButton.width()/2, buttonStartHeight);
				
		//multiplayer button creation
		multiplayerButton = GameController.getInstance().getPacInstance().makeButton("MultiPlay",GameController.getInstance().getPacInstance().getImage("multiplayerButton.png"),
                249, 76);
		multiplayerButton.position(canvasWidth/2 - multiplayerButton.width()/2,	buttonStartHeight + buttonHeight);
		
		//topScore button creation
		topScores = GameController.getInstance().getPacInstance().makeButton("TopScores",GameController.getInstance().getPacInstance().getImage("topScoresButton.png"),
                249, 76);
		topScores.position(canvasWidth/2 - singlePlayerButton.width()/2, buttonStartHeight + buttonHeight*2);
		
		//quite button creation
		quit = GameController.getInstance().getPacInstance().makeButton("Quit",GameController.getInstance().getPacInstance().getImage("quitButton.png"),
                249, 76);
		quit.position(canvasWidth/2 - singlePlayerButton.width()/2, buttonStartHeight + buttonHeight*3);
		
		
	}

	/**
	 * 
	 * Draws all the buttons in the MainMenu. Each button corresponds to a OnClick state located 
	 * in PacManGame.java.
	 * 
	 */
	public void draw(){
		singlePlayerButton.draw();
		multiplayerButton.draw();
		topScores.draw();
		quit.draw();
	}
	
	/**
	 *	 
	 * Plays the main theme in a loop until stopMenuTheme is called
	 * 
	 */
	public void startMenuTheme()
	{
		menuTheme.loop();		
	}
	
	/**
	 *	 
	 * Stops the main theme
	 *  
	 */
	public void stopMenuTheme()
	{
		menuTheme.stop();
	}
		
}
