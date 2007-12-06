package ucigame;

import java.util.HashMap;

public class SpriteAnimationController 
{
	public static final String DEFAULT_MODE = "default";
    private HashMap<String, SpriteAnimationMode> imageModes;
    private String currMode; //the current sprite mode
    
    public SpriteAnimationController() {
    	imageModes = new HashMap<String, SpriteAnimationMode>(); //create the hash
    	currMode = DEFAULT_MODE; //set the current mode to default
    	getOrAddMode(DEFAULT_MODE); //add the default mode automatically
    }
    
    //adds a new mode to the sprite
    public SpriteAnimationMode addNewMode(String mode) {
    	return getOrAddMode(mode);
    }
    
    //switches to new mode as specified. creates mode if necessary
    public void switchToMode(String mode) {
    	currMode = mode;
    }
	
	private SpriteAnimationMode getOrAddMode(String mode) {
		if (imageModes.keySet().contains(mode) == false) { //if the mode does not exist
    		imageModes.put(mode, new SpriteAnimationMode(mode)); //adds new sprite mode for this name
    	}
		
		return imageModes.get(mode); //retrieve the images
	}

	public SpriteAnimationMode getCurrentMode() {
		return imageModes.get(currMode);
	}

	public SpriteAnimationMode getMode(String mode) {
		return imageModes.get(currMode);
	}

	public boolean hasMode(String mode) {
		return imageModes.keySet().contains(mode);
	}
}
