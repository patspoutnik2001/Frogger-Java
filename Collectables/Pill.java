package Collectables;

import FixedGameElements.Board;
import FixedGameElements.FixedGameElement;

public class Pill extends FixedGameElement {
	//la duree de god mode
	private final static int GOD_MODE_DELAY= 5000;
	public Pill(int posX, int posY) {
		super(posX, posY);
	}

	public static String getPathToImage(){
		return "Textures/pill.png";
	}
	public String getType(){
		return "pill";	
	}
	public void triggerAction(Board board){
		board.godMode();
	}
	//le frogger est invinsible
	public static void godMode(){
		//wait 5 secs
		new java.util.Timer().schedule(new java.util.TimerTask(){
			@Override
			public void run(){
				//apres le delay ce bout de code est execute
				Board.setInvincible(false);
			}
		},GOD_MODE_DELAY);
	}
}
