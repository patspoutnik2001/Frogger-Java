public class Bush extends FixedGameElement {
	public Bush(int posX, int posY) {
		super(posX, posY);
	}
	public static String getPathToImage(){
		return "bush.png";
	}
	public String getType(){
		return "bush";	
	}

	@Override
	public void triggerAction(Board board) {
	}
}
