package Enemies;
import FixedGameElements.Board;
import FixedGameElements.Terrain;

import java.util.ArrayList;

public class CarRed extends Enemy {
	private int DOT_SIZE = 15;
	private Terrain nextRoad;
	private Terrain previousRoad;
	private int speed = 3;

	public CarRed(int posX, int posY) {
		super(posX, posY);
	}

	public static String getPathToImage(){
		return "Textures/carRed.png";
	}
	public String getType(){
		return "carRed";	
	}
	public void triggerAction(Board board){
		board.decreaseLivesAmount(1);
	}
	//pas besoins de faire une methode qui verif si on depasse la map car la voiture se deplace en function de frogger
	//frogger va jamais sortir donc la voiture rouge aussi 

	public void followPlayer( ArrayList<Terrain> ter, int playerX, int playerY){
		if (super.getPosY() != playerY) { // si le player ne se trouve pas sur meme route
			nextRoad = ter.get((super.getPosY()/DOT_SIZE)+1);
			previousRoad = ter.get((super.getPosY()/DOT_SIZE)-1);
			//on regarde vers ou la voiture doit aller
			if (previousRoad.getType()=="road" && playerY< super.getPosY()) {// vers le bas
				super.setPosY(super.getPosY()-DOT_SIZE);
			}else if (nextRoad.getType()=="road" && playerY> super.getPosY()) {//vers le haut
				super.setPosY(super.getPosY()+DOT_SIZE);
			}
		}else{// il se trouve sur la meme route
			if (playerX>super.getPosX()) {// player se trouve a droite 
				super.setPosX(super.getPosX()+speed);
			}else if (playerX < super.getPosX()) {//player se trouve a gauche
				super.setPosX(super.getPosX()-speed);
			}
		}
	}

}
