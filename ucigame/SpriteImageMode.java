package ucigame;

import java.util.Vector;

public class SpriteImageMode
{
	private Vector<Image> images;
	private String name;
	private int currFrame;
	private int height;
	private int width;

	public SpriteImageMode(String _name) {
		images = new Vector<Image>();
		name = _name;
		this.currFrame = 0;
		this.height = -1;
		this.width = -1;
	}
	
	public SpriteImageMode(String _name, int width, int height) {
		images = new Vector<Image>();
		name = _name;
		this.currFrame = 0;
		this.width = width;
		this.height = height;
	}

	public void addImage(Image img) {
		images.add(img);
	}

	public void setCurrFrame(int currFrame) {
		this.currFrame = currFrame;
	}

	public int getCurrFrame() {
		return currFrame;
	}
	
	public Image getCurrImage() {
		return images.get(currFrame);
	}

	public int getNumFrames() {
		return images.size();
	}
	
	public void clearFrames() {
		images.clear();
		currFrame = 0;
	}
	
	public String getName() {
		return name;
	}

	public Image getFrameImage(int frame) {
		return images.get(frame);
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}
}
