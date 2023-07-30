package Enemies;
import FixedGameElements.Board;
import FixedGameElements.FixedGameElement;

// on herite de FixedGameElement pour une meilleur manipulations dans board
// on pourrait faire une classe a part
public class Insect extends FixedGameElement {
	//le type est ajouter pour ne pas faire une redendence de code
	private static int typeInsect;
	public Insect(int posX, int posY,int _type) {
		super(posX, posY);
		typeInsect=_type;
	}
	public static String getPathToImage(int i){
		if (i==0) {
			return "Textures/insect1.png";
		}else if (i==1) {
			return "Textures/insect2.png";
		} else if(i ==2) {
			return "Textures/insect3.png";
		}
		return "insect1.png";
	}
	
	public String getType(int i){
		if (i==0) {
			return "insect1";
		}else if (i==1) {
			return "insect2";
		} else if(i ==2) {
			return "insect3";
		}
		return "insect1";
	}
	
	public int getInsectType(){
		return typeInsect;
	}
	public void triggerAction(Board board){
		if (typeInsect==0) {
			board.incScore(2);
		}else if (typeInsect==1) {
			board.incScore(4);
		} else if(typeInsect==2) {
			board.incScore(6);
		}
	}
	//on doit mettre cette methode car dans FixedGameElement, elle est abstraite. Meme si on la utilise jamais
	//c'est pour pouvoir utiliser les insects comme les FixedGameElement's
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
