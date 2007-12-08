package code.uci.pacman.game;

import java.io.IOException;

import code.uci.pacman.controllers.*;
import code.uci.pacman.gui.*;
import code.uci.pacman.objects.stationary.*;
import code.uci.pacman.multiplayer.*;
import ucigame.*;

/**
 * 
 * The main game class for PacMan, This class extends from the UciGame class
 * which is part of the UciGame game framework, Thanks to Professor Frost for
 * making this great framework which made our lives easier, This is the class
 * that should be run to play PacMan.
 * 
 * @author The Game Team
 */
public class PacManGame extends Ucigame {

	private static final long serialVersionUID = -917916728311505169L;
	private GameController control;
	private ScoreBoard scoreBoard;
	private TopScores topScores;
	private MainMenu mainMenu;
	private MultiplayerMenu multiMenu;
	private IntroPlayer introPlayer; // for playing the intro
	private CreditsScreen creditsScreen;
	private ScreenMode currentScene; // stores the current scene displayed
	public static String font = "Dialog.bold";
	public static int multiplayerType = 1; // 1=server, 2=client
	public static int gameType = 1; // 1=single, 2 = multi

	/**
	 * This was added to override the stupid UCI game thing that requires the param twice,
	 * this allows me to put the whole game into a JAR file.
	 * @param args the list of command line args
	 */
	public static void main(String[] args)
	{
		String[] args2 = new String[1];
		args2[0] = "code.uci.pacman.game.PacManGame";
		System.out.println("Current User: " + System.getProperty("user.name") );
		Ucigame.main(args2);
	}

	/**
	 * Initialization of the game
	 */
	public void setup() {
		generatePositions(false);
		// creates the window, sets the title, initialize
		initializeWindow();
		// starts the intro for the game
		showIntroScreen();
	}

	private void initializeWindow() {
		Ucigame.rootImagePath = "images/final/";
		// set global game control 
		control = GameController.setInstance(this);
		// set framerate
		this.framerate(20);
		// set window size
		window.size(600, 650);
		// set window title
		window.title("Pac Man Fever");
	}
	
	private void showIntroScreen() {
		// initialize screens
		introPlayer = new IntroPlayer();
		// show intro screen
		canvas.background(0, 0, 0); 
		// This part was added so that it could better sync.  Untested.
		try
		{
			System.out.println("LOADING INTRO!");
			Thread.sleep(3000);
			showScene(ScreenMode.INTRO);
			introPlayer.playIntroTheme();
		}
		catch(InterruptedException e)
		{
			System.out.println("Intro thread interrupted before start!");
			e.printStackTrace();
		}
	}
	
	private void showCreditsScreen() {
		topScores.stopTopScoresTheme();
		creditsScreen = new CreditsScreen();
		canvas.background(getImage("credits/creditsbackground.png"));		
		creditsScreen.playCreditsTheme();
		showScene(ScreenMode.CREDITS);		
	}
	

	private void showMenuScreen() {
		// stop intro theme
		introPlayer.stopIntroTheme();
		// initialize menu
		mainMenu = new MainMenu();
		// show menu
		canvas.background(getImage("mainMenuBackGroundDim2.png"));
		mainMenu.startMenuTheme();
		showScene(ScreenMode.MENU);
	}

	/**
	 * This displays the screen of the game playing
	 * It is used by the Client to start the game
	 *
	 */
	public void showGameScreen() {
		// stop menu theme
		mainMenu.stopMenuTheme();
		introPlayer.stopIntroTheme();
		// show game
		canvas.background(0, 0, 0);
		control.startGame(); // start the game
		scoreBoard = new ScoreBoard();
		showScene(ScreenMode.GAME);
	}

	/**
	 * Shows the Multi-player screen
	 */
	private void showMultiGameScreen() {
		// initialize multi-player
		multiMenu = new MultiplayerMenu();
		// set multi-player background
		canvas.background(getImage("multiplayermenu.png"));
			//ODO: add multiplyer theme music here
			//****
			//multiMenu.startMultiPlayerTheme();
			//***
		//set scene to multi-player game
		showScene(ScreenMode.MULTIGAME);
	}
	
