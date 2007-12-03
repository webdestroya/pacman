package code.uci.pacman.gui;

import ucigame.*;
import java.util.*;
import code.uci.pacman.game.PacManGame;
import code.uci.pacman.controllers.GameController;
//import java.io.FileNotFoundException;
//import java.io.IOException;


/**
 * 
 * @author GUI Team (Rick, MikeY, Cameron)
 * this will display the opening at the beginning of the game
 *
 */
public class IntroPlayer{	
	
	private ArrayList<String> frames;
	private String introSound;
	private int drawCounter;
		
	// The constructor creates the ArrayList containing the filenames of 
	// each image in the opening.
	public IntroPlayer() {
		drawCounter = 1;
		introSound = "IntroTheme.mp3";
		frames = new ArrayList<String>();
		
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

		String themeLocation = 	("sounds\\final\\" + introSound);
		Sound music = GameController.getInstance().getPacInstance().getSound(themeLocation);			
		music.play();
	}
	
	public void draw(){
			//load intro music file
			//String themeLocation = 	("sounds\\final\\" + introSound);
			//Sound music = GameController.getInstance().getPacInstance().getSound(themeLocation);			
			//music.play();
			if (drawCounter < 30)
			{
				try
				{
					String frameLocation = frames.get(drawCounter);
					Sprite currentFrame =  GameController.getInstance().getPacInstance().makeSpriteFromPath(frameLocation);
					Thread.sleep(1525);	
					currentFrame.draw();
					drawCounter ++;
				}
				catch (InterruptedException e)
				{
					System.out.println("Intro Thread was Interrupted!");
					e.printStackTrace();
				}
			}
	}
}
