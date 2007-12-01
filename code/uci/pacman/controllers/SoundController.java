package code.uci.pacman.controllers;

import ucigame.Sound;
import code.uci.pacman.game.PacManGame;

public class SoundController {
	
	//private static Sound ambient = makeSound("pacchomp.mp3");
	//private static Sound pellet = makeSound("fruiteat.mp3");
	//private static Sound mainMenu = makeSound("menu.mp3");
	//private static Sound pill = makeSound("fruiteat.mp3");
	//private static Sound topScore = makeSound("scores.mp3");
	
	public static void pelletEaten(){
	//	pellet.play();
	}
	
	public static void pillEaten(){
	//	pill.play();
	}
	
	public static void startAmbient(){
	//	ambient.loop();
	}
	
	private static void stopAllSounds(){
		
	}
	
	private static Sound makeSound(String file){
		return GameController.getInstance().getPacInstance().getSound("sounds\\final\\"+file);
	}

}
