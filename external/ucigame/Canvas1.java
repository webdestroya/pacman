package external.ucigame;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The canvas object on which the game is drawn.
 */
public class Canvas1
{
	private Ucigame ucigame;

	Canvas1(Ucigame _u)
	{
		ucigame = _u;
	}

	public int width() { return ucigame.window.clientWidth(); }

	public int height() { return ucigame.window.clientHeight(); }

	/**
	 * This method determines the color of the canvas's background. To actually set
	 * the background to that color, call the canvas.clear() method inside of draw().
	 * The one parameter version of background() sets the background color to a shade
	 * of gray. The varible shade can range from 0 (black) to 128 (gray) to 255 (white).
	 * If shade is less than 0 or greater than 255, the method call has no affect. The
	 * default background color is white.
	 */
	public void background(int _c)
	{
		if (0 <= _c && _c <= 255)
		{
			ucigame.bgColor = new Color(_c, _c, _c);
			ucigame.bgImage = null;
		}
	}

	/**
	 * The three parameter version background() can set the background to any color.
	 * See the about color page for more information about colors in Ucigame and computer graphics.
	 */
	public void background(int _r, int _g, int _b)
	{
		if (0 <= _r && _r <= 255 &&
			0 <= _g && _g <= 255 &&
			0 <= _b && _b <= 255)
		{
			ucigame.bgColor = new Color(_r, _g, _b);
			ucigame.bgImage = null;
		}
	}

	/**
	 * Set the background using the image provided.
	 */
	public void background(Image _image)
	{
		ucigame.bgImage = _image;
		ucigame.bgColor = null;

	}

	/**
	 * Set the drawing color a specific shade.  White, grey, and black.
	 */
	public void color(int _c)
	{
		if (0 <= _c && _c <= 255)
			if (ucigame.offG != null)
				ucigame.offG.setColor(new Color(_c, _c, _c));
			else
				ucigame.logError("canvas.color(" + _c + ") used outside of draw()");
	}

	/**
	 * Set the drawing color to any color.
	 */
	public void color(int _r, int _g, int _b)
	{
		if (0 <= _r && _r <= 255 &&
			0 <= _g && _g <= 255 &&
			0 <= _b && _b <= 255)
			if (ucigame.offG != null)
				ucigame.offG.setColor(new Color(_r, _g, _b));
			else
				ucigame.logError("canvas.color(" + _r + ", " + _g + ", " + _b + ") used outside of draw()");
	}

	/**
	 * Clear the canvas to redraw elements.
	 */
	public void clear()
	{
		if (ucigame.offG != null)
		{
			if (ucigame.bgColor != null)
			{
				Color c = ucigame.offG.getColor();
				ucigame.offG.setColor(ucigame.bgColor);
				ucigame.offG.fillRect(0, 0, ucigame.canvas.width(), ucigame.canvas.height());
				ucigame.offG.setColor(c);
			}
			else if (ucigame.bgImage != null)
			{
				ucigame.bgImage.draw(0, 0);
			}
		}
		else
			ucigame.logError("canvas.clear() used outside of draw()");
	}


	/**
	 * Draw a line on the canvas.
	 *
	 * @param _x1 the x value of the first point
	 * @param _y1 the y value of the first point
	 * @param _x2 the x value of the second point
	 * @param _y2 the y value of the second point
	 */
	public void line(double _x1, double _y1, double _x2, double _y2)
	{
		if (ucigame.offG != null)
			ucigame.offG.drawLine(ucigame.r(_x1), ucigame.r(_y1),
								ucigame.r(_x2), ucigame.r(_y2));
		else
			ucigame.logError("canvas.line(" + _x1 + ", " + _y1 + ", " + _x2 + ", " + _y2 +
					 ") used outside of draw()");
	}

	/**
	 * Draw an oval on the canvas.
	 *
	 * @param _x1 the x value of the upperleft corner of the enclosing rectangle
	 * @param _y1 the y value of the upperleft corner of the enclosing rectangle
	 * @param _w the width of the enclosing rectangle
	 * @param _h the height of the enclosing rectangle
	 */
	public void oval(int _x1, int _y1, int _w, int _h)
	{
		if (ucigame.offG != null)
			ucigame.offG.drawOval(_x1, _y1, _w, _h);
		else
			ucigame.logError("canvas.oval(" + _x1 + ", " + _y1 + ", " + _w + ", " + _h +
					 ") used outside of draw()");
	}

	/**
	 * Draw an oval on the canvas.
	 *
	 * @param _x1 the x value of the upperleft corner of the enclosing rectangle
	 * @param _y1 the y value of the upperleft corner of the enclosing rectangle
	 * @param _w the width of the enclosing rectangle
	 * @param _h the height of the enclosing rectangle
	 * @param _option the option to file in the oval
	 */
	public void oval(int _x1, int _y1, int _w, int _h, int _option)
	{
		if (_option != ucigame.FILL)
			ucigame.logError("canvas.oval used with last parameter not FILL");
		else if (ucigame.offG != null)
			ucigame.offG.fillOval(_x1, _y1, _w, _h);
		else
			ucigame.logError("canvas.oval(" + _x1 + ", " + _y1 + ", " + _w + ", " + _h +
					 ", FILL) used outside of draw()");
	}

	/**
	 * Set the font style and size.
	 */
	public void font(String _name, int _style, int _size)
	{
		if (_style == ucigame.BOLD || _style == ucigame.PLAIN ||
			_style == ucigame.ITALIC ||	_style == ucigame.BOLDITALIC)
			;
		else
		{
			ucigame.logError("Invalid style parameter in canvas.font()");
			_style = ucigame.PLAIN;
		}
		ucigame.windowFont = new Font(_name, _style, _size);
		//System.out.println("Font: " + spriteFont);
		if (ucigame.windowFont.getFamily().equalsIgnoreCase(_name) ||
			ucigame.windowFont.getFontName().equalsIgnoreCase(_name))
			;
		else
			ucigame.logWarning("Could not create font with name " + _name +
					". Using font " + ucigame.windowFont.getFontName() + " instead.");

	}
	
	public void font(String _name, int _style, int _size, int _r, int _g, int _b){
		this.font(_name, _style, _size);
		this.color(_r, _g, _b);
	}

	/**
	 * Put a number on the canvas.
	 *
	 * @param _n the number to print out
	 * @param _x the x value of where to print
	 * @param _y the y value of where to print
	 */
	public void putText(int _n, double _x, double _y)
	{
		putText(""+_n, _x, _y);
	}

	/**
	 * Put text on the canvas.
	 */
	public void putText(String _string, double _x, double _y)
	{
		if (ucigame.offG == null)
		{
			ucigame.logError("canvas.putText(" + _string + "," + _x + ", " + _y +
					 ") used outside of draw()");
			return;
		}
		if (ucigame.windowFont != null)
			ucigame.offG.setFont(ucigame.windowFont);
		ucigame.offG.drawString(_string, (int)_x, (int)_y);
	}
}

