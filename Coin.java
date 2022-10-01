public class Coin extends FixedGameElement {
	public Coin(int posX, int posY) {
		super(posX, posY);
	}
	public static String getPathToImage(){
		return "coin.png";
	}
	public String getType(){
		return "coin";	
	}
	public void triggerAction(Board board){
		board.incScore(1);
		board.decreaseCoinAmount();
	}
}
