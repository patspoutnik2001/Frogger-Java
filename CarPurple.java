

public class CarPurple extends Enemy {
	private int DOT_SIZE = 15;

	private int direction =0; //0->vers la droite/1-> vers la gauche
	private int speed = 5;

	public CarPurple(int posX, int posY, int d) {
		super(posX, posY);
		direction = d;
	}

	public static String getPathToImage(){
		return "carPurple.png";
	}
	public String getType(){
		return "carPurple";	
	}
	public void triggerAction(Board board){
		board.decreaseLivesAmount(1);
	}
//la voiture ralenti quand le player ramasse les pieces
	public void moveCar(int playerX, int playerY, int borderSize, int coins){
		
		//move car
		if (direction==0) {//vers droite
			super.setPosX(super.getPosX()+speed/2);
		}else{//vers gauche
			super.setPosX(super.getPosX()-speed/2);
		} 
		changeSpeed(coins);
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
	private void changeSpeed(int coins){
		if (coins<1) {
			speed=20;
		}else if(coins<3){
			speed = 15;
		}else if(coins<5){
			speed = 10;
		}
	}
}
