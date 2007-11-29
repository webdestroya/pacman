package code.uci.pacman.controllers.utilities;

import code.uci.pacman.objects.ControllableObject;


public abstract class ActorController<T, S extends ControllableObject> extends SpriteController<T, S> {
  public abstract void constructActors();  
}
