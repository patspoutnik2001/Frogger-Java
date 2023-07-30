package FixedGameElements;
public class Wood extends FixedGameElement {
	public Wood(int posX, int posY) {
		super(posX, posY);
	}
	public static String getPathToImage(){
		return "Textures/wood.png";
	}
	public String getType(){
		return "wood";	
	}

	@Override
	public void triggerAction(Board board) {
	}
}
