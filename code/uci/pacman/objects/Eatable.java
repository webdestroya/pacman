package code.uci.pacman.objects;

public interface Eatable {
	/**
	 * method called when superior object collides with this object
	 */
	public void eaten();
	/**
	 * returns the pointValue for every object
	 * @return
	 */
	public int getValue();

}
