package code.uci.pacman.controllers;

import java.util.ArrayList;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.*;


/**
 * 
 * @author The Game Team
 * responsible for and controls wall locations
 */
public class WallController {
	ArrayList<Wall> walls;
	public WallController() {
		walls = new ArrayList<Wall>();
	   //this.constructArtifacts("wall");
		makeWalls();
    }
	
	private void makeWalls() {
		addWall(0,0,8,600);//left
		addWall(0,0,600,8);//top
		addWall(0,580,600,8);//bottom
		addWall(592,0,8,600);//right
		addWall(0,177,114,195);
		addWall(46,45,72,41);
		addWall(157,50,95,38);
		addWall(285,7,31,81);
		addWall(354,48,92,38);
		addWall(482, 49, 74, 38);
		addWall(46, 122, 72, 21);
		addWall(155, 124, 30, 133);
		addWall(174, 180, 76, 20);
		addWall(220, 122, 159, 21);
		addWall(285, 136, 31, 66);
		addWall(354, 180, 73, 22);
		addWall(417, 124, 31, 135);
		addWall(484, 124, 72, 20);
		addWall(483, 178, 117, 195);
		addWall(221, 238, 159, 76);
		addWall(155, 295, 29, 77);
		addWall(416, 296, 29, 77);
		addWall(222, 350, 157, 23);
		addWall(284, 367, 32, 64);
		addWall(155, 409, 92, 18);
		addWall(352, 410, 92, 18);
		addWall(47, 526, 200, 20);
		addWall(156,469,30,60);
		addWall(222, 466, 157, 20);
		addWall(285, 481, 30, 63);
		addWall(353, 526, 200, 20);
		addWall(417, 468, 30, 60);
		addWall(48, 409, 70, 20);
		addWall(89, 426, 29, 61);
		addWall(485, 427, 29, 61);
		addWall(483, 411, 70, 20);
		addWall(3, 466, 53, 20);
		addWall(549, 466, 45, 20);
		
		
	}
	
	private void addWall(int x, int y, int width, int height){
		walls.add(new Wall(x,y,width,height));
	}

	
	public void stopCollision(PacMan p){
		for(Wall w :walls){
			p.stopIfCollidesWith(w);
		}
	}

	public void drawObjects() {
		for(Wall w: walls){
			w.draw();
		}
		
	}
}
