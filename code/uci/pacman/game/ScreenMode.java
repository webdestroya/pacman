package code.uci.pacman.game;

/**
 * 
 * This enumeration represents all the possible screens that exist
 * within the game including the intro, the menu, the game itself,the multi-player menu, scores, credits
 * and the game over screen.
 * 
 * @author Game Team
 *
 */
public enum ScreenMode {
	INTRO,			// Introduction vid sequence
	MENU,			// main menu on the screen
	GAME,			// the actual game
	MULTIGAME,		// multiplayer selection screen
	SCORES,			// top scores
	GAMEOVER,		// game over
	MPWAITING,		// multiplayer waiting to connect
	CREDITS,			// the game credits screen
	
}
