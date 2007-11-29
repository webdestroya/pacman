package code.uci.pacman.controllers.utilities;

import java.util.Collection;
import java.util.HashMap;

import ucigame.Sprite;

//controller for sprites is basically an easy api for keeping track 
// of multiple sprites which are grouped in some way
public abstract class SpriteController<T, S extends Sprite> {
	private static final long serialVersionUID = 1L;
	private HashMap<T, S> hash;

	public SpriteController() {
       hash = new HashMap<T, S>();
	}
	
	protected void addObject(T key, S object) {
		hash.put(key, object);
	}
	
	protected S getObjectAt(T key) {
		return hash.get(key);
	}
	
	protected void destroyAt(T key) {
		hash.remove(key);
	}
	
	protected void destroy(S object) {
		for (T key : hash.keySet()) {
			S value = hash.get(key);
			if (value.equals(object)) {
				this.destroyAt(key);
			}
		}
	}
	
	public void drawObjects() {
		for (Sprite s : this.getObjects()) {
			s.draw();
		}
	}
	
	protected Collection<S> getObjects() {
		return hash.values();
	}
}
