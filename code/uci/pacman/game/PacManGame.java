package code.uci.pacman.game;

import java.io.IOException;

import code.uci.pacman.controllers.*;
import code.uci.pacman.gui.*;
import code.uci.pacman.objects.stationary.*;
import code.uci.pacman.multiplayer.*;
import ucigame.*;

import java.util.*;

import com.sun.xml.internal.ws.util.StringUtils;

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

	private ScreenMode currentScene; // stores the current scene displayed

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

		// make the server or client connection
		setupServerOrClient();

	}

	private void initializeWindow() {
		control = GameController.setInstance(this);
		this.framerate(20);
		window.size(600, 650);
		window.title("Pac Man Fever");
	}

	private void displayIntroScreen() {
		// initialize screens
		introPlayer = new IntroPlayer();
		// show intro screen
		introPlayer.playIntroTheme();
		canvas.background(0, 0, 0); // Background has to be black for the intro (sorry for removing)
		showScene(ScreenMode.INTRO);
		introPlayer.playIntroTheme(); // Play intro theme after starting scene. (so it syncs properly)
	}

	private void displayMenuScreen() {
		//initialize menu
		mainMenu = new MainMenu();
		// stop intro theme
		introPlayer.stopIntroTheme();
		// show menu
		canvas.background(getImage("images/final/mainMenuBackGroundDim.png"));
		mainMenu.startMenuTheme();
		showScene(ScreenMode.MENU);
	}

	private void beginGame() {
		// stop menu theme
		mainMenu.stopMenuTheme();
		// show game
		canvas.background(0, 0, 0);
		control.startGame(); // start the game
		scoreBoard = new ScoreBoard();
		topScores = new TopScores();
		showScene(ScreenMode.GAME);
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

	/* Painting the Game Scenes */

	// Draws the "Intro" scene
	public void drawIntro() {
		canvas.clear();
		introPlayer.draw();
	}

	// Draws the "Menu" scene
	public void drawMenu() {
		canvas.clear();
		mainMenu.draw();
	}

	// Draws the "Scores" scene
	public void drawScores() {
		canvas.clear();
		topScores.draw();
	}

	// Draws the "Game" scene
	public void drawGame() {
		canvas.clear();
		control.nextMove();
		control.drawState();
		scoreBoard.draw();
	}

	// Draws the "GameOver" scene
	public void drawGameover() {
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

	/* Event Input Handling */

	/**
	 * 
	 * displays the menu and plays the menu theme.
	 * 
	 */
	public void onClickMenuStart() {
		if (isShowingScene(ScreenMode.INTRO)) {
			displayMenuScreen();
		}
	}

	/**
	 * 
	 * starts a single-player game.
	 * 
	 */
	public void onClickSinglePlay() {
		if (isShowingScene(ScreenMode.MENU)) {
			System.out.println("single player click");
			beginGame();
		}
	}

	/**
	 * 
	 * goes to the multiplayer menu.
	 * 
	 */
	public void onClickMultiPlay() {
		if (isShowingScene(ScreenMode.MENU)) {
			System.out.println("multi playter click");
			// beginGame();
		}
	}

	/**
	 * 
	 * shows top scores screen.
	 * 
	 */
	public void onClickTopScores() {
		if (isShowingScene(ScreenMode.MENU)) {
			System.out.println("topScore click");
			// beginGame();
		}
	}

	/**
	 * 
	 * writes out the high scores, then quits the game.
	 * 
	 */
	public void onClickQuit() {
		if (isShowingScene(ScreenMode.MENU)) {
			System.out.println("quit click");
			// beginGame();
		}
	}

	public void onKeyPressIntro() {
		if (keyboard.isDown(keyboard.S)) {
			displayMenuScreen();
		}
	}

	public void onMousePressed() {
		if (isShowingScene(ScreenMode.INTRO)) {
			displayMenuScreen();
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

	public void showScene(ScreenMode scene) {
		this.currentScene = scene;
		String sceneName = StringUtils.capitalize(scene.toString().toLowerCase());
		super.startScene(sceneName);
	}
	
	public boolean isShowingScene(ScreenMode scene) {
		return currentScene.equals(scene);
	}
}
