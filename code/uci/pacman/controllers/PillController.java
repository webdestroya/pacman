package code.uci.pacman.controllers;

import code.uci.pacman.controllers.utilities.ArtifactController;
import code.uci.pacman.objects.controllable.PacMan;
import code.uci.pacman.objects.stationary.*;

/**
 * 
 * @author The Game Team responsible for and controls pill locations and actions
 */
public class PillController extends ArtifactController<Pill> {
	private int totalPills;

	public PillController() {
		totalPills = 0;
		this.constructArtifacts("pill");
	}

	public Pill getCollidingPill(PacMan p) {
		return super.getCollidedWith(p);
	}

	public void addArtifact(int x, int y) {
		super.addObject(x, y, new Pill(x, y));
		totalPills++;
	}

	public Pill getPillAt(int x, int y) {
		return super.getObjectAt(x, y);
	}

	public int getInitialCount() {
		return totalPills;
	}

	public int getPillCount() {
		return super.getObjects().size();
	}

}
