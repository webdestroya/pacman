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
	private MainMenu mainMenu;
	private IntroPlayer introPlayer; // for playing the intro
	
	public static String font = "Dialog.bold";
	
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
		
		// creates the window, sets the title, initialize
		initializeWindow(); 
		
		// starts the intro for the game
		displayIntroScreen();
		
		//make the server or client connection
		setupServerOrClient(); 

	}

	private void initializeWindow() {
		control = GameController.setInstance(this);
		this.framerate(20);
		window.size(600, 650);
		window.title("Pac Man Fever");        
	}
	
	private void displayIntroScreen() {
		//initialize screens
		introPlayer = new IntroPlayer();
		mainMenu = new MainMenu();
		//show intro screen
		startScene("Intro");
		introPlayer.playIntroTheme();
	}
	
	private void displayMenuScreen() {
		//stop intro theme
		introPlayer.stopIntroTheme();
		//show menu
		canvas.background(getImage("images/final/mainMenuBackGroundDim.png"));
		mainMenu.startMenuTheme();
		startScene("Menu");
	}
	
	private void beginGame() {
		//stop menu theme
		mainMenu.stopMenuTheme();
		//show game
		canvas.background(0, 0, 0);
		control.startGame(); // start the game
		scoreBoard = new ScoreBoard();
		topScores = new TopScores();
		startScene("Game");
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

	//Draws the "Intro" scene
	public void drawIntro() {
		introPlayer.draw();
	}
	
	//Draws the "Menu" scene
	public void drawMenu() {
		canvas.clear();
		mainMenu.draw();
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

	//Draws the "GameOver" scene
	public void drawGameOver() {
		canvas.clear();
		canvas.font(PacManGame.font, PacManGame.BOLD, 40, 255, 255, 255);
		canvas.putText("GAME OVER", 200, 300);
		canvas.font(PacManGame.font, PacManGame.BOLD, 20, 255, 255, 255);
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

	/**
	 * 
	 * displays the menu and plays the menu theme. 
	 * 
	 */
	public void onClickMenuStart()
	{
		displayMenuScreen();
	}

	/**
	 * 
	 * starts a single-player game. 
	 * 
	 */
	public void onClickSinglePlay(){
		System.out.println("single player click");
		beginGame();
	}
	
	/**
	 * 
	 * goes to the multiplayer menu. 
	 * 
	 */
	public void onClickMultiPlay(){
		System.out.println("multi playter click");
		//startScene("Game");
	}
	
	/**
	 * 
	 * shows top scores screen. 
	 * 
	 */
	public void onClickTopScores(){
		System.out.println("topScore click");
		//startScene("Game");
	}
	
	/**
	 * 
	 * writes out the high scores, then quits the game. 
	 * 
	 */
	public void onClickQuit(){
		System.out.println("quit click");
		//startScene("Game");
	}
	
	public void onKeyPressIntro() {
		System.out.println("test");
		if (keyboard.isDown(keyboard.R)) {
			beginGame();
		}
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
			beginGame();
		}
	}

	public void onKeyPressGameOver() {
		if (keyboard.isDown(keyboard.R)) {
			beginGame();
		}
	}

	public Sprite makeSpriteFromPath(String string) {
		return makeSprite(getImage(string));
	}
}
