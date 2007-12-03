package code.uci.pacman.game;

import java.io.IOException;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.controllers.GhostController;
import code.uci.pacman.gui.ScoreBoard;
import code.uci.pacman.gui.TopScores;
import code.uci.pacman.objects.stationary.Fruit;
import ucigame.Sprite;
import ucigame.Ucigame;

import java.util.*;

import code.uci.pacman.multiplayer.*;
/**
 * 
 * @author The Game Team The main game class
 */
public class PacManGame extends Ucigame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -917916728311505169L;
	private GameState state;
	private ScoreBoard scoreBoard;
	private TopScores topScores;
	private GameController control;
	
	public static int multiplayerType = 1; // 1=server, 2=client
	public static String hostname	= "127.0.0.1"; // used for network
	

	public void setup() {
		generatePositions(false); 
		control = GameController.setInstance(this);
		state = GameState.getInstance();
		state.setupLevel();
		framerate(20);
		window.size(600, 650);
		canvas.background(0, 0, 0);
		window.title("Pac Man Fever");
		scoreBoard = new ScoreBoard();
		topScores = new TopScores();
		startScene("Game");
		
		// Make the server
		if(PacManGame.multiplayerType==1)
		{
			try
			{
				new Server().start();
			}
			catch(Exception e){}
		}
		else if(PacManGame.multiplayerType==2)
		{
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
	
	public void startFruitTimer(){
		startTimer("removeFruit",Fruit.ONSCREENLENGTH);
	}
	
	public void removeFruitTimer(){
		stopTimer("removeFruit");
		state.getFruit().hide();
	}
	
	public void startScatterTimer(){
		startTimer("unScatterGhosts",GhostController.SCATTERSECONDS);
	}
	
	public void unScatterGhostsTimer(){
		stopTimer("unScatterGhosts");
		state.getGhosts().unScatter();
	}
	
	
    public void drawMenu(){
		
	}

	public void drawScores(){
		canvas.clear();
		topScores.draw();
	}

	public void drawGame() {
		canvas.clear();
		control.nextMove();
		state.drawState();
		scoreBoard.draw();
	}

	public static void main(String[] args)
	{
		//System.out.println(args[0]+"|"+args[1]+"|"+args[2]);
		
		List cargs = Arrays.asList(args);
		
		if( cargs.contains("CLIENT") )
		{
			System.out.println("Starting Client");
			PacManGame.multiplayerType = 2;
			PacManGame.hostname = (String)cargs.get(2);
		}
		
		Ucigame.main(args);
		
		//System.out.println(args[2]);
		
	}
	
	
	public void drawGameOver() {
		canvas.clear();
		canvas.font("Tahoma", PacManGame.BOLD, 40, 255, 255, 255);
		canvas.putText("GAME OVER", 200, 300);
	}
	
	public void onKeyPressGame() {
		// // Arrow keys and WASD keys move the paddle
		if (keyboard.isDown(keyboard.UP, keyboard.W))
			state.getPacMan().step(Direction.UP);
		if (keyboard.isDown(keyboard.DOWN, keyboard.S))
			state.getPacMan().step(Direction.DOWN);
		if (keyboard.isDown(keyboard.LEFT, keyboard.A))
			state.getPacMan().step(Direction.LEFT);
		if (keyboard.isDown(keyboard.RIGHT, keyboard.D))
			state.getPacMan().step(Direction.RIGHT);
	}

	public PacManGame() {

	}

	public Sprite makeSpriteFromPath(String string) {
		return makeSprite(getImage(string));
	}

}
