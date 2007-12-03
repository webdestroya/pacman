package code.uci.pacman.game;

import java.io.IOException;

import code.uci.pacman.controllers.*;
import code.uci.pacman.gui.*;
import code.uci.pacman.objects.stationary.*;
import code.uci.pacman.multiplayer.*;
import ucigame.*;

import java.util.*;

/**
 * 
 * @author The Game Team The main game class
 */
public class PacManGame extends Ucigame {

	private static final long serialVersionUID = -917916728311505169L;
	private GameController control;
	private ScoreBoard scoreBoard;
	private TopScores topScores;
	private IntroPlayer introPlayer; // for playing the intro

	public static int multiplayerType = 1; // 1=server, 2=client
	public static String hostname = "127.0.0.1"; // used for network

	/* Main Class */

	public static void main(String[] args) {
		// System.out.println(args[0]+"|"+args[1]+"|"+args[2]);

		List<String> cargs = Arrays.asList(args);

		if (cargs.contains("CLIENT")) {
			System.out.println("Starting Client");
			PacManGame.multiplayerType = 2;
			PacManGame.hostname = (String) cargs.get(2);
		}

		Ucigame.main(args);

		// System.out.println(args[2]);
	}

	/* Initialization */

	public void setup() {
		generatePositions(false);
		initializeWindow(); // creates the window, sets the title, initialize
		// control

		// This code is for displaying the opening
		// Pass a 1 if you want to play the intro or 0 to skip it
		// Doesn't work right now
		// introPlayer = new IntroPlayer(1, this);
		startScene("Intro");
		//startScene("Game"); // switch to the "scene" containing the actual game
		setupServerOrClient(); //make the server or client connection

	}

	private void initializeWindow() {
		framerate(20);
		window.size(600, 650);
		canvas.background(0, 0, 0);
		window.title("Pac Man Fever");
		control = GameController.setInstance(this);
		control.startGame(); // start the game
		scoreBoard = new ScoreBoard();
		topScores = new TopScores();
	}

	private void setupServerOrClient() {
		// Make the server
		if (PacManGame.multiplayerType == 1) {
			try {
				new Server().start();
			} catch (Exception e) {
			}
		} else if (PacManGame.multiplayerType == 2) {
			Client.hostname = hostname;
		}
	}

	private void generatePositions(boolean run) {
		try {
			if (run) {
				ItemGenerator.execute();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*  Painting the Game Scenes  */

	//Draws the "Menu" scene
	public void drawMenu() {

	}

	//Draws the "Scores" scene
	public void drawScores() {
		canvas.clear();
		topScores.draw();
	}

	//Draws the "Game" scene
	public void drawGame() {
		canvas.clear();
		control.nextMove();
		control.drawState();
		scoreBoard.draw();
	}

	//Draws the "Intro" scene
	public void drawIntro() {
		canvas.clear();
        Sprite button1 = makeButton("Play",
                getImage("images\\final\\singleplayerButton.png", 0, 255, 0),
                249, 228);
        button1.draw();
		//introPlayer.draw();
		//screwAround(); 
        ///////////////////// MIKE THIS IS WHERE YOU PUT YOUR INTO and from your intro you need to call 
        ///////////////////// startScene("Game"); to start the game. so put that in a button somewhere. 
        ///////////////////// I made a basic start game screen, to show you about how it works.. my button sucks lol
	}

//	this was just a test
	public void screwAround()
	{
		String themeLocation = ("sounds\\final\\IntroTheme.mp3");
		Sound music = getSound(themeLocation);
		music.play();
	}

	//Draws the "GameOver" scene
	public void drawGameOver() {
		canvas.clear();
		canvas.font("Tahoma", PacManGame.BOLD, 40, 255, 255, 255);
		canvas.putText("GAME OVER", 200, 300);
		canvas.font("Tahoma", PacManGame.BOLD, 20, 255, 255, 255);
		canvas.putText("Press R to Try Again", 210, 340);
	}

	/* Timer Handling */

	public void startFruitTimer() {
		startTimer("removeFruit", Fruit.SHOW_FRUIT_DURATION);
	}

	public void removeFruitTimer() {
		stopTimer("removeFruit");
		control.hideFruit();
	}

	public void startScatterTimer() {
		startTimer("unScatterGhosts", GhostController.SCATTERSECONDS);
	}

	public void unScatterGhostsTimer() {
		stopTimer("unScatterGhosts");
		control.unscatterGhosts();
	}

	public void startGameTimer(){
		stopTimer("startGame");
		startScene("Game");
	}
	/* Event Input Handling */

	public void onClickPlay(){
		startScene("Game");
	}
	
	public void onKeyPressGame() {
		// // Arrow keys and WASD keys move the paddle
		if (keyboard.isDown(keyboard.UP, keyboard.W))
			control.setPacManDirection(Direction.UP);
		if (keyboard.isDown(keyboard.DOWN, keyboard.S))
			control.setPacManDirection(Direction.DOWN);
		if (keyboard.isDown(keyboard.LEFT, keyboard.A))
			control.setPacManDirection(Direction.LEFT);
		if (keyboard.isDown(keyboard.RIGHT, keyboard.D))
			control.setPacManDirection(Direction.RIGHT);

		if (keyboard.isDown(keyboard.R)) {
			control.startGame();
		}
	}

	public void onKeyPressGameOver() {
		if (keyboard.isDown(keyboard.R)) {
			startScene("Game");
			control.startGame();
		}
	}

	public Sprite makeSpriteFromPath(String string) {
		return makeSprite(getImage(string));
	}

}
