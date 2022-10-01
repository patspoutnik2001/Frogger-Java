

public class Road extends Terrain {

	public Road(int posY) {
		super(posY);
	}

	public static String getPathToImage(){
		return "road.png";
	}
	public String getType(){
		return "road";	
	}
}
