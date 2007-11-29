package code.uci.pacman.controllers;



import code.uci.pacman.controllers.utilities.ActorController;
import code.uci.pacman.game1.PacManGame;
import code.uci.pacman.objects.controllable.Ghost;
import code.uci.pacman.objects.controllable.PacMan;

//responsible for and controls ghosts
public class GhostController extends ActorController<String, Ghost> {

	public GhostController() {
		this.constructActors();
	}

	public Ghost getGhost(String name) {
	   return super.getObjectAt(name);
   }

	public void constructActors() {
	   //Ghost blinky = new Ghost(PacManGame.getPacImage("blinky.png"), 300, 300, );
	   //Ghost pinky = new Ghost("Blinky", 400, 400, ghostImages.getImage("pinky"));
	   //this.addObject("Blinky", blinky);
	   //this.addObject("Pinky", pinky);
	   //TODO add ghost construction
	}
}