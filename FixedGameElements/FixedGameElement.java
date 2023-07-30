package FixedGameElements;
//cette classe est heritee par tout les elements imobile dans le jeu
public abstract class FixedGameElement  {
	// abstract-> cette classe peut pas etre instancie
	private int pos_x;
	private int pos_y;

	public FixedGameElement(int posX, int posY) {
        pos_x=posX;
		pos_y=posY;
		
	}
    public int getPosX(){
		return pos_x;	
	}

	public int getPosY(){
		return pos_y;	
	}

	public void setPosX(int newPos){
		pos_x=newPos;
	}

	public void setPosY(int newPos){
		pos_y=newPos;
	}
	public abstract String getType();
	public abstract void triggerAction(Board board);
	 
}
