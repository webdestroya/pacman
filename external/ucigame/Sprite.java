// Sprite.java

package ucigame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.*;
import java.awt.geom.AffineTransform;

/**
 * A sprite is an image that can be moved around in the game window. Ucigame
 * uses many kinds of sprites: they can be stationary; they can cycle between
 * multiple images, and they can act like buttons and be sensitive to mouse
 * clicks. Sprites can have text written on them, and multiple sprites can be
 * "pinned" together so that when one moves the others follow.
 *
 * @author Dan Frost
 */
public class Sprite {

	class PinnedSprite // inner class
	{
		Sprite sprite;

		int x;

		int y;

		PinnedSprite(Sprite _s, int _x, int _y) {
			sprite = _s;
			x = _x;
			y = _y;
		}
	}

	private Ucigame ucigame;
	private Vector<Image> gameImage = new Vector<Image>();
	private Image[][] tiledImages = null;
	private Vector<int[][]> transparencyBuffer = null;
	private int currFrame;
	private int numFrames;
	private int tileWidth = 0, tileHeight = 0;
	private int tileCols, tileRows;
	private double deltaX, deltaY;
	private double addOnceDeltaX, addOnceDeltaY;
	private double nextX, nextY;
	private double rotationDegrees;
	private double rotCenterX, rotCenterY;
	private boolean flipH = false, flipV = false;
	private Vector<PinnedSprite> pinnedSprites = new Vector<PinnedSprite>();
	private boolean isButton = false;
	private String buttonName = null;
	private boolean isShown = true;
	private boolean Xcollision;
	private boolean Ycollision;
	private boolean contactOnThisBottom;
	private boolean contactOnThisRight;
	private Font spriteFont = null;
	private Color spriteFontColor = null;
	private double cumFrames = 0;
	private int spriteGoalFPS = 0;

	// package visible
	int width, height;
	double currX, currY;

	/**
	 * This constructor takes an already loaded Image object, and creates
	 * a Sprite with the same width and height as the image. Usually used for
	 * creating sprites with a single image.
	 */
	public Sprite(Image _image) {
		if (_image == null ||
		    _image.width() < 1 ||
		    _image.height() < 1)
		{
			Ucigame.logError("in Sprite constructor: image is invalid.");
		}
		ucigame = Ucigame.ucigameObject;
		gameImage.add(_image);
		currFrame = 0;
		numFrames = 1;
		width = _image.width();
		height = _image.height();
		deltaX = deltaY = 0;
		rotationDegrees = 0;
		addOnceDeltaX = addOnceDeltaY = 0;
	}

	// Makes a tiled sprite.
	// Note that the first dimension in tiledImages is the column (the x)
	// and the second is the row (the y) -- this is non-standard,
	// but I like to keep the x and y order.
	public Sprite(int _cols, int _rows, int _tileWidth, int _tileHeight) {
		if (_cols < 1 || _cols > 1000 ||
		    _rows < 1 || _rows > 1000)
		{
			Ucigame.logError("in Sprite constructor (" + _cols + ", " + _rows +
			                ", " + _tileWidth + ", " + _tileHeight +
						") found an illegal number of columns or rows.");
		}
		if (_tileWidth < 1 || _tileWidth > 1000 ||
		    _tileHeight < 1 || _tileHeight > 1000)
		{
			Ucigame.logError("in Sprite constructor (" + _cols + ", " + _rows +
			                ", " + _tileWidth + ", " + _tileHeight +
						") found an illegal width or height.");
		}
		ucigame = Ucigame.ucigameObject;
		tileCols = _cols;
		tileRows = _rows;
		tileWidth = _tileWidth;
		tileHeight = _tileHeight;
		width = _cols * _tileWidth;
		height = _rows * _tileHeight;
		tiledImages = new Image[_cols][];
		for (int col = 0; col < _cols; col++)
			tiledImages[col] = new Image[_rows];
		currFrame = 0;
		numFrames = 1;
		deltaX = deltaY = 0;
		addOnceDeltaX = addOnceDeltaY = 0;
	}

	/**
	 * This makeSprite method takes an already loaded Image object, and creates
	 * a Sprite with the specified width and height. If width is larger than
	 * image's width, and/or height is larger than image's height, then the
	 * image will be tiled to cover the complete sprite.
	 */
	// Likely to have multiple animated images.
	public Sprite(int _w, int _h) {
		if (_w < 1 || _w > 2000 ||
		    _h < 1 || _h > 2000)
		{
			Ucigame.logError("in Sprite constructor, the width or height is invalid");
		}
		ucigame = Ucigame.ucigameObject;
		width = _w;
		height = _h;
		currFrame = 0;
		numFrames = 0;
		deltaX = deltaY = 0;
		addOnceDeltaX = addOnceDeltaY = 0;
	}

