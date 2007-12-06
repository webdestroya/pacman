package code.uci.pacman.multiplayer;

/**
 * This is the packet type, used only for networking
 * @author Networking Team
 */
public enum PType 
{ 
	JOIN,		//GHOST			(duh - GHOST_TYPE tells the type joining)
	
	PPOS,		//XPOS, YPOS				(used as consistency for pacman)
	GPOS,		//GHOST,XPOS,YPOS	(used as a consistency to make sure everyone is in the right spot)

	GMOVE, 		//DIR, GHOST		(tells the direction and what ghost to move)
	PMOVE, 		//DIR					( direction of pacman)
	
	GAMEOVER, 	//						(duh)
	LEAVE, 		//GHOST			(tells the server to drop the ghost)
	
	SCORE,		//SCORE				(updates the clients with a score - not yet used)
	
	SPOTFREE,	//GHOST			(tells the clients listening what spots are free on the server)
	
	
	AFRUIT,		//XPOS,YPOS		places a fruit on screen
	DFRUIT,		//XPOS,YPOS		removes a fruit
	
	LIVES,		//

	PILLA,		//XPOS,YPOX		adds a pill to the screen
	PILLD,		//XPOS,YPOS		deletes a pill
	PPILLA,		//XPOS,YPOS		adds a power pill
	PPILLD,		//XPOS,YPOS		deletes a power pill

	LEVEL,		//LEVEL			sets the current level

	HEARTBEAT,	//GHOST			sends the server a heartbeat signal

	DGHOST,		//				tells the server to show all ghosts as dead
	AGHOST,		//				reset ghosts as alive

};


