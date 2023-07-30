package Enemies;
import FixedGameElements.Board;

public class CarBlue extends Enemy {
	private int DOT_SIZE = 15;

	private int direction =0; //0->vers la droite/1-> vers la gauche
	private int slowerSpeed =DOT_SIZE/2;

	public CarBlue(int posX, int posY, int d) {
		super(posX, posY);
		direction = d;
	}

	public static String getPathToImage(){
		return "Textures/carBlue.png";
	}
	public String getType(){
		return "carBlue";	
	}
	public void triggerAction(Board board){
		board.decreaseLivesAmount(1);
	}
//la voiture ralenti quand le player est sur la meme route
	public void moveCar(int playerX, int playerY, int borderSize){
		if (super.getPosY() == playerY) { // il se trouve sur la meme route, il ralenti
			slowerSpeed = DOT_SIZE/4;
		}
		//move car
		if (direction==0) {//vers droite
			super.setPosX(super.getPosX()+slowerSpeed);
		}else{//vers gauche
			super.setPosX(super.getPosX()-slowerSpeed);
		}
		checkBorders(borderSize);
	}
	private void checkBorders(int borderSize){

        if (super.getPosX() >= borderSize) {//droite
        	super.setPosX(0);
        }

        if (super.getPosX() < 0) {//gauche
        	super.setPosX(borderSize - DOT_SIZE);	        
        }
	}
}