	/**
	 * Add a frame to the sprite. Each time the sprite is displayed on the
	 * canvas with draw(), it will automatically cycle to the next image. Image
	 * is an already loaded Image object. The x and y parameters specify the
	 * upper left hand corner of a rectangle in the image that will be sprite's
	 * next frame. The width and height of that rectangle are same as the
	 * sprite1's width and height.
	 */
	public void addFrame(Image _gameImage, int _x, int _y) {
		if (_gameImage == null) {
			ucigame.logError("addFrame(Image, " + _x + ", " + _y + "): "
					+ " first parameter (image) is null.");
			return;
		}
		if (_x < 0 || _y < 0) {
			ucigame.logError("addFrame(Image, " + _x + ", " + _y + "): "
					+ " invalid parameter (less than 0).");
			return;
		}
		BufferedImage newimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		int[] pixels = new int[width * height + 1];
		BufferedImage source = _gameImage.getBufferedImage();
		if (_x >= source.getWidth() || _y >= source.getHeight()
				|| _x + width > source.getWidth()
				|| _y + height > source.getHeight()) {
			ucigame.logError("addFrame(Image, " + _x + ", " + _y + "): "
					+ " requested frame extends beyond image.");
			return;
		}
		source.getRGB(_x, _y, width, height, pixels, 0, width);
		for (int x = 0; x < width; x++) // maybe should use BufferedImage.
		{ // setData(Raster)
			for (int y = 0; y < height; y++) {
				newimage.setRGB(x, y, pixels[x + (y * width)]);
			}
		}
		gameImage.add(new Image(newimage, ucigame));
		numFrames++;
	}

	
	public void setFrames(Image _gameImage, int... _locations){
		gameImage.clear();
		numFrames = 0;
		currFrame = 0;
		addFrames(_gameImage, _locations);
	}
	/**
	 * This method is a variant of addFrame() which allows multiple frames from
	 * one Image object to be added to sprite1 with one method call.
	 */
	public void addFrames(Image _gameImage, int... _locations) {
		if (_locations.length % 2 != 0) {
			ucigame.logError("addFrames() does not have an even number of x and y's.");
			return;
		}
		for (int p = 0; p < _locations.length; p += 2)
			addFrame(_gameImage, _locations[p], _locations[p + 1]);
	}

	/**
	 * The method takes a frame from image (with an upper left hand corner as
	 * specified by x and y and with width and height as specified in
	 * makeTiledSprite()), and puts that frame into one or more of sprite1's
	 * tiles as specified by the col and row pairs. Note that counting columns
	 * and rows starts from zero, and that position (0, 0) is in the matrix's
	 * upper right hand corner. This method can only be used if sprite1 was
	 * created with makeTiledSprite.
	 */
	public void setTiles(Image _gameImage, int _x, int _y, int... _locations) {
		if (tileWidth == 0 || tileHeight == 0 || tiledImages == null) {
			ucigame.logError("Cannot call setTiles() unless the sprite was\n"
					+ "created with makeTiledSprite().");
			return;
		}
		if (_gameImage == null) {
			ucigame.logError("setTiles(Image, " + _x + ", " + _y + ", ...): "
					+ " first parameter (image) is null.");
			return;
		}
		if (_x < 0 || _y < 0) {
			ucigame.logError("setTiles(Image, " + _x + ", " + _y + ", ...): "
					+ " invalid parameter (less than 0).");
			return;
		}
		if (_locations.length % 2 != 0) {
			ucigame.logError("setTiles(Image, " + _x + ", " + _y + ", ...): "
					+ "not an even number of cols and rows.");
			return;
		}
		if (_locations.length == 0) {
			ucigame.logError("setTiles(Image, " + _x + ", " + _y + ", ...): "
					+ "no cols and rows specified.");
			return;
		}

		BufferedImage newimage = new BufferedImage(tileWidth, tileHeight,
				BufferedImage.TYPE_INT_ARGB);
		int[] pixels = new int[tileWidth * tileHeight + 1];
		BufferedImage source = _gameImage.getBufferedImage();
		if (_x >= source.getWidth() || _y >= source.getHeight()
				|| _x + tileWidth > source.getWidth()
				|| _y + tileHeight > source.getHeight()) {
			ucigame.logError("setTiles(Image, " + _x + ", " + _y + "): "
					+ " requested frame extends beyond image.");
			return;
		}
		source.getRGB(_x, _y, tileWidth, tileHeight, pixels, 0, tileWidth);
		for (int x = 0; x < tileWidth; x++) {
			for (int y = 0; y < tileHeight; y++) {
				newimage.setRGB(x, y, pixels[x + (y * tileWidth)]);
			}
		}
		Image tileImage = new Image(newimage, ucigame);
		for (int p = 0; p < _locations.length; p += 2) {
			int col = _locations[p];
			int row = _locations[p + 1];
			if (col < 0 || col >= tileCols || row < 0 || row >= tileRows) {
				ucigame.logError("setTiles(Image, " + _x + ", " + _y
						+ ", ...): " + " col " + col + " or row " + row
						+ " is invalid");
				return;
			}
			tiledImages[col][row] = tileImage;
		}
	}

	/**
	 * Pins the sprite (sprite2) passed in on top of current sprite (sprite1),
	 * which means that sprite2 will be drawn whenever sprite1.draw() is
	 * performed. The upper left hand corner of sprite2 is located x pixels to
	 * the right and y pixels below the upper left hand corner of sprite1; thus
	 * sprite2 moves when sprite1 is moved. Any number of other sprites can be
	 * pinned to a sprite. A sprite cannot be pinned to itself. If a sprite is
	 * hidden, all sprites pinned to it are hidden.
	 */
	public void pin(Sprite _sprite, int _x, int _y) {
		if (this != _sprite)
			pinnedSprites.add(new PinnedSprite(_sprite, _x, _y));
	}

	/**
	 * Sets a framerate for this sprite; Ucigame will try to change the sprite's
	 * current frame number times per second, although there is no guarantee
	 * that it will be successful. This method has no effect if number is
	 * negative or greater than the framerate specified for the entire game.
	 */
	public void framerate(int _d) {
		if (_d == 0) {
			spriteGoalFPS = 0; // turn it off
			return;
		} else if (0 < _d && _d <= 1000) {
			spriteGoalFPS = _d;
			cumFrames = 0;
		} else
			ucigame.logError("sprite.framerate(" + _d
					+ ") has an invalid parameter.");
	}

