public class Water extends Terrain {
	public Water(int posY) {
		super(posY);
	}
	public static String getPathToImage(){
		return "water.png";
	}
	public String getType(){
		return "water";	
	}
}
