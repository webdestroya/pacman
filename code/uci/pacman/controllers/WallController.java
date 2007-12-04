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

	public WallController() {
		switch (GameState.getInstance().getLevel()) {
		case 1:
			makeWalls1();
			break;
		case 2:
			makeWalls1();
			break;
		case 3:
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
	 * @param the object to stop from colliding with walls
	 */
	public void stopCollisions(ControllableObject c) {
		c.stopIfCollidesWith(super.getObjects());
	}
	
	/* Private Methods */
	
	private void makeWalls1() {
		addWall(0, 0, 8, 600);// left
		addWall(0, 0, 600, 8);// top
		addWall(0, 580, 600, 8);// bottom
		addWall(592, 0, 8, 600);// right

		addWall(TOP_CAGEPOS_X, TOP_CAGEPOS_Y, 157, 10); // top of the cage
		addWall(222, 245, 157, 75); // rest of the cage

		addWall(0, 177, 118, 195);
		addWall(46, 46, 72, 38);
		addWall(157, 46, 95, 38);
		addWall(288, 7, 25, 77);
		addWall(354, 46, 90, 38);
		addWall(485, 46, 70, 38);
		addWall(46, 122, 72, 21);
		addWall(157, 122, 27, 133);
		addWall(174, 180, 73, 20);
		addWall(220, 122, 159, 21);
		addWall(287, 136, 29, 64);
		addWall(354, 180, 73, 20);
		addWall(417, 122, 27, 135);
		addWall(485, 122, 70, 20);
		addWall(485, 178, 114, 194);

		addWall(157, 295, 27, 77);
		addWall(417, 295, 27, 77);
		addWall(222, 354, 157, 18);
		addWall(284, 367, 30, 61);
		addWall(157, 409, 90, 19);
		addWall(352, 411, 92, 17);
		addWall(47, 526, 200, 18);
		addWall(157, 469, 27, 60);
		addWall(222, 469, 157, 17);
		addWall(285, 481, 30, 63);
		addWall(353, 526, 200, 18);
		addWall(417, 469, 27, 60);
		addWall(48, 409, 70, 20);
		addWall(89, 426, 29, 60);
		addWall(485, 427, 29, 59);
		addWall(485, 411, 68, 20);
		addWall(3, 466, 53, 20);
		addWall(549, 466, 45, 20);

	}

	private void addWall(int x, int y, int width, int height) {
		super.addObject(new Point(x, y), new Wall(x, y, width, height));
	}
}
