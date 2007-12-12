package code.uci.pacman.controllers;

import java.awt.Point;
import java.util.Collection;

import ucigame.Sprite;

import code.uci.pacman.controllers.utilities.SpriteController;
import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.*;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * This controller is responsible for all the walls located within the game
 * world. Intended to abstract and control all wall-related actions within the
 * game such as constructing walls for a stage, drawing walls, checking wall
 * collisions and stopping collisions.
 * 
 * @author The Game Team
 */
public class WallController extends SpriteController<Point, Wall> {
	private static int TOP_CAGEPOS_X = 222;
	private static int TOP_CAGEPOS_Y = 235;
	
	private int curLevel = 1;

	public WallController() {
		switch (GameState.getInstance().getLevel()) {
		case 1:
			curLevel = 1;
			makeWalls1();
			break;
		case 2:
			curLevel = 2;
			makeWalls2();
			break;
		case 3:
			curLevel = 3;
			makeWalls1();
			break;
		}
	}


	/**
	 * 
	 * To check for a possible immediate future collision with a wall, we move
	 * the PacMan or Ghost object xCheck/yCheck pixels from where it is and then
	 * check for a collision before proceeding.
	 * 
	 * @param pac the PacMan instance
	 * @param xCheck the x offset from current position
	 * @param yCheck the y offset from current position
	 * @return true if a wall will collide at that position; false otherwise.
	 */
	public boolean willCollideAtPos(ControllableObject pac, int xCheck, int yCheck) {

		double curX = pac.x() + pac.xspeed();
		double curY = pac.y() + pac.yspeed();
		pac.nextX(pac.x() + xCheck);
		pac.nextY(pac.y() + yCheck);
		Sprite[] spriteWalls = super.getObjects().toArray(new Sprite[0]);
		pac.checkIfCollidesWith(spriteWalls);
		boolean r = pac.collided();
		pac.nextX(curX);
		pac.nextY(curY);
		if (r)
			return false;
		else
			return true;
	}

	/**
	 * 
	 * Commands all objects within the collection to no longer be able to
	 * collide with the walls in the game. This method is used to
	 * instruct the ghosts not to collide with the walls and to abide by
	 * them as boundaries for movement.
	 * 
	 * @param objects the collection of objects to stop from colliding
	 */
	public void stopCollisions(Collection<? extends ControllableObject> objects) {
		for (ControllableObject c : objects) {
			this.stopCollisions(c);
		}
	}

	/**
	 * 
	 * Commands the specified game object to no longer be able to
	 * collide with the walls in the game. This method is used to
	 * instruct PacMan not to collide with the walls and to abide by
	 * them as boundaries for movement.
	 * 
	 * @param c the object to stop from colliding with walls
	 */
	public void stopCollisions(ControllableObject c) {
		c.stopIfCollidesWith(super.getObjects().toArray(new Sprite[0]));
	}
	
	/* Private Methods */
	
	private void makeWalls1() {
	
		addWall(0,0,18,596);
		addWall(18,0,582,17);
		addWall(18,17,266,1);
		addWall(284,17,33,71);
		addWall(317,17,283,1);
		addWall(581,18,19,578);
		addWall(45,47,73,39);
		addWall(154,49,96,39);
		addWall(350,49,97,39);
		addWall(481,49,74,39);
		addWall(46,124,72,23);
		addWall(154,124,31,136);
		addWall(220,124,160,23);
		addWall(414,124,33,136);
		addWall(481,124,73,23);
		addWall(284,147,33,56);
		addWall(481,176,100,205);
		addWall(18,178,100,203);
		addWall(185,181,65,22);
		addWall(350,181,64,22);
		addWall(220,239,160,10);
		addWall(220,249,11,75);
		addWall(370,249,10,59);
		addWall(154,294,31,87);
		addWall(414,294,33,87);
		addWall(231,308,149,16);
		addWall(220,353,160,28);
		addWall(284,381,33,53);
		addWall(45,411,73,23);
		addWall(154,411,96,23);
		addWall(350,411,97,23);
		addWall(481,411,30,79);
		addWall(511,411,44,23);
		addWall(88,434,30,56);
		addWall(18,467,38,23);
		addWall(154,467,31,57);
		addWall(220,467,160,23);
		addWall(414,467,33,57);
		addWall(544,467,37,23);
		addWall(284,490,33,58);
		addWall(45,524,205,24);
		addWall(350,524,205,24);
		addWall(18,574,563,22);
	}
	
	private void makeWalls2()
	{
		addWall(0,0,24,603);
		addWall(24,0,548,11);
		addWall(572,0,28,603);
		addWall(225,11,29,58);
		addWall(342,11,29,58);
		addWall(53,40,28,58);
		addWall(81,40,116,29);
		addWall(399,40,116,29);
		addWall(515,40,28,58);
		addWall(283,42,30,84);
		addWall(109,98,30,57);
		addWall(168,98,29,57);
		addWall(197,98,57,28);
		addWall(342,98,57,28);
		addWall(399,98,29,57);
		addWall(457,98,30,57);
		addWall(24,125,85,30);
		addWall(487,125,85,30);
		addWall(225,155,146,29);
		addWall(53,184,28,86);
		addWall(109,184,88,28);
		addWall(399,184,88,28);
		addWall(515,184,28,86);
		addWall(225,212,146,30);
		addWall(81,242,58,28);
		addWall(168,242,29,86);
		addWall(225,242,29,86);
		addWall(342,242,29,86);
		addWall(399,242,29,86);
		addWall(457,242,58,28);
		addWall(112,299,56,29);
		addWall(254,299,88,29);
		addWall(428,299,56,29);
		addWall(515,299,28,87);
		addWall(53,300,28,86);
		addWall(81,357,58,29);
		addWall(168,357,29,86);
		addWall(197,357,57,29);
		addWall(283,357,30,58);
		addWall(342,357,86,29);
		addWall(457,357,58,29);
		addWall(399,386,29,57);
		addWall(24,412,57,31);
		addWall(109,412,30,60);
		addWall(457,412,30,89);
		addWall(515,412,57,31);
		addWall(229,415,138,28);
		addWall(53,472,86,29);
		addWall(168,472,29,115);
		addWall(197,472,57,29);
		addWall(283,472,30,58);
		addWall(342,472,57,29);
		addWall(399,472,29,115);
		addWall(487,472,56,29);
		addWall(53,530,86,28);
		addWall(229,530,138,28);
		addWall(457,530,86,28);
		addWall(24,587,548,16);
	}

	private void addWall(int x, int y, int width, int height) {
		super.addObject(new Point(x, y), new Wall(curLevel,x, y, width, height));
	}
}
