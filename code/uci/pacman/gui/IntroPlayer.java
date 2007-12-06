package code.uci.pacman.gui;

import ucigame.*;
import java.util.ArrayList;
import code.uci.pacman.game.PacManGame;
import code.uci.pacman.controllers.GameController;
//import java.io.FileNotFoundException;
//import java.io.IOException;


/**
 * 
 * This will display the opening at the beginning of the game
 * @author GUI Team (Rick, MikeY, Cameron)
 *  
 */
public class IntroPlayer{	
	
	private ArrayList<String> frames;
	private Sound introSound;
	private int drawCounter;
	private Sprite menuButton; 
		
	// The constructor creates the ArrayList containing the filenames of 
	// each image in the opening.
	/**
	 * 
	 * Constructor for IntroPlayer; sets into sound, creates frames array, creates menuStart button	 * 
	 * 
	 */
	public IntroPlayer() {
		drawCounter = 1;
		introSound = GameController.getInstance().getPacInstance().getSound("sounds/final/IntroTheme.mp3");
		frames = new ArrayList<String>();
		
		menuButton = GameController.getInstance().getPacInstance().makeButton("MenuStart",GameController.getInstance().getPacInstance().getImage("startbutton.png"),
                249, 76);
		menuButton.position(175, 450);
		
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
			frames.add("intro/" + workingString);
		}


	}
	
	/**
	 * 
	 * Draws the frames for the Intro graphics, adds the button to access MainMenu
	 * 
	 */
	public void draw(){
			//load intro music file
			//String themeLocation = 	("sounds/final/" + introSound);
			//Sound music = GameController.getInstance().getPacInstance().getSound(themeLocation);			
			//music.play();
			if (drawCounter < 30)
			{
				try
				{
					String frameLocation = frames.get(drawCounter);
					Sprite currentFrame =  GameController.getInstance().getPacInstance().makeSpriteFromPath(frameLocation);
					Thread.sleep(1000);	//orig 1525
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
				menuButton.draw();		
			}
	}
	
	/**
	 * 
	 * Plays Intro theme
	 * 
	 */
	public void playIntroTheme()
	{
		introSound.play();
	}
	
	/**
	 * 
	 * Stops Intro theme
	 * 
	 */
	public void stopIntroTheme()
	{
		introSound.stop();
	}
}
