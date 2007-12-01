package code.uci.pacman.controllers.utilities;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import code.uci.pacman.objects.ControllableObject;
import code.uci.pacman.objects.StationaryObject;
import ucigame.Image;

public abstract class ArtifactController<A extends StationaryObject> extends SpriteController<Point, A> {
	private static final long serialVersionUID = 1L;

	public void addObject(int x, int y, A object) {
		super.addObject(new Point(x, y), object);
	}

	public abstract void addArtifact(int x, int y);

	public A getObjectAt(int x, int y) {
		return super.getObjectAt(new Point(x, y));
	}

	public void destroyAt(int x, int y) {
		super.destroyAt(new Point(x, y));
	}

	public void destroy(A artifact) {
		Point artifactLoc = new Point(artifact.x(), artifact.y());
		super.destroyAt(artifactLoc);
	}

	/**
	 * returns the sprite that collided with the ControllableObject
	 * 
	 * @param ControllableObject
	 * @return a Stationary
	 */
	public A getCollidedWith(ControllableObject c) {
		for (A artifact : super.getObjects()) {
			if (artifact.collided(c))
				return artifact;
		}
		return null;
	}

	public void constructArtifacts(String filePrefix) {
		ArrayList<Integer> coords = loadFromFile(state.getLevel(), filePrefix);
		constructLevel(coords);
	}

	private ArrayList<Integer> loadFromFile(int level, String filePrefix) {
		ArrayList<Integer> coordsList = new ArrayList<Integer>();
		String file = "levels/"+filePrefix+"s/"+level+".txt";
		try {
			Scanner s = new Scanner(new File(file));
			while(s.hasNextLine()){
				String[] coords = s.nextLine().split(",\\s?");
				coordsList.add(Integer.parseInt(coords[0]));
				coordsList.add(Integer.parseInt(coords[1]));
			}
			return coordsList;
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find Artifact file: "+ file);
		}
		// TODO Auto-generated method stub
		return null;
	}

	protected void constructLevel(ArrayList<Integer> coords) {
		for (int index = 0; index < coords.size() - 1; index += 2) {
			int x = coords.get(index);
			int y = coords.get(index + 1);
			addArtifact(x, y);
		}
	}

	public Collection<A> getObjects() {
		return super.getObjects();
	}
}
