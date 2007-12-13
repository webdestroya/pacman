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
			makeWalls3();
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
		addWall(0,0,24,606);
		addWall(24,0,554,10);
		addWall(578,0,22,606);
		addWall(231,10,23,58);
		addWall(348,10,23,58);
		addWall(59,45,22,52);
		addWall(81,45,116,23);
		addWall(405,45,138,23);
		addWall(289,47,24,78);
		addWall(521,68,22,29);
		addWall(115,103,24,51);
		addWall(174,103,23,51);
		addWall(197,103,57,22);
		addWall(348,103,80,23);
		addWall(463,103,24,28);
		addWall(405,126,23,28);
		addWall(463,130,115,24);
		addWall(24,130,91,24);
		addWall(230,160,142,23);
		addWall(59,189,22,80);
		addWall(116,189,81,23);
		addWall(405,189,81,23);
		addWall(521,189,22,80);
		addWall(231,218,23,109);
		addWall(254,218,94,22);
		addWall(348,218,23,109);
		addWall(81,247,58,22);
		addWall(174,247,23,57);
		addWall(405,247,23,80);
		addWall(463,247,58,22);
		addWall(59,304,22,81);
		addWall(117,304,80,23);
		addWall(254,304,94,23);
		addWall(428,304,56,23);
		addWall(521,304,22,81);
		addWall(81,362,57,23);
		addWall(174,362,23,80);
		addWall(197,362,56,23);
		addWall(289,362,24,57);
		addWall(349,362,79,23);
		addWall(464,362,57,23);
		addWall(405,385,23,57);
		addWall(24,419,57,23);
		addWall(116,419,22,81);
		addWall(235,419,132,23);
		addWall(463,419,23,81);
		addWall(521,419,57,24);
		addWall(59,477,57,23);
		addWall(174,477,23,115);
		addWall(197,477,57,23);
		addWall(289,477,24,58);
		addWall(348,477,80,23);
		addWall(486,477,57,23);
		addWall(405,500,23,92);
		addWall(59,535,79,22);
		addWall(234,535,134,22);
		addWall(464,535,78,22);
		addWall(24,592,554,14);

	}
	
	private void makeWalls3(){
		addWall(0,0,24,606);
		addWall(24,0,555,8);
		addWall(579,0,21,606);
		addWall(203,8,22,116);
		addWall(378,8,22,116);
		addWall(59,44,109,22);
		addWall(259,44,24,22);
		addWall(321,44,23,22);
		addWall(435,44,109,22);
		addWall(59,101,22,82);
		addWall(81,101,87,23);
		addWall(259,101,85,23);
		addWall(435,101,109,23);
		addWall(522,124,22,58);
		addWall(114,159,83,24);
		addWall(231,159,52,51);
		addWall(320,159,52,51);
		addWall(406,159,23,195);
		addWall(429,159,59,23);
		addWall(174,183,23,171);
		addWall(24,216,27,23);
		addWall(88,216,51,23);
		addWall(464,216,23,52);
		addWall(487,216,28,23);
		addWall(552,216,27,23);
		addWall(116,239,23,29);
		addWall(231,245,141,23);
		addWall(231,268,23,86);
		addWall(349,268,23,64);
		addWall(59,275,22,22);
		addWall(522,275,22,22);
		addWall(116,303,23,79);
		addWall(464,303,23,79);
		addWall(59,332,57,22);
		addWall(254,332,118,22);
		addWall(487,332,57,22);
		addWall(24,389,57,23);
		addWall(174,389,51,51);
		addWall(260,389,23,53);
		addWall(283,389,37,23);
		addWall(320,389,23,53);
		addWall(378,389,22,87);
		addWall(400,389,29,52);
		addWall(522,389,22,53);
		addWall(544,389,35,23);
		addWall(59,412,22,30);
		addWall(116,418,23,24);
		addWall(464,418,23,23);
		addWall(203,442,22,56);
		addWall(59,476,22,22);
		addWall(116,476,51,22);
		addWall(225,476,175,22);
		addWall(436,476,51,22);
		addWall(522,476,22,22);
		addWall(116,498,23,59);
		addWall(289,498,25,59);
		addWall(464,498,23,59);
		addWall(24,532,57,25);
		addWall(174,532,78,25);
		addWall(351,532,77,25);
		addWall(522,533,56,24);
		addWall(24,592,555,14);

	}

	private void addWall(int x, int y, int width, int height) {
		super.addObject(new Point(x, y), new Wall(curLevel,x, y, width, height));
	}
}
