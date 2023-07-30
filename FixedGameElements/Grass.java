package FixedGameElements;

public class Grass extends Terrain {
	public Grass(int posY) {
		super(posY);
	}
	public static String getPathToImage(){
		return "Textures/grass.png";
	}
	public String getType(){
		return "grass";	
	}
}