	/**
	 * Rotates the sprite counterclockwise by the number of degrees specified.
	 * The center of rotation is the center of the sprite. Any sprites pinned to
	 * this sprite are also rotated around the same center of rotation. Note
	 * that rotation occurs immediately before sprite.draw() is executed, no
	 * matter when the call to rotate() is made (as long as rotate() is called
	 * before draw()). This means that rotation does not affect collision
	 * detection; collision detection is computed on the unrotated sprite.
	 * (Defined as a feature, but could well be a bug.) After draw() is called
	 * on a sprite any rotation is removed; thus if a sprite should be rotated
	 * in every frame rotate() must be called before each draw().
	 */
	public void rotate(double degrees) {
		rotationDegrees = degrees;
		rotCenterX = (double) width / 2.0;
		rotCenterY = (double) height / 2.0;
	}

	/**
	 * Same as the one parameter version of rotate(), except that the center of
	 * rotation is x pixels to the right and y pixels down from the upper left
	 * hand corner of the sprite.
	 */
	public void rotate(double degrees, double _rotCenterX, double _rotCenterY) {
		rotationDegrees = degrees;
		rotCenterX = _rotCenterX;
		rotCenterY = _rotCenterY;
	}

	/**
	 * This methods flip the sprite around a vertical line running through the
	 * center of the sprite.
	 */
	public void flipHorizontal() {
		flipH = true;
	}

	/**
	 * This methods flip the sprite around a horizontal line running through the
	 * center of the sprite.
	 */
	public void flipVertical() {
		flipV = true;
	}

	/**
	 * Causes the sprite, and any other sprites pinned to this sprite, to be
	 * drawn on the canvas, unless the sprite is hidden. Can only be called
	 * inside the game class's draw() method.
	 */
	public void draw() {
		draw(new AffineTransform()); // call with identity
	}

	/**
	 * Causes the sprite, and any other sprites pinned to this sprite, to be
	 * drawn on the canvas, unless the sprite is hidden. Can only be called
	 * inside the game class's draw() method.
	 */
	public void draw(AffineTransform _Tx) {
		if (!isShown)
			return;
		if (numFrames == 0)
			return;
		currX = nextX;
		currY = nextY;

		AffineTransform at = new AffineTransform(_Tx);
		at.translate(currX + rotCenterX, currY + rotCenterY); // third
		at.rotate(rotationDegrees * Math.PI / 180.0); // second
		at.translate(-rotCenterX, -rotCenterY); // first
		if (flipH || flipV) // flips happen, logically, before rotations
		{
			at.translate((double) width / 2.0, (double) height / 2.0); // third
			at.scale(flipH ? -1 : 1, flipV ? -1 : 1); // second
			at.translate(-(double) width / 2.0, -(double) height / 2.0);// first
			flipH = flipV = false;
		}
		if (tiledImages == null)
			gameImage.get(currFrame).draw(at);
		else {
			for (int col = 0; col < tileCols; col++)
				for (int row = 0; row < tileRows; row++) {
					if (tiledImages[col][row] != null) {
						AffineTransform at2 = new AffineTransform(_Tx);
						at2.translate(currX + rotCenterX + (col * tileWidth),
								currY + rotCenterY + (row * tileHeight));
						at2.rotate(rotationDegrees * Math.PI / 180.0);
						at2.translate(-rotCenterX, -rotCenterY);
						tiledImages[col][row].draw(at2);
					}
				}
		}
		ucigame.addSpriteToList(this);
		if (!isButton) {
			if (spriteGoalFPS == 0)
				currFrame = (currFrame + 1) % numFrames;
			else {
				cumFrames += spriteGoalFPS;
				if (cumFrames >= ucigame.goalFPS) {
					cumFrames -= ucigame.goalFPS;
					currFrame = (currFrame + 1) % numFrames;
				}
			}
		}
		for (PinnedSprite ps : pinnedSprites) {
			ps.sprite.nextX = ps.x;
			ps.sprite.nextY = ps.y;
			// ps.sprite.nextX = this.currX + ps.x;
			// ps.sprite.nextY = this.currY + ps.y;
			ps.sprite.draw(at);
			ucigame.addSpriteToList(ps.sprite);
		}
		rotationDegrees = rotCenterX = rotCenterY = 0;
	}

	/**
	 * This method specifies the font to use in subsequent calls to
	 * sprite1.putText().
	 */
	public void font(String _name, int _style, int _size) {
		font(_name, _style, _size, 0, 0, 0);
	}

	public void font(String _name, int _style, int _size, int _r, int _g, int _b) {
		if (_style == ucigame.BOLD || _style == ucigame.PLAIN ||
			_style == ucigame.ITALIC || _style == ucigame.BOLDITALIC)
			;
		else {
			ucigame.logError("Invalid style parameter in Sprite.font()");
			_style = ucigame.PLAIN;
		}
		spriteFont = new Font(_name, _style, _size);
		// System.out.println("Font: " + spriteFont);
		if (spriteFont.getFamily().equalsIgnoreCase(_name)
				|| spriteFont.getFontName().equalsIgnoreCase(_name))
			;
		else
			ucigame.logWarning("Could not create font with name " + _name
					+ ". Using font " + spriteFont.getFontName() + " instead.");
		if (0 <= _r && _r <= 255 && 0 <= _g && _g <= 255 && 0 <= _b && _b <= 255)
			spriteFontColor = new Color(_r, _g, _b);
		else
			spriteFontColor = Color.BLACK;
	}

	/**
	 * Draws the specified int on the sprite, using the sprite's current font.
	 * The lower left hand corner of text is located x pixels to the right and y
	 * pixels below the upper left hand corner of sprite1. Any part of text that
	 * lies outside the sprite is clipped (not displayed).
	 */
	public void putText(int _n, double _x, double _y) {
		putText("" + _n, _x, _y);
	}

	public void putText(double _d, double _x, double _y) {
		putText("" + _d, _x, _y);
	}

