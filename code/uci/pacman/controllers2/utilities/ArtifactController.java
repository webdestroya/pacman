package code.uci.pacman.controllers2.utilities;

import java.awt.Point;
import java.util.Collection;

import code.uci.pacman.objects.StationaryObject;
import ucigame.Image;

public abstract class ArtifactController<A extends StationaryObject> extends SpriteController<Point, A> {
	private static final long serialVersionUID = 1L;
	protected Image artifactImage;
	
	public void addObject(int x, int y, A object) {
		super.addObject(new Point(x, y), object);
	}
	
	public A getObjectAt(int x, int y) {
		return super.getObjectAt(new Point(x, y));
	}
	
	public void destroyAt(int x, int y) {
	    super.destroyAt(new Point(x, y));
	}
	
	public abstract void constructArtifacts();
	
	
	public Collection<A> getObjects() {
		return super.getObjects();
	}
}
