package code.uci.pacman.controllers;

import ucigame.Sound;

/**
 * 
 * This controller is responsible for handling all sound related
 * functions within the game world. The game controller
 * invokes this controller whenever a sound need to be played 
 * or stopped.
 * 
 * @author The Game Team
 *
 */
public class SoundController {
	
	private static Sound ambient = makeSound("gs_siren_soft2.mp3");
	private static Sound pellet = makeSound("gs_chomp.mp3");
	private static Sound pill = makeSound("gs_chomp.mp3");
	private static Sound fruit = makeSound("gs_eatfruit.mp3");
	private static Sound ghost = makeSound("gs_eatghost.mp3");
	private static Sound pacman = makeSound("gs_pacmandies.mp3");
	private static Sound start = makeSound("gs_start.mp3");
	private static Sound fever = makeSound("fever_clip.mp3");
	
	
	/**
	 * Plays the appropriate sound for a pellet being eaten, once and then stops.
	 */
	public static void pelletEaten(){
		pellet.play();
	}
	
	/**
	 * Plays the appropriate sound for a pill being eaten, once and then stops.
	 */
	public static void pillEaten(){
		pill.play();
	}
	
	/**
	 * Not in the javadocs. but do we need to take in account for the other sounds? i think we forgot some here like this one.
	 */
	public static void fruitEaten(){
		fruit.play();
	}
	
	/**
	 * Not in the javadocs. but do we need to take in account for the other sounds? i think we forgot some here like this one.
	 */
	public static void ghostEaten(){
		ghost.play();
	}
	
	/**
	 * Not in the javadocs. but do we need to take in account for the other sounds? i think we forgot some here like this one.
	 */
	public static void pacmanEaten(){
		feverSwitch(false); //stop fever
		pacman.play();
	}
	
	/**
	 * Not in the javadocs. but do we need to take in account for the other sounds? i think we forgot some here like this one.
	 */
	public static void gameStarted(){
		start.play();
	}
	
	/**
	 * Not in the javadocs. but do we need to take in account for the other sounds? i think we forgot some here like this one.
	 */
	public static void feverSwitch(boolean play){
		if (play) { stopAllSounds(); fever.play(); } 
		else { fever.stop(); startAmbient(); }
	}
	
	/**
	 * Begins a loop playing the appropriate ambient sound over and over again 
	 * during the duration of the game.
	 */
	public static void startAmbient(){
		ambient.loop();
	}
	
	public static void stopAllSounds(){
		pellet.stop();
		pill.stop();
		ambient.stop();
		fever.stop();
		fruit.stop();
		ghost.stop();
		pacman.stop();
		start.stop();
	}
	
	private static Sound makeSound(String file){
		return GameController.getInstance().getPacInstance().getSound("sounds/final/"+file);
	}

}