	/**
	 * Draws the specified text on the sprite, using the sprite's current font.
	 * The lower left hand corner of text is located x pixels to the right and y
	 * pixels below the upper left hand corner of sprite1. Any part of text that
	 * lies outside the sprite is clipped (not displayed).
	 */
	public void putText(String _string, double _x, double _y) {
		if (ucigame.offG == null) {
			ucigame.logError("Sprite.putText(" + _string + "," + _x + ", "
					+ _y + ") used outside of draw()");
			return;
		}
		if (isShown) {
			ucigame.offG.setClip((int) currX, (int) currY, width, height);
			if (spriteFont != null)
				ucigame.offG.setFont(spriteFont);
			Color prevColor = ucigame.offG.getColor();
			if (spriteFontColor != null)
				ucigame.offG.setColor(spriteFontColor);
			ucigame.offG.drawString(_string, (int) (currX + _x), (int) (currY + _y));
			ucigame.offG.setClip(null);
			ucigame.offG.setColor(prevColor);
		}
	}

	/**
	 * This method returns the sprite's width.
	 */
	public int width() {
		return width;
	}

	/**
	 * This method returns the sprite's height.
	 */
	public int height() {
		return height;
	}

	/**
	 * Hide the sprite from view.
	 */
	public void hide() {
		isShown = false;
	}

	/**
	 * Show the sprite.
	 */
	public void show() {
		isShown = true;
	}

	/**
	 * Check to make sure the sprite is set to be visible.
	 */
	public boolean isShown() {
		return isShown;
	}

	/**
	 * Moves the sprite so that its upper left hand corner is at the specified
	 * position, relative to the upper left hand corner of the canvas.
	 */
	public void position(double _x, double _y) {
		currX = _x;
		currY = _y;
		nextX = _x;
		nextY = _y;
	}

	/**
	 * Sets the x and y motion amount for this sprite. See move().
	 */
	public void motion(double _x, double _y) {
		deltaX = _x;
		deltaY = _y;
	}

	/**
	 * Change the motion amount base on the command provided.
	 */
	public void motion(double _x, double _y, int _COMMAND) {
		if (_COMMAND == ucigame.SET) {
			deltaX = _x;
			deltaY = _y;
		} else if (_COMMAND == ucigame.ADD) {
			deltaX += _x;
			deltaY += _y;
		} else if (_COMMAND == ucigame.ADDONCE) {
			addOnceDeltaX += _x;
			addOnceDeltaY += _y;
		} else if (_COMMAND == ucigame.MULTIPLY) {
			deltaX *= _x;
			deltaY *= _y;
		} else
			ucigame.logError("motion(" + _x + ", " + _y
					+ ", ???) -- last parameter not valid.");
	}

	/**
	 * Moves the sprite by its x and y motion amounts. Typically this method is
	 * called once for every moving sprite, before collision detection.
	 */
	public void move() {
		nextX = currX + deltaX + addOnceDeltaX;
		//HLOC: check for negative value
		///if(nextX < 0){
		///	nextX = -1;
		///}
		nextY = currY + deltaY + addOnceDeltaY;
		//HLOC: check for negative value
		///if(nextY < 0){
		///	nextY = -1;
		///}
		addOnceDeltaX = addOnceDeltaY = 0;
	}

	/**
	 * This method return the x values of the sprite's upper left hand corner
	 * (relative to the upper left hand corner of the canvas).
	 *
	 * @return the x value of the sprites upper left hand corner
	 */
	public int x() {
		return (int) (Math.round(currX));
	}

	/**
	 * This method return the y values of the sprite's upper left hand corner
	 * (relative to the upper left hand corner of the canvas).
	 *
	 * @return the y value of the sprites upper left hand corner
	 */
	public int y() {
		return (int) (Math.round(currY));
	}

	/**
	 * Sets the sprite's next x positions (relative to the upper left hand
	 * corner of the canvas). This method is usually used instead of move(), but
	 * like move() is called before collision detection and before draw().
	 * Calling this method sets the sprites x motion amounts to 0.
	 *
	 * @param _nextX
	 *            the next x value for the sprite
	 */
	public void nextX(double _nextX) {
		nextX = _nextX;
		deltaX = 0;
	}

	/**
	 * Sets the sprite's next y positions (relative to the upper left hand
	 * corner of the canvas). This method is usually used instead of move(), but
	 * like move() is called before collision detection and before draw().
	 * Calling this method sets the sprites y motion amounts to 0.
	 *
	 * @param _nextY
	 *            the next y value for the sprite
	 */
	public void nextY(double _nextY) {
		nextY = _nextY;
		deltaY = 0;
	}

	/**
	 * This methods return the sprite's current x change amounts.
	 *
	 * @return the sprite's x change amount
	 */
	public double xspeed() {
		return deltaX;
	}

	/**
	 * This methods return the sprite's current y change amounts.
	 */
	public double yspeed() {
		return deltaY;
	}

	/**
	 * Returns a boolean value of true or false, depending on
	 * whether an collision was detected in the immediately preceeding
	 * xxxIfCollidesWith() call.
	 */
	public boolean collided(int... sides) {
		if (sides.length == 0) // any side
			return Xcollision || Ycollision;
		for (int side : sides) {
			if (side == ucigame.LEFT && Xcollision && !contactOnThisRight)
				return true;
			else if (side == ucigame.RIGHT && Xcollision && contactOnThisRight)
				return true;
			else if (side == ucigame.TOP && Ycollision && !contactOnThisBottom)
				return true;
			else if (side == ucigame.BOTTOM && Ycollision
					&& contactOnThisBottom)
				return true;
			if (side != ucigame.LEFT && side != ucigame.RIGHT
					&& side != ucigame.TOP && side != ucigame.BOTTOM)
				ucigame.logError("collided() called with illegal value");
		}
		return false;
	}

