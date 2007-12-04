package code.uci.pacman.controllers.utilities;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import code.uci.pacman.objects.ControllableObject;
import code.uci.pacman.objects.StationaryObject;

/**
 * 
 * This is the abstract controller that will be extended by any controllers
 * which contain collections of stationary objects on the screen. Any group of
 * similar stationary objects should be grouped and encapsulated into a
 * controller that handles their actions.
 * 
 * This controller extends from SpriteController which is used for any
 * collection of game objects. This adds additional functions to an artifact
 * such as the ability to be destroyed, loading their positions from file, and
 * functions for handling collisions with other objects.
 * 
 * @author Game Team
 * 
 * @param <A>
 */
public abstract class ArtifactController<A extends StationaryObject> extends SpriteController<Point, A> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Adds a new artifact to the this collection.
	 * 
	 * @param x the x coordinate position of the artifact on screen
	 * @param y the y coordinate position of the artifact on screen
	 * @param object the artifact to add to the collection
	 */
	public void addObject(int x, int y, A object) {
		super.addObject(new Point(x, y), object);
	}

	/**
	 * 
	 * This method will be used to add artifacts to the screen given only
	 * an x and y coordinate. This will be implemented within every
	 * class that extends from this one. Used also to construct
	 * objects loaded from the position file.
	 * 
	 * @param x the x coordinate position of the artifact on screen
	 * @param y the y coordinate position of the artifact on screen
	 * 
	 */
	protected abstract void addArtifact(int x, int y);

	/**
	 * 
	 * Destroys an artifact by removing the item from this collection
	 * and in doing so removing the artifact from the game world. Used
	 * to destroy pills and pellets when they are eaten.
	 * 
	 */
	public void destroy(A artifact) {
		Point artifactLoc = new Point(artifact.x(), artifact.y());
		super.destroyAt(artifactLoc);
	}

	/**
	 * Gets the sprite that collided with the specified object
	 * 
	 * @param c the object to check against
	 * @return the artifact that collided with this object
	 */
	public A getCollidedWith(ControllableObject c) {
		for (A artifact : super.getObjects()) {
			if (artifact.collidedWith(c))
				return artifact;
		}
		return null;
	}

	/**
	 * 
	 * Constructs the artifacts for this controller based on a file
	 * which contains line delineated coordinate pairs. Each coordinate pair
	 * is used to construct an artifact at the specified position.
	 * 
	 * @param filePrefix the file to use for creation
	 */
	public void constructArtifactsFromFile(String filePrefix) {
		ArrayList<Integer> coords = loadFromFile(state.getLevel(), filePrefix);
		constructLevel(coords);
	}
	
	private void constructLevel(ArrayList<Integer> coords) {
		for (int index = 0; index < coords.size() - 1; index += 2) {
			int x = coords.get(index);
			int y = coords.get(index + 1);
			addArtifact(x, y);
		}
	}
	
	private ArrayList<Integer> loadFromFile(int level, String filePrefix) {
		ArrayList<Integer> coordsList = new ArrayList<Integer>();
		String file = "levels/" + filePrefix + "s/" + level + ".txt";
		try {
			Scanner s = new Scanner(new File(file));
			while (s.hasNextLine()) {
				String[] coords = s.nextLine().split(",\\s?");
				coordsList.add(Integer.parseInt(coords[0]));
				coordsList.add(Integer.parseInt(coords[1]));
			}
			return coordsList;
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find Artifact file: " + file);
		}
		return null;
	}
}
