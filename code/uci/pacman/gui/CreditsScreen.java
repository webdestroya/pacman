package code.uci.pacman.gui;

import ucigame.*;
import java.util.*;
import code.uci.pacman.game.PacManGame;
import code.uci.pacman.controllers.GameController;



/**
 * this will display the credits at the end of the game
 * @author GUI Team (Rick, MikeY, Cameron)
 *
 */
public class CreditsScreen{	
	
	private ArrayList<String> frames;
	private Sound creditsSound;
	private int drawCounter;
	private Sprite playAgainButton; 
	private Sprite quitButton;
		
	// The constructor creates the ArrayList containing the filenames of 
	// each image in the opening.
	/**
	 * 
	 * constructor for CreditsScreen; sets into sound, creates frames array, creates play again and quit buttons 
	 * 
	 */
	public CreditsScreen() {
		drawCounter = 1;
		creditsSound = GameController.getInstance().getPacInstance().getSound("sounds\\final\\CreditsTheme.mp3");
		frames = new ArrayList<String>();
		
		playAgainButton = GameController.getInstance().getPacInstance().makeButton("PlayAgain",GameController.getInstance().getPacInstance().getImage("images/final/singleplayerButton.png"),
                249, 76);
		playAgainButton.position(200, 450);
		
		for (int currentImage = 1; currentImage <= 30; currentImage++)
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
			frames.add("images\\final\\intro\\" + workingString);
		}


	}
	
	/**
	 * 
	 * draws the frames for the credits graphics, adds the buttons to play again or quit
	 * 
	 */
	public void draw(){
			if (drawCounter < 30)
			{
				try
				{
					String frameLocation = frames.get(drawCounter);
					Sprite currentFrame =  GameController.getInstance().getPacInstance().makeSpriteFromPath(frameLocation);
					Thread.sleep(1525);	//orig 1525
					currentFrame.draw();
					drawCounter ++;
				}
				catch (InterruptedException e)
				{
					System.out.println("Intro Thread was Interrupted!");
					e.printStackTrace();
				}
			}
			if (drawCounter == 30)
			{
				playAgainButton.draw();				
			}
	}
	
	/**
	 * 
	 * plays Credits theme
	 * 
	 */
	public void playCreditsTheme()
	{
		creditsSound.play();
	}
	
	/**
	 * 
	 * stops Credits theme
	 * 
	 */
	public void stopCreditsTheme()
	{
		creditsSound.stop();
	}
}

