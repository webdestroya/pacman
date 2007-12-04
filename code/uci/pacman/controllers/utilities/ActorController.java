package code.uci.pacman.controllers.utilities;

import java.util.ArrayList;
import java.util.Collection;

import ucigame.Sprite;
import code.uci.pacman.objects.ControllableObject;

/**
 * 
 * This is the abstract controller that will be extended by any controllers which contain
 * collections of moving objects on the screen. Any group of similar moving objects should
 * be grouped and encapsulated into a controller that handles their actions. 
 * 
 * This controller extends from SpriteController which is used for any collection
 * of game objects. This adds additional functions to an actor such as the ability
 * to be constructed on the screen and functions for handling collisions with 
 * other objects.
 * 
 * @author Game Team
 * 
 * @param <T> the key type for these stored objects
 * @param <S> the type of object that will be controlled
 */
public abstract class ActorController<T, S extends ControllableObject> extends SpriteController<T, S> {
	protected abstract void constructActors();

	/**
	 * Gets a collection of all the actors that have collided with a specified object.
	 * Used to determine which ghosts are currently colliding with PacMan.
	 * 
	 * @param c the sprite to check collisions against. 
	 * @return a collection of all the objects colliding with the object specified.
	 */
	public Collection<S> getCollidedWith(Sprite c) {
		Collection<S> collidedObjects = new ArrayList<S>();
		for (S actor : super.getObjects()) {
			if (actor.collidedWith(c))
				collidedObjects.add(actor);
		}
		return collidedObjects;
	}
}
