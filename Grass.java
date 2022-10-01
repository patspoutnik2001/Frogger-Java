public class Grass extends Terrain {
	public Grass(int posY) {
		super(posY);
	}
	public static String getPathToImage(){
		return "grass.png";
	}
	public String getType(){
		return "grass";	
	}
}
