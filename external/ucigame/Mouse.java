// Mouse.java

package ucigame;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Every Ucigame has one object named mouse; the object's methods reveal the
 * location of the mouse and the topmost sprite under the mouse.
 */
public final class Mouse
{
	public final int CROSSHAIR = Cursor.CROSSHAIR_CURSOR;
	public final int DEFAULT   = Cursor.DEFAULT_CURSOR;
	public final int HAND      = Cursor.HAND_CURSOR;
	public final int MOVE      = Cursor.MOVE_CURSOR;
	public final int TEXT      = Cursor.TEXT_CURSOR;
	public final int WAIT      = Cursor.WAIT_CURSOR;
	public final int N_RESIZE  = Cursor.N_RESIZE_CURSOR;
	public final int E_RESIZE  = Cursor.E_RESIZE_CURSOR;
	public final int S_RESIZE  = Cursor.S_RESIZE_CURSOR;
	public final int W_RESIZE  = Cursor.W_RESIZE_CURSOR;
	public final int NE_RESIZE = Cursor.NE_RESIZE_CURSOR;
	public final int NW_RESIZE = Cursor.NW_RESIZE_CURSOR;
	public final int SE_RESIZE = Cursor.SE_RESIZE_CURSOR;
	public final int SW_RESIZE = Cursor.SW_RESIZE_CURSOR;

	public final int NONE   = MouseEvent.NOBUTTON;
	public final int LEFT   = MouseEvent.BUTTON1;
	public final int MIDDLE = MouseEvent.BUTTON2;
	public final int RIGHT  = MouseEvent.BUTTON3;

	private Ucigame ucigame;

	/**
	 * Create new mouse object
	 */
	Mouse(Ucigame _u)
	{
		ucigame = _u;
	}

	/**
	 * Returns the current x position of the mouse, relative to the upper left hand
	 * corner of the game window, which is at position (0, 0).
	 *
	 * @return the x position of the mouse
	 */
	public int x() { return ucigame.mouseX; }

	/**
	 * Returns the current y position of the mouse, relative to the upper left hand
	 * corner of the game window, which is at position (0, 0).
	 *
	 * @return the y position of the mouse
	 */
	public int y() { return ucigame.mouseY; }

	public int Xchange() { return ucigame.mouseChangeX; }

	public int Ychange() { return ucigame.mouseChangeY; }

	public int button() { return ucigame.mouseButton; }

	public boolean isAltDown() { return ucigame.mouseIsAltDown; }

	public boolean isControlDown() { return ucigame.mouseIsControlDown; }

	public boolean isMetaDown() { return ucigame.mouseIsMetaDown; }

	public boolean isShiftDown() { return ucigame.mouseIsShiftDown; }

	public int wheelClicks() { return ucigame.mouseWheelUnits; }

	/**
	 * Returns the topmost Sprite object under the current mouse position. The return
	 * value will be null  if the mouse position is not over a sprite.
	 *
	 * @return the sprite object beneath current mouse position
	 */
	public Sprite sprite() { return ucigame.mouseSprite; }

	/**
	 * Changes the cursor to one of the standard system cursors. Legal values for cursorType are:
	 * mouse.CROSSHAIR, mouse.DEFAULT, mouse.HAND, mouse.MOVE, mouse.TEXT, mouse.WAIT, mouse.N_RESIZE,
	 * mouse.NE_RESIZE, mouse.E_RESIZE, mouse.SE_RESIZE, mouse.S_RESIZE, mouse.SW_RESIZE, mouse.W_RESIZE,
	 * mouse.NW_RESIZE.
	 *
	 * @param _c the cursor type
	 */
	public void setCursor(int _c)
	{
		ucigame.setCursor(_c);
	}

	/**
	 * Changes the cursor to the image supplied. The size of the cursor is determined
	 * by the operating system, and the image will be scaled if necessary. In practice 32 by
	 * 32 works well. The xPos and yPos values indicate the position of the cursor's "hot spot,"
	 * relative to the upper left hand corner of the image.
	 *
	 * @param _image the image to be used as a cursor
	 * @param _x the x value of the cursor hotspot
	 * @param _y the y value of the cursor hotspot
	 */
	public void setCursor(Image _image, int _x, int _y)
	{
		ucigame.setCursor(_image, _x, _y);
	}

}