	/**
	 * Shows the top scores screen
	 */
	public void showScoresScreen() {
		// initialize top scores
		SoundController.stopAllSounds();
		topScores = new TopScores();
		mainMenu.stopMenuTheme();		
		canvas.background(getImage( "topscores.png"));
		topScores.startTopScoresTheme();
		showScene(ScreenMode.SCORES);
	}
	
	/**
	 * Shows the game over screen
	 */
	public void showGameOverScreen() {
		boolean f = true;
		canvas.background(0, 0, 0);
		//stop all gameplay sounds
		SoundController.stopAllSounds();
		showScene(ScreenMode.GAMEOVER);
	}

	/**
	 * This is used to show the player that the client is waiting for servers to connect to
	 */
	private void showMpwaitingScreen()
	{
		mainMenu.stopMenuTheme();
		introPlayer.stopIntroTheme();
		canvas.background(0, 0, 0);
		showScene(ScreenMode.MPWAITING);
	}
	
	private void startPacManServer()
	{
		try
		{
			new Server().start();
		}
		catch (Exception e){}
	}

	private void startPacManClient()
	{
		try
		{
			new Client().start();
		}
		catch (Exception e){}
		showMpwaitingScreen();
	}

	/**
	 * Draws the Multi-player waiting screen
	 */
	public void drawMpwaiting() {
		canvas.clear();
		canvas.font(PacManGame.font, PacManGame.BOLD, 40, 255, 255, 255);
		canvas.putText("WAITING FOR SERVER", 100, 300);
		canvas.font(PacManGame.font, PacManGame.BOLD, 20, 255, 255, 255);
		canvas.putText("PacMan is currently listening for open games", 80, 340);
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

	/**
	 * Draws the "Intro" scene
	 */
	public void drawIntro() {		
		introPlayer.draw();
	}
	
	public void drawCredits() {
		if(creditsScreen.canClear())
		{
			canvas.clear();
		}
		creditsScreen.draw();
	}

	/**
	 * Draws the "Menu" scene
	 */
	public void drawMenu() {
		canvas.clear();
		mainMenu.draw();
	}

	/**
	 * Draws the "Scores" scene
	 */
	public void drawScores() {
		canvas.clear();
		topScores.draw();
	}

	/**
	 * Draws the "Game" scene
	 */
	public void drawGame() {
		canvas.clear();
		control.nextMove();
		control.drawState();
		scoreBoard.draw();
	}

	/**
	 * Draws the Multi-player menu
	 */
	public void drawMultigame() {
		canvas.clear();
		multiMenu.draw();
	}
	
	/**
	 * Draws the "Game Over" scene
	 */
	public void drawGameover() {
		//draw gameover screen
		canvas.clear();
		canvas.font(PacManGame.font, PacManGame.BOLD, 40, 255, 255, 255);
		canvas.putText("GAME OVER", 180, 300);
		canvas.font(PacManGame.font, PacManGame.BOLD, 20, 255, 255, 255);
		canvas.putText("Press R to Try Again or T to see top scores", 100, 340);
		//Sprite topScoreButton = makeButton("TopScores", getImage("topScoresButton.png"), 249, 76);
		//topScoreButton.position(150, 400);
		//topScoreButton.draw();		
	}

	/** 
	 * Timer Handling
	 */
	public void startFruitTimer() {
		if( PacManGame.gameType == 1 )
		{
			startTimer("removeFruit", Fruit.SHOW_FRUIT_DURATION);
		}
	}
	
	/**
	 * Pac-Man death timer
	 */
	public void pacManDeathTimer(){
		stopTimer("pacManDeath");
		control.pacManRevive();
	}

	/**
	 * Stops Fruit timer
	 */
	public void removeFruitTimer() {
		if( PacManGame.gameType == 1 )
		{
			stopTimer("removeFruit");
			control.hideFruit();
		}
	}

	/**
	 * Starts ghost scatter timer
	 */
	public void startScatterTimer() {
		restartTimer("unScatterGhosts", GhostController.SCATTERSECONDS);
	}
	
	public void startInitialWaitTimer(){
		restartTimer("initialWait", 5000);
	}
	
	public void initialWaitTimer(){
		stopTimer("initialWait");
		control.initialWaitOver();
	}

	/**
	 * Stops ghost scatter timer
	 */
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
			showMenuScreen();
		}
	}

	/**
	 * 
	 * starts a single-player game.
	 * 
	 */
	public void onClickSinglePlay() {
		if (isShowingScene(ScreenMode.MENU)) {
			PacManGame.gameType = 1;
			System.out.println("single player click");
			startPacManServer();
			showGameScreen();
		}
	}

	/**
	 * 
	 * goes to the multi-player menu.
	 * 
	 */
	public void onClickMultiPlay() {
		if (isShowingScene(ScreenMode.MENU)) {
			System.out.println("load multiplayer screen..");
			showMultiGameScreen();
		}
	}
	
	/**
	 * 
	 * Host Pac-Man in multi-player game.
	 * 
	 */
	public void onClickPacManPlayer() {
		if (isShowingScene(ScreenMode.MULTIGAME)){
			PacManGame.gameType = 1;
			System.out.println("pac-man player game");
			startPacManServer();
			showGameScreen();
		}
	}
	
	/**
	 * 
	 * Joins ghost to multi-player game.
	 * 
	 */
	public void onClickGhostPlayer() {
		if (isShowingScene(ScreenMode.MULTIGAME)){
			PacManGame.gameType = 2;
			System.out.println("ghost player game");
			startPacManClient();
		}
	}

	
	/**
	 * 
	 * Shows top scores screen from MainMenu.
	 * 
	 */
	public void onClickTopScores() {
		if (isShowingScene(ScreenMode.MENU)) {
			System.out.println("topScore click");
			//mainMenu.stopMenuTheme();
			showScoresScreen();			
			// beginGame();
		}
	}

	/**
	 * returns back to MainMenu from Top Scores
	 */
	public void onClickTopScoresMainMenu(){
		if(isShowingScene(ScreenMode.SCORES))
		{
			mainMenu.stopMenuTheme();
			topScores.stopTopScoresTheme();
			showMenuScreen();
		}
	}//multiMenu.hideButtons();
	
	public void onClickCreditsFromTopScores(){
		if(isShowingScene(ScreenMode.SCORES))
		{
			mainMenu.stopMenuTheme();
			topScores.stopTopScoresTheme();
			topScores.hideButtonsAndScores();
			showCreditsScreen();
		}
	}

	public void onClickBackToMainFromCredits(){
		if(isShowingScene(ScreenMode.CREDITS))
		{
			mainMenu.stopMenuTheme();
			creditsScreen.stopCreditsTheme();			
			showMenuScreen();
		}
	}
	
	
	/**
	 * Returns player back to MainMenu from Multi-player
	 */
	public void onClickMultiPlayerBackToMainMenu(){
		if(isShowingScene(ScreenMode.MULTIGAME))
		{
			mainMenu.stopMenuTheme();
			multiMenu.hideMultiPlayerMenuButtons();
			showMenuScreen();
		}
	}

	/**
	 * 
	 * Quits the game.
	 * 
	 */
	public void onClickQuit() {
		if (isShowingScene(ScreenMode.MENU)) {

			if( PacManGame.gameType == 1 )
			{
				// server
				Server.send(PType.GAMEOVER);
			}
			else
			{
				Client.send(PType.LEAVE);
			}

			System.out.println("quit click");
			System.exit(0);
			// beginGame();
		}
	}

	/**
	 * Allows the user to skip the intro by holding down "S" or the Spacebar 
	 *
	 */
	public void onKeyPressIntro() {
		if (keyboard.isDown(keyboard.S) && isShowingScene(ScreenMode.INTRO)) {
			showMenuScreen();
		}
		if (keyboard.isDown(keyboard.SPACE) && isShowingScene(ScreenMode.INTRO)) {
			showMenuScreen();
		}
	}
	
	/**
	 *	 
	 * Keyboard listener that allows the user to type their name on the high scores screen.
	 *  
	 */
	public void onKeyPressScores(){
		if(isShowingScene(ScreenMode.SCORES))
		{
			try
			{
				if(keyboard.isDown(keyboard.BACKSPACE))
				{
					topScores.removeFromName();
				}
				else
				{
					topScores.addToName(keyboard.getKeyChar());
				}
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
				System.err.println("Typing Name into Top Scores thread interrupted!");
			}
		}
	}

	// This kept messing up the Start Game button, since it would start the game before
	// the button was pressed.   If you want to skip the intro, just press and hold "s" or the 
	// spacebar to get to the menu.  If you still want to click to get to the menu, 
	// click the area where the start game button would be
	//public void onMousePressed() {
		//if (isShowingScene(ScreenMode.INTRO)) {
		//	showMenuScreen();
		//}
	//}

	/**
	 * 
	 */
	public void onKeyPressGame() {
		// // Arrow keys and WASD keys move the paddle
		if( PacManGame.gameType == 1 )
		{
			if (keyboard.isDown(keyboard.UP, keyboard.W))
			{
				Server.send(Direction.UP);
				control.setPacManDirection(Direction.UP);
			}
			else if (keyboard.isDown(keyboard.DOWN, keyboard.S))
			{
				Server.send(Direction.DOWN);
				control.setPacManDirection(Direction.DOWN);
			}
			else if (keyboard.isDown(keyboard.LEFT, keyboard.A))
			{
				Server.send(Direction.LEFT);
				control.setPacManDirection(Direction.LEFT);
			}
			else if (keyboard.isDown(keyboard.RIGHT, keyboard.D))
			{
				Server.send(Direction.RIGHT);
				control.setPacManDirection(Direction.RIGHT);
			}
		}
		else
		{
			// FOR MUTLIPLAYER
			String gname = capitalize(Client.getGhostType().name());

			if (keyboard.isDown(keyboard.UP, keyboard.W))
			{
				Client.send(Direction.UP);
				GameState.getInstance().getGhosts().getObjectAt(gname).setDirection(Direction.UP);
			}
			else if (keyboard.isDown(keyboard.DOWN, keyboard.S))
			{
				Client.send(Direction.DOWN);
				GameState.getInstance().getGhosts().getObjectAt(gname).setDirection(Direction.DOWN);
			}
			else if (keyboard.isDown(keyboard.LEFT, keyboard.A))
			{
				Client.send(Direction.LEFT);
				GameState.getInstance().getGhosts().getObjectAt(gname).setDirection(Direction.LEFT);
			}
			else if (keyboard.isDown(keyboard.RIGHT, keyboard.D))
			{
				Client.send(Direction.RIGHT);
				GameState.getInstance().getGhosts().getObjectAt(gname).setDirection(Direction.RIGHT);
			}

		}

		//TODO i like being able to restart during the game for testing REMOVE this later
		if (keyboard.isDown(keyboard.R) && isShowingScene(ScreenMode.GAME)) {
			showGameScreen();
		}
		
	}

	/**
	 * Keyboard listener.  If the player presses R, restarts the game.
	 * If the player presses T, goes to top scores screen.
	 */
	public void onKeyPressGameover() {
		if (keyboard.isDown(keyboard.R) && isShowingScene(ScreenMode.GAMEOVER)) 
		{
			showGameScreen();
		}
		
		else if (keyboard.isDown(keyboard.T) && isShowingScene(ScreenMode.GAMEOVER)) 
		{
			//System.out.println("Pressed T");
			showScoresScreen();
			//showMenuScreen();
		}
	}

	/**
	 * @param filename
	 * Makes a Sprite object from the image file specified by the filename passed in
	 * @return Sprite
	 */
	public Sprite makeSpriteFromPath(String filename) {
		return makeSprite(getImage(filename));
	}

	/**
	 * Displays the specified scene onto the window, while also storing the
	 * scene for later reference
	 * 
	 * @param scene
	 *            the scene to display
	 */
	public void showScene(ScreenMode scene) {
		this.currentScene = scene;
		String sceneName = capitalize(scene.toString().toLowerCase());
		super.startScene(sceneName);
	}

	/**
	 * Determines if the scene currently displayed is the one specified.
	 * 
	 * @param scene
	 *            the scene to check
	 * @return true if the scene specified is currently displayed; false
	 *         otherwise.
	 */
	public boolean isShowingScene(ScreenMode scene) {
		return currentScene.equals(scene);
	}

	private String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
