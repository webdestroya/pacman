package ucigame;

import java.util.HashMap;

public class SpriteImagesHandler 
{
	public static final String DEFAULT_MODE = "default";
    private HashMap<String, SpriteImageMode> imageModes;
    private String currMode; //the current sprite mode
    
    public SpriteImagesHandler() {
    	imageModes = new HashMap<String, SpriteImageMode>(); //create the hash
    	currMode = DEFAULT_MODE; //set the current mode to default
    	getOrAddMode(DEFAULT_MODE); //add the default mode automatically
    }
    
    //adds a new mode to the sprite
    public SpriteImageMode addNewMode(String mode) {
    	return getOrAddMode(mode);
    }
    
    //switches to new mode as specified. creates mode if necessary
    public void switchToMode(String mode) {
    	currMode = mode;
    }
	
	private SpriteImageMode getOrAddMode(String mode) {
		if (imageModes.keySet().contains(mode) == false) { //if the mode does not exist
    		imageModes.put(mode, new SpriteImageMode(mode)); //adds new sprite mode for this name
    	}
		
		return imageModes.get(mode); //retrieve the images
	}

	public SpriteImageMode getCurrentMode() {
		return imageModes.get(currMode);
	}

	public SpriteImageMode getMode(String mode) {
		return imageModes.get(currMode);
	}
}
