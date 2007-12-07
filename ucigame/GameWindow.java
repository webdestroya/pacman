package ucigame;

import java.awt.Dimension;

public class GameWindow
{
	private Ucigame ucigame;

	GameWindow(Ucigame _u)
	{
		ucigame = _u;
	}

	/*
		Sets the width and height of the internal area of the window.
		The actual window will be slightly wider and higher to account
		for the frame and title bar. 
	*/
	int clientWidth, clientHeight;
	String title;
	boolean showfps = false;

	/**
	 * Sets the width and height of the internal area of the window. The actual
	 * window will be slightly wider and higher to account for the frame and title bar.
	 *
	 *  @param _width the width of the internal area of the window
	 *  @param _height the height of the internal area of the window
	 *
	 */
	public void size(int _width, int _height)
	{
		if (ucigame.isApplet)
		{
			clientWidth = ucigame.getWidth();
			clientHeight = ucigame.getHeight();
		}
		else if (50 <= _width && _width <= 2000 &&
				 50 <= _height && _height <= 2000)
		{
			ucigame.gameCanvas.setPreferredSize(new Dimension(_width, _height));
			ucigame.gameCanvas.setMinimumSize(new Dimension(_width, _height));
			clientWidth = _width;
			clientHeight = _height;
		}
		else
			size(100, 100);  // bad parm values, use reasonable defaults
	}

	/**
	 * Set the title of the window.
	 *
	 * @param _title the title of the window
	 */
	public void title(String _title)
	{
		title = _title;
		if (ucigame.frame != null)
			ucigame.frame.setTitle(_title);
	}

	/**
	 * Method will show the FPS of the window.
	 */
	public void showFPS() { showfps = true; }

	/**
	 * Method will hide the FPS of the window.
	 */
	public void hideFPS()
	{
		showfps = false;
		if (ucigame.frame != null)
			ucigame.frame.setTitle(title);
	}

	/**
	 * Method will set the FPS of the window.
	 *
	 * @param _f the FPS value
	 */
	public void setfps(int _f)
	{
		if (showfps && ucigame.frame != null)
			ucigame.frame.setTitle(title + " (" + _f + ")");
	}

	/**
	 * Get the width of the window.
	 *
	 * @return the width of the internal area of the window
	 */
	int clientWidth() { return clientWidth; }

	/**
	 * Get the height of the window.
	 *
	 * @return the height of the internal area of the window
	 */
	int clientHeight() { return clientHeight; }
}
