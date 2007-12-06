package code.uci.pacman.multiplayer;

/**
 * This is the packet type, used only for networking
 */
public enum PType 
{ 
	JOIN,		//1 GHOST_TYPE			(duh - GHOST_TYPE tells the type joining)
	HEARTBEAT,	//2 GHOST_TYPE			(Tells the server that the player hasnt exploded) 
	GMOVE, 		//3 DIR, GHOST_TYPE		(tells the direction and what ghost to move)
	PMOVE, 		//4 DIR					( direction of pacman)
	GAMEOVER, 	//5 						(duh)
	LEAVE, 		//6 GHOST_TYPE			(tells the server to drop the ghost)
	SCORE,		//7 SCORE				(updates the clients with a score - not yet used)
	SPOTFREE,	//8 GHOST_TYPE			(tells the clients listening what spots are free on the server)
	NOTYET,		//9 ----not yet used---
};


