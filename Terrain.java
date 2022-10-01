

public abstract class Terrain  {
	private int pos_y;

	public Terrain(int posY) {
        	pos_y=posY;
	}
    public int getPosY(){
		return pos_y;	
	}

	public abstract String getType();
	 
}
