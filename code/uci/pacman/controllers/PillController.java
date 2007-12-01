package code.uci.pacman.controllers;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.*;


/**
 * 
 * @author The Game Team
 * responsible for and controls pill locations and actions
 */
public class PillController extends ArtifactController<Pill> {
	private int totalPills;
	public PillController() {
		totalPills = 0;
	   this.constructArtifacts();
    }
	
	public Pill getCollidingPill(PacMan p){
		return super.getCollidedWith(p);
	}
	
	private void addPill(int x, int y) {
		super.addObject(x, y, new Pill(x, y));
		totalPills++;
	}
	
	public Pill getPillAt(int x, int y) {
		return super.getObjectAt(x, y);
	}

	@Override
	public void constructArtifacts() {
		switch(state.getLevel()){
		case 1:
			level1();
			break;
		case 2:
			level2();
			break;
		case 3:
			level3();
			break;
		}
	}

	private void level3() {
		addPill(300, 320); //TODO fill in pills here
		addPill(300,200);
		addPill(300,240);
		addPill(300,280);
		addPill(300,360);
		addPill(300,400);
		
	}

	private void level2() {
		addPill(200, 320); //TODO fill in pills here
		addPill(200,200);
		addPill(200,240);
		addPill(200,280);
		addPill(200,360);
		addPill(200,400);
		
	}

	private void level1() {
		addPill(100, 320); //TODO fill in pills here
		addPill(100,200);
		addPill(100,240);
		addPill(100,280);
		addPill(100,360);
		addPill(100,400);
	}

	public int getInitialCount() {
		return totalPills;
	}
	
	public int getPillCount(){
		return super.getObjects().size();
	}
	
	
}
