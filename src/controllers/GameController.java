package controllers;

import objects.stationary.Fruit;

//controls the action of the game
public class GameController {
	
	private static GameController gControl = new GameController();
	

	public static GameController getInstance() {
		return gControl;
	}


	public void fruitEaten(Fruit fruit) {
		// TODO Auto-generated method stub
	}
	public void pillEaten(Pill pill){
		
	}
	public void ghostEaten(Ghost ghost){
		
	}
	public void pacManEaten(PacMan pac){
		
	}
	public void powerPelletEaten(PowerPellet power){
		
	}
	
	public void canMove(ControllableObject c, Direction d){
		
	}

}
