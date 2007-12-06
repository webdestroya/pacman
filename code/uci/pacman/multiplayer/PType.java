package code.uci.pacman.multiplayer;

/**
 * This is the packet type, used only for networking
 */
public enum PType 
{ 
	JOIN,		//1 GHOST_TYPE			(duh - GHOST_TYPE tells the type joining)
	PPOS,		//2 XPOS YPOS				(used as consistency for pacman)
	GMOVE, 		//3 DIR, GHOST_TYPE		(tells the direction and what ghost to move)
	PMOVE, 		//4 DIR					( direction of pacman)
	GAMEOVER, 	//5 						(duh)
	LEAVE, 		//6 GHOST_TYPE			(tells the server to drop the ghost)
	SCORE,		//7 SCORE				(updates the clients with a score - not yet used)
	SPOTFREE,	//8 GHOST_TYPE			(tells the clients listening what spots are free on the server)
	GPOS,		//9 GHOST_TYPE XPOS YPOS	(used as a consistency to make sure everyone is in the right spot)
};


