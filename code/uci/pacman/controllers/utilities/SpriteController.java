package code.uci.pacman.controllers.utilities;

import java.util.*;
import java.util.concurrent.*;

import code.uci.pacman.game.GameState;

import ucigame.Sprite;

/**
 * 
 * This class is the parent abstraction for all the controllers within this game. This 
 * controller should be extended by any other class which is responsible for handling
 * the behavior and actions of a group of game objects.
 * 
 * This class gives the controller certain functionality such as functions for
 * adding and removing game objects as well as drawing the objects onto the 
 * game world.
 * 
 * @author Game Team
 *
 * @param <T> the key used to store these sprites in a hash
 * @param <S> the type of sprite object to store
 * 
 */
public abstract class SpriteController<T, S extends Sprite> {
	private static final long serialVersionUID = 1L;
	protected static GameState state = GameState.getInstance(); 
	//private HashMap<T, S> hash;
	private ConcurrentHashMap<T, S> hash;

	public SpriteController() {
       //hash = new HashMap<T, S>();
       hash = new ConcurrentHashMap<T, S>();
	}
	
	/**
	 * Add a sprite to the collection
	 * 
	 * @param key the key
	 * @param object the sprite to add
	 */
	protected void addObject(T key, S object) {
		hash.put(key, object);
	}
	
	/**
	 * Get a sprite based on the specified key
	 * 
	 * @param key the key for the object
	 * @return the sprite associated with the key
	 */
	public S getObjectAt(T key) {
		return hash.get(key);
	}
	
	/**
	 * Destroys a sprite and removes it from the collection.
	 * 
	 * @param key the key for the sprite to remove.
	 */
	protected void destroyAt(T key) {
		if( hash.containsKey(key) )
		{
			hash.remove(key);
		}
	}
	
	
	/**
	 * Draws each sprite in this collection onto the screen. Called from
	 * other draw methods or the main draw method for game within PacManGame.
	 */
	public void drawObjects() {
		for (Sprite s : this.getObjects())
		{
			s.draw();
		}
	}
	
	/**
	 * Gets all the objects stored within this collection.
	 * 
	 * @return the collection of sprites controlled by this instance.
	 */
	public Collection<S> getObjects() {
		return hash.values();
	}
}
