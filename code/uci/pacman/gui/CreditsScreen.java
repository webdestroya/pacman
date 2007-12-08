package code.uci.pacman.gui;

import ucigame.*;
import java.util.*;
import code.uci.pacman.game.PacManGame;
import code.uci.pacman.controllers.GameController;



/**
 * This will display the credits at the end of the game
 * @author GUI Team (Rick, MikeY, Cameron)
 *
 */
public class CreditsScreen{	
	
	private ArrayList<String> frames;
	private Sound creditsSound;
	private int drawCounter;
	private Sprite backToMainButton; 
	private Sprite quitButton;
		
	// The constructor creates the ArrayList containing the filenames of 
	// each image in the opening.
	/**
	 * 
	 * Constructor for CreditsScreen; sets into sound, creates frames array, creates play again and quit buttons 
	 * 
	 */
	public CreditsScreen() {
		drawCounter = 1;
		creditsSound = GameController.getInstance().getPacInstance().getSound("sounds/final/CreditsTheme.mp3");
		frames = new ArrayList<String>();
		
		backToMainButton = GameController.getInstance().getPacInstance().makeButton("BackToMainFromCredits",GameController.getInstance().getPacInstance().getImage("mainmenubutton.png"),
                249, 76);
		backToMainButton.position(195, 450);
		
		for (int currentImage = 1; currentImage <= 16; currentImage++)
		{
			String workingString = "0";
			if (currentImage < 10)
			{
				workingString += ("0" + currentImage);
			}
			if (currentImage >= 10)
			{
				workingString += currentImage;
			}
			workingString += ".png";
			frames.add("credits/" + workingString);
		}		
	}
	
	/**
	 * 
	 * Draws the frames for the credits graphics, adds the buttons to play again or quit
	 * 
	 */
	public void draw(){
			if (drawCounter <= 16)
			{
				try
				{
					String frameLocation = frames.get(drawCounter);
					Sprite currentFrame =  GameController.getInstance().getPacInstance().makeSpriteFromPath(frameLocation);
					currentFrame.draw();
					Thread.sleep(4000);	//orig 1525					
					drawCounter ++;
				}
				catch (InterruptedException e)
				{
					System.out.println("Intro Thread was Interrupted!");
					e.printStackTrace();
				}
			}
			if (drawCounter == 17)
			{				
				backToMainButton.draw();				
			}
	}
	
	/**
	 * 
	 * Plays Credits theme
	 * 
	 */
	public void playCreditsTheme()
	{
		creditsSound.play();
	}
	
	/**
	 * 
	 * Stops Credits theme
	 * 
	 */
	public void stopCreditsTheme()
	{
		creditsSound.stop();
	}
}

