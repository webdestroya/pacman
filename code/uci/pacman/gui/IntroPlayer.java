package code.uci.pacman.gui;

import ucigame.*;
import java.util.*;
import code.uci.pacman.game.PacManGame;
//import java.io.FileNotFoundException;
//import java.io.IOException;


/**
 * 
 * @author GUI Team (Rick, MikeY, Cameron)
 * this will display the opening at the beginning of the game
 *
 */
public class IntroPlayer{	
	
	ArrayList<String> frames;
	String introSound;
	int shouldDraw;
	PacManGame game;
	//canvas canv;
	
	// The constructor creates the ArrayList containing the filenames of 
	// each image in the opening.
	public IntroPlayer(int play, PacManGame mainGame) {
		shouldDraw = play;
		game = mainGame;
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

	}
	
	public void draw(){
		if (shouldDraw == 1) // if you should draw, then draws.
		{
			String themeLocation = 	("sounds\\final\\" + introSound);
			Sound music = game.getSound(themeLocation);			
			music.play();
			
			try
			{
				for(int i = 0; i < frames.size(); i++)
				{
					String frameLocation = frames.get(i);
					//Sprite currentFrame = game.makeSpriteFromPath(frameLocation);  
					Image currentFrame = game.getImage(frameLocation);
					//Sprite currentFrame =  game.makeSprite(currentImage, 600, 650);
					//currentFrame.position(0, 0);
					currentFrame.draw(0, 0);
					//canvas.background(currentImage);
					Thread.sleep(1000);
					//currentFrame.hide();
					//frames.remove(0);
					//canvas.clear();
				}				
			}
			catch (InterruptedException e)
			{
				System.out.println("Intro Thread was Interrupted!");
				e.printStackTrace();
			}
			
			shouldDraw = 0;
		}
		
		
	}
}
