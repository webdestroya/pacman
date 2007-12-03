package code.uci.pacman.controllers;

import java.util.ArrayList;

import code.uci.pacman.game.GameState;
import code.uci.pacman.objects.controllable.*;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * @author The Game Team responsible for and controls wall locations
 */
public class WallController {
	ArrayList<Wall> walls;

	public WallController() {
		walls = new ArrayList<Wall>();
		// this.constructArtifacts("wall");
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

	private void makeWalls1() {
		addWall(0, 0, 8, 600);// left
		addWall(0, 0, 600, 8);// top
		addWall(0, 580, 600, 8);// bottom
		addWall(592, 0, 8, 600);// right
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
		addWall(222, 238, 157, 75);
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
		walls.add(new Wall(x, y, width, height));
	}

	public void stopCollision(PacMan p) {
		for (Wall w : walls) {
			p.stopIfCollidesWith(w);
		}
	}
	
	public boolean collidesWith(Ghost g) {
		for (Wall w : walls) {
			g.stopIfCollidesWith(w);
			if (g.collided())
				return true;
		}
		return false;
	}

	public void drawObjects() {
		for (Wall w : walls) {
			w.draw();
		}

	}
}
