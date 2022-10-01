import java.util.Random;

public class CarOrange extends Enemy {
	private int DOT_SIZE = 15;
	Random random = new Random();
	private int direction =0; //0->vers la droite/1-> vers la gauche
	private int speed = DOT_SIZE;

	public CarOrange(int posX, int posY, int d) {
		super(posX, posY);
		direction = d;
	}

	public static String getPathToImage(){
		return "carOrange.png";
	}
	public String getType(){
		return "carOrange";	
	}
	public void triggerAction(Board board){
		board.decreaseLivesAmount(1);
	}
//la voiture change sa vitesse au hasard
	public void moveCar(int playerX, int playerY, int borderSize){
		
		//move car
		if (direction==0) {//vers droite
			super.setPosX(super.getPosX()+speed/2);
		}else{//vers gauche
			super.setPosX(super.getPosX()-speed/2);
		}
		changeSpeed();
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
	private void changeSpeed(){
		int rndSpeed = random.nextInt(35 - 5) + 5;
		speed = rndSpeed;
	}
}