	/**
	 * Makes a button with the specified name.
	 */
	public void makeButton(String _name) {
		isButton = true;
		buttonName = _name;
	}

	// d drag, m move, p press r release, x, y);
	public final void buttonAction(char _action, int _x, int _y) {
		if (!(isShown && isButton))
			return;
		boolean over = _x >= currX && _x < currX + width && _y >= currY
				&& _y < currY + height;
		// System.out.println("action: " + _action + " over: " + over);
		if (_action == 'M' && over) // mouseMove event
		{
			if (currFrame == 0)
				currFrame = 1;
		} else if (_action == 'M' && !over) // mouseMove event
		{
			if (currFrame == 1 || currFrame == 2)
				currFrame = 0;
		} else if (_action == 'D' && over) // mouseDrag event
		{
			;
		} else if (_action == 'D' && !over) // mouseDrag event
		{
			;
		} else if (_action == 'P' && over) // mousePressed event
		{
			if (currFrame == 0 || currFrame == 1)
				currFrame = 2;
		} else if (_action == 'P' && !over) // mousePressed event
		{
			;
		} else if (_action == 'R' && over) // mouseReleased event
		{
			if (currFrame == 2) {
				currFrame = 1;
				Method m = ucigame.name2method.get(buttonName);
				try {
					m.invoke(ucigame.isApplet ? ucigame : ucigame.gameObject);
				} catch (Exception e) {
					e.printStackTrace(System.err);
					ucigame.logError("Exception4 while invoking " + m.getName()
							+ "\n" + e + "\n" + e.getCause());
				}
			}
		} else if (_action == 'R' && !over) // mouseReleased event
		{
			if (currFrame == 2)
				currFrame = 0;
		}
	}

	private final int BOUNCE = 1234321;
	private final int STOP   = 1234322;
	private final int CHECK  = 1234567;
	private final int PAUSE  = 1234568;

	/**
	 * This method tests whether this sprite overlaps with the sprite passed in
	 * and bounce if collision occurs.
	 */
	public void bounceIfCollidesWith(Sprite... _sprite) {
		somethingIfCollidesWith("bounce", BOUNCE, _sprite);
	}

	/**
	 * This method tests whether this sprite overlaps with the sprite passed in
	 * and stop movement if collision occurs.
	 */
	public void stopIfCollidesWith(Sprite... _sprite) {
		somethingIfCollidesWith("stop", STOP, _sprite);
	}

	/**
	 * Like the other xxxIfCollidesWith() methods, this methods test whether the
	 * two sprites will overlap in the next x and y positions. However, this
	 * methods don't change the position or motion of either sprite. The
	 * programmer can then test whether an overlap occurred with collided() and
	 * code the desired behavior.
	 */
	public void checkIfCollidesWith(Sprite... _sprite) {
		somethingIfCollidesWith("check", CHECK, _sprite);
	}

	/**
	 * This method tests whether this sprite overlaps with the sprite passed in
	 * and pauses. Pausing means that sprite1 moves as far as possible without
	 * overlapping sprite2, and its x and y motion amounts are unchanged.
	 */
	public void pauseIfCollidesWith(Sprite... _sprite) {
		somethingIfCollidesWith("pause", PAUSE, _sprite);
	}

	/**
	 * Check for collision and perform appropriate actions.
	 */
	private void somethingIfCollidesWith(
			String _name, int _action, Sprite... _sprite) {
		if (_sprite.length == 0 ||
		    (_sprite.length == 1 && _sprite[0] == Ucigame.PIXELPERFECT))
			ucigame.logError(_name + "IfCollidesWith called with no Sprite specified.");

		boolean pixelPerfect = (_sprite[_sprite.length-1] == ucigame.PIXELPERFECT);

		// check each sprite listed, after initializing flags to false
		Xcollision = false;
		Ycollision = false;
		contactOnThisBottom = false;
		contactOnThisRight = false;
		for (Sprite s : _sprite)
		{
			if (s == ucigame.PIXELPERFECT)	// skip this flag
				continue;
			if (pixelPerfect)
				ifCollidesWithPixelPerfect(s, _action);
			else
				ifCollidesWith(s, _action);
		}
	}

