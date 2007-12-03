package code.uci.pacman.objects;

/**
 * Represents all objects that are eatable (Ghost, Pacman, Fruit, Pill, PowerPellet).
 * @author Team Objects/AI
 *
 */
public interface Eatable {
	/**
	 * method called when superior object collides with this object
	 */
	public void eaten();

}
