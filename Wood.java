public class Wood extends FixedGameElement {
	public Wood(int posX, int posY) {
		super(posX, posY);
	}
	public static String getPathToImage(){
		return "wood.png";
	}
	public String getType(){
		return "wood";	
	}

	@Override
	public void triggerAction(Board board) {
	}
}
