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
	
	//private static Sound ambient = makeSound("pacchomp.mp3");
	private static Sound pellet = makeSound("fruiteat.mp3");
	//private static Sound mainMenu = makeSound("menu.mp3");
	private static Sound pill = makeSound("fruiteat.mp3");
	//private static Sound topScore = makeSound("scores.mp3");
	
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
	 * Begins a loop paying the appropriate ambient sound over and over again 
	 * during the duration of the game.
	 */
	public static void startAmbient(){
		//ambient.loop();
	}
	
	//private static void stopAllSounds(){
		//pellet.stop();
		//pill.stop();
		//ambient.stop();
	//}
	
	private static Sound makeSound(String file){
		return GameController.getInstance().getPacInstance().getSound("sounds/final/"+file);
	}

}