	/**
	 * Check for collision
	 */
	// for right now, just based on final (next) positions, not on entire trajectory
	private void ifCollidesWith(Sprite _sprite, int _action) {
		// System.out.println("in ifCollidesWith, position:" +
		// nextX + ", " + nextY);
		double XcollisionTime = 0;
		double YcollisionTime = 0;
		boolean XcollisionThisSprite = false;		// based on _sprite
		boolean YcollisionThisSprite = false;

		if (_sprite == null)
			return;
		if (!overlapsWith(_sprite)) {
			return;
		}
		if (_action == CHECK) {
			Xcollision = Ycollision = true;
			return;
		}

		if (_sprite == ucigame.BOTTOMEDGE)
		{
			if (nextY + height >= ucigame.window.clientHeight())
			{
				if(currY + height > ucigame.window.clientHeight())	// already out of screen
					YcollisionTime = 0;
				else
					YcollisionTime = (ucigame.window.clientHeight() - (currY + height))
									/ (nextY - currY);
				Ycollision = true;
				YcollisionThisSprite = true;
				contactOnThisBottom = true;
			}
		}
		else if (_sprite == ucigame.TOPEDGE)
		{
			if (nextY < 0)
			{
				if(currY < 0)	// already out of screen
					YcollisionTime = 0;
				else
					YcollisionTime = -currY	/ (nextY - currY);
				Ycollision = true;
				YcollisionThisSprite = true;
				contactOnThisBottom = false;
			}
		}

		// System.out.println("\n_sprite: " + _sprite);
		// System.out.println("currY+ht: " + (currY + height) +
		// " nextY+ht: " + (nextY + height) +
		// " _sprite.currY:" + _sprite.currY +
		// " _sprite.nextY:" + _sprite.nextY);
		// this's bottom hit other's top
		else if (currY + height <= _sprite.currY && nextY + height > _sprite.nextY) {
			//HLOC: Account for the possibility that the next position of the other sprite will
			//overlap the current position of this sprite
			if((_sprite.nextY - (currY + height)) < 0)
				YcollisionTime = 0;
			else
				YcollisionTime = (_sprite.nextY - (currY + height))
						/ (nextY - currY);
			Ycollision = true;
			YcollisionThisSprite = true;
			contactOnThisBottom = true;
			// System.out.println(".-Y collision at: " + YcollisionTime);
		}
		// this's top hit other's bottom
		else if (currY >= _sprite.currY + _sprite.height
				&& nextY < _sprite.nextY + _sprite.height) {
			//HLOC: Account for negative value
			if((currY - (_sprite.nextY + _sprite.height)) < 0)
				YcollisionTime = 0;
			else
				YcollisionTime = (currY - (_sprite.nextY + _sprite.height))
						/ (currY - nextY);
			Ycollision = true;
			YcollisionThisSprite = true;
			contactOnThisBottom = false;
			// System.out.println("--Y collision at: " + YcollisionTime);
		}

		if (_sprite == ucigame.RIGHTEDGE)
		{
			if (nextX + width >= ucigame.window.clientWidth())
			{
				if(currX + width > ucigame.window.clientWidth())	// already out of screen
					XcollisionTime = 0;
				else
					XcollisionTime = (ucigame.window.clientWidth() - (currX + width))
									/ (nextX - currX);
				Xcollision = true;
				XcollisionThisSprite = true;
				contactOnThisRight = true;
			}
		}
		else if (_sprite == ucigame.LEFTEDGE)
		{
			if (nextX < 0)
			{
				if(currX < 0)	// already out of screen
					XcollisionTime = 0;
				else
					XcollisionTime = -currX	/ (nextX - currX);
				Xcollision = true;
				XcollisionThisSprite = true;
				contactOnThisRight = false;
			}
		}

		// System.out.println("currX+wd: " + (currX + width) +
		// " nextX+wd: " + (nextX + width) +
		// " _sprite.currX:" + _sprite.currX +
		// " _sprite.nextX:" + _sprite.nextX);
		// System.out.println("currX: " + currX +
		// " nextX: " + nextX +
		// " _sprite.currX+wd:" + (_sprite.currX + _sprite.width) +
		// " _sprite.nextX+wd:" + (_sprite.nextX + _sprite.width));
		// this's right hit other's left
		else if (currX + width <= _sprite.currX && nextX + width > _sprite.nextX) {
			//HLOC: Account for negative value
			if((_sprite.nextX - (currX + width)) < 0)
				XcollisionTime = 0;
			else
				XcollisionTime = (_sprite.nextX - (currX + width))
						/ (nextX - currX);
			contactOnThisRight = true;
			Xcollision = true;
			XcollisionThisSprite = true;
			// System.out.println(".-X collision at: " + XcollisionTime);
		}
		// this's left hit other's right
		else if (currX >= _sprite.currX + _sprite.width
				&& nextX < _sprite.nextX + _sprite.width) {
			//HLOC: Account for negative value
			if((currX - (_sprite.nextX + _sprite.width)) < 0)
				XcollisionTime = 0;
			else
				XcollisionTime = (currX - (_sprite.nextX + _sprite.width))
						/ (currX - nextX);
			contactOnThisRight = false;
			Xcollision = true;
			XcollisionThisSprite = true;
			// System.out.println("--X collision at: " + XcollisionTime);
		}
		if (XcollisionTime == -1
				|| (XcollisionTime >= 0 && XcollisionTime <= 1))
			;
		else
			System.out.println("**Unexpected XcollisionTime: " + XcollisionTime);
		if (YcollisionTime == -1
				|| (YcollisionTime >= 0 && YcollisionTime <= 1))
			; // nop
		else
			System.out.println("**Unexpected YcollisionTime: " + YcollisionTime);

		if (!XcollisionThisSprite && !YcollisionThisSprite)
			;
		else if ((!XcollisionThisSprite && YcollisionThisSprite) || // collision in Y only or Y first
				(XcollisionThisSprite && YcollisionThisSprite && YcollisionTime < XcollisionTime))
		{
			if (contactOnThisBottom) // **MORE COMPLEX IF _SPRITE IS MOVING**
			{
				if (_action == BOUNCE) {
					double overlap = (nextY + height) - _sprite.nextY;
					// System.out.println("\tC " + overlap);
					nextY = nextY - overlap; // bounce
					//HLOC: check for negative value
					if(nextY < 0)
						nextY = -1;
					deltaY = -deltaY;
				} else if (_action == STOP) {
					nextY = _sprite.nextY - height - 1;
					deltaY = 0;
				} else if (_action == PAUSE)
					nextY = _sprite.nextY - height - 1;
			} else {
				if (_action == BOUNCE) {
					double overlap = (_sprite.nextY + _sprite.height) - nextY;
					// System.out.println("\tD " + overlap);
					nextY = nextY + overlap; // bounce
					//HLOC: check for negative value
					if(nextY < 0)
						nextY = -1;
					deltaY = -deltaY;
				} else if (_action == STOP) {
					nextY = _sprite.nextY + _sprite.height + 1;
					deltaY = 0;
				} else if (_action == PAUSE)
					nextY = _sprite.nextY + _sprite.height + 1;
			}
		}
		else if ((XcollisionThisSprite && !YcollisionThisSprite) || // collision in X only or X first
				(XcollisionThisSprite && YcollisionThisSprite && XcollisionTime < YcollisionTime))
		{
			if (contactOnThisRight) // **MORE COMPLEX IF _SPRITE IS MOVING**
			{
				if (_action == BOUNCE) {
					double overlap = (nextX + width) - _sprite.nextX;
					// System.out.println("\tA " + overlap);
					nextX = nextX - overlap; // bounce
					//HLOC: check for negative value
					if(nextX < 0)
						nextX = -1;
					deltaX = -deltaX;
				} else if (_action == STOP) {
					nextX = _sprite.nextX - width - 1;
					deltaX = 0;
				} else if (_action == PAUSE)
					nextX = _sprite.nextX - width - 1;
			} else {
				if (_action == BOUNCE) {
					double overlap = (_sprite.nextX + _sprite.width) - nextX;
					// System.out.println("\tB " + overlap);
					nextX = nextX + overlap; // bounce
					//HLOC: check for negative value
					if(nextX < 0)
						nextX = -1;
					deltaX = -deltaX;
				} else if (_action == STOP) {
					nextX = _sprite.nextX + _sprite.width + 1;
					deltaX = 0;
				} else if (_action == PAUSE)
					nextX = _sprite.nextX + _sprite.width + 1;
			}
		}
		else  // collision is X and Y simultaneously
			; // System.out.println("***Unexpected ELSE***");
	}

