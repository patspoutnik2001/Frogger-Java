
//cette classe va etre heritee par tout les voitures
public abstract class Enemy  {
	// abstract-> cette classe peut pas etre instancie
	private int pos_x;
	private int pos_y;

	public Enemy(int posX, int posY) {
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
