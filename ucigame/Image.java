// Image.java

package ucigame;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

/**
 * Class for images in ucigame.
 *
 * @author Dan Frost
 */
public class Image
{
	private Ucigame ucigame;
	BufferedImage buffImage;
	int iwidth, iheight;

	/**
	 * Create new image.
	 *
	 * @param _i the image to be loaded
	 * @param _u the ucigame object
	 */
	Image(java.awt.Image _i, Ucigame _u)
	{
		ucigame = _u; 
		buffImage = toBufferedImage(_i);
		iwidth = buffImage.getWidth(ucigame);
		iheight = buffImage.getHeight(ucigame);
	}

	/**
	 * Draws the image so that its upper left hand corner is at the specified position,
	 * relative to the upper left hand corner of the canvas. Can only be called inside the
	 * game class's draw() method.
	 *
	 * @param _x the x value of drawing position
	 * @param _y the y value of drawing position
	 */
	public void draw (int _x, int _y)
	{
		draw(AffineTransform.getTranslateInstance(_x, _y));
	}

	/**
	 * Draw the image.
	 *
	 * @param _Tx
	 */
	void draw(AffineTransform _Tx)   // package access only
	{
		if (ucigame.offG != null)
		{
			ucigame.offG.drawImage(buffImage, _Tx, null);
		}
		else
			Ucigame.logError("UImage.draw() used outside of draw()");
	}

	/**
	 * This method return the width of the image.
	 *
	 * @return the width of the image
	 */
	public int width() { return iwidth; }

	/**
	 * This method return the height of the image.
	 *
	 * @return the height of the image
	 */
	public int height() { return iheight; }

	/**
	 * Get the image.
	 *
	 * @return the image associated with this object
	 */
	BufferedImage getBufferedImage() { return buffImage; }

	/**
	 * This methods set the image's transparent color to the specied shade.
	 *
	 * @param _c the shade value
	 */
	void transparent(int _c)
	{
		if (0 <= _c && _c <= 255)
			transparent(_c, _c, _c);
		else
			Ucigame.logError("Image.transparent(" + _c + ") called, " +
							 "value is invalid.");
	}

	/**
	 * This methods set the image's transparent color to the specied shade.
	 *
	 * @param _r the red value
	 * @param _g the green value
	 * @param _b the blue value
	 */
	void transparent(int _r, int _g, int _b)
	{
		int transparentColor = 0;
		if (0 <= _r && _r <= 255 &&
		    0 <= _g && _g <= 255 &&
		    0 <= _b && _b <= 255)
		    transparentColor = (_r << 16) | (_g << 8) | _b;
		else
			return;
		BufferedImage newimage =
			new BufferedImage(iwidth, iheight, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = new int[iwidth * iheight];
		//System.err.println("image: " + image);
		//System.err.println("image.getRGB(0, 0, " + iwidth +
		//		", " + iheight + " , " + pixels + " , 0, " + iwidth);
		buffImage.getRGB(0, 0, iwidth, iheight, pixels, 0, iwidth);
		for (int x=0; x<iwidth; x++)
		{
			for (int y=0; y<iheight; y++)
			{
				int oldPixel = pixels[x + (y*iwidth)];
				if ((oldPixel & 0x00FFFFFF) == (transparentColor & 0x00FFFFFF))
					oldPixel = oldPixel & 0x00FFFFFF;
				newimage.setRGB(x, y, oldPixel);
			}
		}
		buffImage = newimage;
	}

	/**
	 * This method returns a buffered image with the contents of an image.
	 *
	 * @param image the image
	 */
	// This method returns a buffered image with the contents of an image
	// from http://javaalmanac.com/egs/java.awt.image/Image2Buf.html?l=rel
	private BufferedImage toBufferedImage(java.awt.Image _image) {
		if (_image instanceof BufferedImage) {
			return (BufferedImage)_image;
		}

		// This code ensures that all the pixels in the image are loaded
		_image = new ImageIcon(_image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent Pixels
		//boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the screen
		BufferedImage bimage = null;
		if (bimage == null) {
			// I'm making the images always have alpha
			int type = BufferedImage.TYPE_INT_ARGB;
			bimage = new BufferedImage(_image.getWidth(null), _image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(_image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	/**
	 * This method returns true if the specified image has transparent pixels.
	 *
	 * @param image the image in consideration
	 *
	 * @return the boolean to indicate transparent pixels
	 */
	// This method returns true if the specified image has transparent pixels
	private boolean hasAlpha(java.awt.Image _image) {
		// If buffered image, the color model is readily available
		if (_image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage)_image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(_image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}

	/**
	 * returns a int[][] with one char for each pixel, containing
	 * the alpha value (0x00 = transparent, otherwise non-transparent)
	 *
	 * @return the char array of representing the pixel
	 */
	int[][] getTransparencyBuffer()
	{
		
		int [][] buffer = new int[iwidth][iheight];
		int[] pixels = new int[iwidth * iheight];
		buffImage.getRGB(0, 0, iwidth, iheight, pixels, 0, iwidth);
		
		//HLOC: change to 2-d array
		for(int x=0; x<iwidth; x++){
			 	
			for (int y=0; y<iheight; y++){
				buffer[x][y] = (pixels[x+(y*iwidth)] >> 24);
			}
			
		}
		
		return buffer;

	}
}