	/**
	 * Pixel Perfect check for collision and perform appropriate action.
	 */
	private void ifCollidesWithPixelPerfect(Sprite _sprite, int _action) {
		// this version assumes _sprite is not moving, and does
		// go pixel by pixel with this.
		// System.out.println("in ifCollidesWith, position:" +
		// nextX + ", " + nextY);

		if (_sprite == null)
			return;
		if (!overlapsWith(_sprite))
			return;

		//Return a number if pixels overlap;
		int ppCollisionCount = pixelPerfectOverlap(_sprite, this.nextX, this.nextY);

		if(ppCollisionCount <= 0)
			return;
		Xcollision = Ycollision = true; // make collided() work

		//Normal vector
		int dx = pixelPerfectOverlap(_sprite, this.nextX +1, this.nextY) -
			pixelPerfectOverlap(_sprite, this.nextX -1, this.nextY);
		int dy = pixelPerfectOverlap(_sprite, this.nextX, this.nextY +1) -
			pixelPerfectOverlap(_sprite, this.nextX, this.nextY -1);


		//Convert the vector to positive values to calculate the angle
		//between the normal vector and the horizontal line
		int tempDx, tempDy;

		if(dx < 0)
			tempDx = -dx;
		else
			tempDx = dx;

		if(dy < 0)
			tempDy = -dy;
		else
			tempDy = dy;

		double angleVal;
		if( tempDx != 0)
			angleVal = Math.atan2(tempDy,tempDx);
		else
			angleVal = Math.PI/2;

		double minThreshold = (Math.PI)/32;
		double maxThreshold = (15*Math.PI)/32;

		//Assume to be a 45 degree angle collision
		if(angleVal >= minThreshold && angleVal <= maxThreshold ){

			if((dx > 0 && dy < 0)
				|| dx < 0 && dy > 0){

				double temp = this.deltaX;
				this.deltaX = this.deltaY;
				this.deltaY = temp;
			}
			//the normal vector have the same signs
			else{
				double temp = this.deltaX;
				this.deltaX = -this.deltaY;
				this.deltaY = -temp;
			}
		}
		//Normal vector leaning toward vertical
		else if(angleVal > maxThreshold && angleVal <= Math.PI/2){
			//Assume vertical, and reset dx to 0
			dx = 0;
		}
		else if(angleVal >= 0 && angleVal < minThreshold){
			//Assume horizontal, reset dy to 0
			dy = 0;
		}

		//Assume vertical or horizontal collision
		{
			//If the x component for the normal vector is positive
			if(dx > 0){

				//if the current deltaX is positive, make it negative
				if(this.deltaX > 0)
					this.deltaX = -this.deltaX;

			}
			else if(dx < 0){	//x component for the normal vector is negative

				//if the current delta is negative as well
				if(this.deltaX < 0)
					this.deltaX = -this.deltaX;

			}

			//If the x component for the normal vector is positive
			if(dy > 0){

				//if the current deltaX is positive, make it negative
				if(this.deltaY > 0)
					this.deltaY = -this.deltaY;

			}
			else if(dy < 0){	//x component for the normal vector is negative

				//if the current delta is negative as well
				if(this.deltaY < 0)
					this.deltaY = -this.deltaY;

			}
		}

		//Set new location
		this.nextX = this.currX + this.deltaX;
		if(this.nextX < 0){
			this.nextX = -1;
			if(this.deltaX < 0)
				this.deltaX = -this.deltaX;
		}
		this.nextY = this.currY + this.deltaY;
		if(this.nextY < 0){
			this.nextY = -1;
			if(this.deltaY < 0)
				this.deltaY = -this.deltaY;
		}

	}

