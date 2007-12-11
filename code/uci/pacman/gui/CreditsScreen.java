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
	private boolean canClear;
		
	// The constructor creates the ArrayList containing the filenames of 
	// each image in the opening.
	/**
	 * 
	 * Constructor for CreditsScreen; sets into sound, creates frames array, creates play again and quit buttons 
	 * 
	 */
	public CreditsScreen() {
		drawCounter = 1;
		canClear = true;
		creditsSound = GameController.getInstance().getPacInstance().getSound("sounds/final/CreditsTheme.mp3");
		frames = new ArrayList<String>();
		
		backToMainButton = GameController.getInstance().getPacInstance().makeButton("BackToMainFromCredits",GameController.getInstance().getPacInstance().getImage("mainmenubutton.png"),
                249, 76);
		backToMainButton.position(300, 42);
		
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
		
		//frames.add("credits/001.png");
	}
	
	/**
	 * 
	 * Draws the frames for the credits graphics, adds the button to go to main menu
	 * 
	 */
	public void draw(){
			if (drawCounter < 16)
			{
				try
				{
					String frameLocation = frames.get(drawCounter);
					Sprite currentFrame =  GameController.getInstance().getPacInstance().makeSpriteFromPath(frameLocation);
					Thread.sleep(4500);	//orig 4000
					currentFrame.draw();
					drawCounter ++;
				}
				catch (InterruptedException e)
				{
					System.out.println("Credits Thread was Interrupted!");
					e.printStackTrace();
				}
			}
			if (drawCounter == 16)
			{				
				Sprite finalFrame = GameController.getInstance().getPacInstance().makeSpriteFromPath(frames.get(15));				
				finalFrame.draw();
				backToMainButton.draw();
				canClear = false;				
			}
	}

	/**
	 * 
	 * Used to check if drawCredits() in PacManGame can clear the canvas before drawing the next frame
	 * @return boolean 
	 */
	public boolean canClear()
	{
		return canClear;
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

