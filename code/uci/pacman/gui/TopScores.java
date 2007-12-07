package code.uci.pacman.gui;
import ucigame.*;
import java.util.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import code.uci.pacman.controllers.GameController;
import code.uci.pacman.controllers.SoundController;
import code.uci.pacman.gui.MainMenu;
import code.uci.pacman.game.GameState;
import code.uci.pacman.game.PacManGame;

/**
 * 
 * This will keep track of top 10 stores and names
 * @author GUI Team (Rick, MikeY, Cameron)
 *
 */
public class TopScores{

	private Sprite topScoresMainMenu;
	private ArrayList<String> nameList = new ArrayList<String>();
	private ArrayList<Integer> scoreList = new ArrayList<Integer>();
	private String name;
	private int score;
	private int addIndex;
	private int numberOfScores = 10;
	private Sprite backGround;
	private int bgX = 0;
	private int bgY = 0;
	private int nameStartPosX = 175;
	private int nameStartPosY = 150;
	private int scoreStartPosX = 375;
	private int scoreStartPosY = 150;
	private int spaceIncrement = 30;
	FileReader inFile;
	FileWriter outFile;
	Scanner scanner;
	private int menuButtonX = 175;
	private int menuButtonY = 450;
	
	/**
	 * 
	 * Constructor for TopScores.  This will load from a file top 10 players and their corresponding scores.
	 *
	 */
	public TopScores(){
		// TODO Auto-generated constructor stub
		topScoresMainMenu = GameController.getInstance().getPacInstance().makeButton("TopScoresMainMenu",GameController.getInstance().getPacInstance().getImage("mainmenubutton.png"),
                249, 76);
		topScoresMainMenu.position(menuButtonX, menuButtonY);
		
		readScores();
		backGround = GameController.getInstance().getPacInstance().makeSpriteFromPath("scorespriteadjuster.png");
		backGround.position(bgX, bgY);
		backGround.font(PacManGame.font, PacManGame.BOLD, 24, 255, 255, 255);
		if(isTopList()){
			addTopScore();
			writeScores();
		}
	}
	
	private void readScores(){
		try
		{
			inFile = new FileReader("code\\uci\\pacman\\gui\\scores.txt");
			scanner = new Scanner(inFile);
			while(scanner.hasNextLine())
			{
				nameList.add(scanner.next());
				scoreList.add(Integer.parseInt(scanner.next()));
			}
			inFile.close();
		}
		catch(IOException e)
		{
			System.err.println("No such file.");
			e.printStackTrace();
			//for(int x = 0; x < numberOfScores; x++)
			//{
			//	nameList.add("Name"+(x+1));
			//	scoreList.add(10-x);
			//}
		}
	}
	
	private void writeScores(){
		try
		{
			outFile = new FileWriter("code\\uci\\pacman\\gui\\scores.txt");
			for(int x = 0; x < numberOfScores; x++){
				outFile.write(nameList.get(x)+" ");
				outFile.write(scoreList.get(x)+"");
				if(x!=9)
					outFile.write("\n");
			}
			outFile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}			
	}
	
	/**
	 * 
	 * Displays the top score screen.
	 * 
	 */
	public void draw(){
		//turn off pellet, ghost, and ambient sound
		//SoundController.stopAllSounds();
		
		//stop MainMenu music
		//MainMenu.stopMenuTheme();
		
		//put text on screen from arrayLists
		for(int x = 0; x < numberOfScores; x++){
			backGround.putText(nameList.get(x)+"", nameStartPosX, nameStartPosY + x*spaceIncrement);
			backGround.putText(scoreList.get(x)+"", scoreStartPosX, scoreStartPosY + x*spaceIncrement);
		}
		backGround.draw();
		topScoresMainMenu.draw();
	}
	
	/**
	 * 
	 * 
	 * Retrieves player's score from GameState,
	 * and checks it against scoreList to see if it qualifies
	 * to be on the top score list.
	 * 
	 * Returns true if score qualifies to be on the list,
	 * false otherwise.
	 * 
	 */
	public boolean isTopList(){
		score = GameState.getInstance().getScore();
		for(int x = 0; x < numberOfScores; x++){
			if(score >= scoreList.get(x)){
				addIndex = x;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Retrieves player's score from GameState, and
	 * adds the score to the scoreList at the proper place.
	 * the method will also request the user for his/her name
	 * to be put into the nameList.
	 * 
	 * scoreList stores a limit of 10 scores, therefore when 1 is
	 * added the last one will be removed
	 * 
	 */
	public void addTopScore(){
		name = nameRequest();
		nameList.add(addIndex, name);
		nameList.remove(10);
		scoreList.add(addIndex, score);
		scoreList.remove(10);
		GameState.getInstance().addToScore(score*-1);
	}
	
	private String nameRequest(){
		return "you";
		//return new Scanner(System.in).next();
	}
}