	/**
	 * Pixel perfect check for overlap.
	 */
	private int pixelPerfectOverlap(Sprite _sprite, double thisX, double thisY) {

		if (this.transparencyBuffer == null)
			this.setUpTransparencyBuffer();
		if (_sprite.transparencyBuffer == null)
			_sprite.setUpTransparencyBuffer();

		//The width and height of the over lap
		double overlapWidth = 0;
		double overlapHeight = 0;

		double overlapX = 0;
		double overlapY = 0;

		//The larger of the two next X values
		if(thisX > _sprite.nextX)
			overlapX = thisX;
		else
			overlapX = _sprite.nextX;

		//The larger of the two next Y values
		if(thisY > _sprite.nextY)
			overlapY = thisY;
		else
			overlapY = _sprite.nextY;

		//this sprite's left side overlaps with the other sprite's right side
		if ( (thisX < _sprite.nextX + _sprite.width) && (thisX + this.width > _sprite.nextX + _sprite.width)){

			overlapWidth = _sprite.nextX + _sprite.width - thisX;

			//Check to see if the overlap area is greater than the width of either sprite
			if(overlapWidth >= this.width)
				overlapWidth = this.width - 1;

			if(overlapWidth >= _sprite.width)
				overlapWidth = _sprite.width - 1;

		}
		//this sprite's right side overlaps with the other sprite's left side
		else if ( (_sprite.nextX < thisX + this.width) && (thisX < _sprite.nextX)){

			overlapWidth = thisX + this.width - _sprite.nextX;

			//Check to see if the overlap area is greater than the width of either sprite
			if(overlapWidth >= this.width)
				overlapWidth = this.width - 1;

			if (overlapWidth >= _sprite.width)
				overlapWidth = _sprite.width -1;

		}
		//this sprite's x-values is completely within the other sprite in term of width
		else if( (thisX >= _sprite.nextX) && ( (thisX + this.width) <= (_sprite.nextX + _sprite.width) )){

			overlapWidth = this.width;

		}

		//this sprite's top overlaps with the other's bottom
		if((thisY < _sprite.nextY + _sprite.height) && (thisY + this.height > _sprite.nextY + _sprite.height)){

			overlapHeight = (_sprite.nextY + _sprite.height) - thisY;

			//Check to see if the overlap area is larger than the height of either sprite
			if(overlapHeight >= this.height)
				overlapHeight = this.height - 1;

			if(overlapHeight >= _sprite.height)
				overlapHeight = _sprite.height - 1;


		}
		//this sprite's bottom overlaps with the other's top
		else if((_sprite.nextY < thisY + this.height) && (thisY < _sprite.nextY)){

			overlapHeight = (thisY + this.height) - _sprite.nextY;

			//Check to see if the overlap area is larger than the height of either sprite
			if(overlapHeight >= this.height)
				overlapHeight = this.height - 1;

			if(overlapHeight >= _sprite.height)
				overlapHeight = _sprite.height - 1;

		}
		//this sprite's x-values is completely withing the other sprite in term of height
		else if((thisY >= _sprite.nextY) && ((thisY + height) <= (_sprite.nextY + _sprite.height)) ){

			overlapHeight = this.height;

		}

		//The value to offset in each individual sprite
		int thisOffsetX = 0, thisOffsetY = 0;
		int spriteOffsetX = 0, spriteOffsetY = 0;

		//Calculate the offset for this sprite
		if(thisX < overlapX)
			thisOffsetX = (int)(overlapX - thisX);
		else 	//Also include the case where the values thisX == overlapX
			thisOffsetX = (int)(thisX - overlapX);

		if(thisY < overlapY)
			thisOffsetY = (int)(overlapY - thisY);
		else 	//Also include the case where the values this.nextY == overlapY
			thisOffsetY = (int)(thisY - overlapY);

		//Calulate the offset for the other sprite
		if(_sprite.nextX < overlapX)
			spriteOffsetX = (int)(overlapX - _sprite.nextX);
		else 	//Also include the case where the values _sprite.nextX == overlapX
			spriteOffsetX = (int)(_sprite.nextX - overlapX);

		if(_sprite.nextY < overlapY)
			spriteOffsetY = (int)(overlapY - _sprite.nextY);
		else 	//Also include the case where the values _sprite.nextY == overlapY
			spriteOffsetY = (int)(_sprite.nextY - overlapY);

		//boolean collisionDetected = false;

		int ppCollisionCount = 0;

		//loop through the overlapped area to see if there is any pixel collision
		for(int x = 0; x < overlapWidth; ++x){

			for(int y = 0; y < overlapHeight; ++y){

				int [][] thisSprite = this.transparencyBuffer.get(this.currFrame);
				int [][] otherSprite = _sprite.transparencyBuffer.get(_sprite.currFrame);

				//there is a colision
				if( thisSprite[x+thisOffsetX][y+thisOffsetY] != 0 &&
					otherSprite[x+spriteOffsetX][y+spriteOffsetY] != 0){

					++ppCollisionCount;

				}

			}

		}

		return ppCollisionCount;

	}

	private void setUpTransparencyBuffer() {
		//HLOC: change to 2-D array
		transparencyBuffer = new Vector<int[][]>();
		for (int frame = 0; frame < numFrames; frame++) {
			transparencyBuffer.add(frame,
				gameImage.get(frame).getTransparencyBuffer());
		}
	}


	private boolean overlapsWith(Sprite _sprite) {
		// System.out.println(
		// "**this x: " + nextX + " to " + (nextX + width) +
		// " y: " + nextY + " to " + (nextY + height) +
		// " other x: " + _sprite.nextX + " to " + (_sprite.nextX +
		// _sprite.width) +
		// " y: " + _sprite.nextY + " to " + (_sprite.nextY + _sprite.height)
		// );
		if (this.nextX > _sprite.nextX + _sprite.width
				|| this.nextX + this.width < _sprite.nextX
				|| this.nextY > _sprite.nextY + _sprite.height
				|| this.nextY + this.height < _sprite.nextY)
			return false;
		else
			return true;
	}

	public String toString()
	{
		if (this == ucigame.TOPEDGE) return "<TOPEDGE>";
		if (this == ucigame.BOTTOMEDGE) return "<BOTTOMEDGE>";
		if (this == ucigame.LEFTEDGE) return "<LEFTEDGE>";
		if (this == ucigame.RIGHTEDGE) return "<RIGHTEDGE>";
		if (this == ucigame.PIXELPERFECT) return "<PIXELPERFECT>";
		return "<Sprite [" + width + "," + height + "]#" + (hashCode() % 10000) + ">";
	}
}
