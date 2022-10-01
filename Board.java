import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Board extends JPanel implements ActionListener {
	//les variables qui vont pas changer
	private final int B_WIDTH = 450;
	private final int B_HEIGHT = 450;
	private final int DOT_SIZE = 15;
	private final int RAND_POS = 29;
	private final int DELAY = 70;
	//position du frogger
	private int pos_x,pos_y ;
	//position du trou ou doit aller frogger apres avoir racolter tout les pieces
	private int hole_x=B_WIDTH/2,hole_y=0;
	//position de depart du frogger
	private int spawn_x=B_WIDTH/2,spawn_y=B_HEIGHT-DOT_SIZE;
	// cordonne du vide ou les elements vont spawn
	private int void_x=-1*B_WIDTH, void_y=-1*B_HEIGHT; 

	//les counters
	private int coinCounter, insectCounter, pillCounter;
	//les listes
	private ArrayList<FixedGameElement> fixedGameElementList;
	private HashMap<String, ImageIcon> fixedGameElementImageMap;
	private HashMap<String, ImageIcon> terrainsImageMap;
	private HashMap<String, ImageIcon> enemysImageMap;
	private ArrayList<Terrain> terrains;//30 lines de terrains
	private ArrayList<Enemy> enemys;
	//dans le fichier levels.txt les niveaux sont hardcode dans un systemes comme ca:
	// [numbers of roads top/down, grass, water, number of insects, number of coins, number of cars and wood]

	//la structure de niveau en cours
	private Integer[] currentLevel;
	//la liste des structures niveaux
	private ArrayList<Integer[]> levels;
	
	private boolean inGame = true;
	//est static car on le utilise dans une methode static
	private static boolean invincible = false;

	private Timer timer;
	//les images d'elements propore au board 
	private Image frog;
	private Image frogGod;
	private Image hole;

	private int score;
	private int highestScore; 
	private int lives;
	private int level =0;
	private int bushes=25;
	//functionnalite de new game +
	private int multiplier=1;

	public Board() {
        
		initBoard();
		score=0;
		lives = 3;
		//on la mit dans board sinon a chaque nouvelles parties le frogger avance 2x plus vite
		addKeyListener(new TAdapter());
	}
    
	private void initBoard() {
		setBackground(Color.black);
	    setFocusable(true);
	      
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
		initMap(level);
		
	}

	private void loadImages() {
		fixedGameElementImageMap = new HashMap<String, ImageIcon>();
		terrainsImageMap = new HashMap<String, ImageIcon>();
		enemysImageMap = new HashMap<String, ImageIcon>();
	
		ImageIcon iig = new ImageIcon(Grass.getPathToImage());
		terrainsImageMap.put("grass", iig);	
		
		ImageIcon iir = new ImageIcon(Road.getPathToImage());
		terrainsImageMap.put("road", iir);		

		ImageIcon iiw = new ImageIcon(Water.getPathToImage());
		terrainsImageMap.put("water", iiw);	

	    ImageIcon iic = new ImageIcon(Coin.getPathToImage());
		fixedGameElementImageMap.put("coin", iic);

		ImageIcon iiwo = new ImageIcon(Wood.getPathToImage());
		fixedGameElementImageMap.put("wood", iiwo);	
		
		ImageIcon iib = new ImageIcon(Bush.getPathToImage());
		fixedGameElementImageMap.put("bush", iib);
	
		ImageIcon iii1 = new ImageIcon(Insect.getPathToImage(0));
		fixedGameElementImageMap.put("insect1", iii1);
		ImageIcon iii2 = new ImageIcon(Insect.getPathToImage(1));
		fixedGameElementImageMap.put("insect2", iii2);
		ImageIcon iii3 = new ImageIcon(Insect.getPathToImage(2));
		fixedGameElementImageMap.put("insect3", iii3);

        	ImageIcon iif = new ImageIcon("frog.png");
        	frog = iif.getImage();
			ImageIcon iifg = new ImageIcon("frogGod.png");
        	frogGod = iifg.getImage();
			ImageIcon iih = new ImageIcon("hole.png");
			hole = iih.getImage();
			

		ImageIcon iip =new ImageIcon(Pill.getPathToImage());
		fixedGameElementImageMap.put("pill", iip);

		ImageIcon iirc = new ImageIcon(CarRed.getPathToImage());
		enemysImageMap.put("carRed", iirc);
		
		ImageIcon iibc = new ImageIcon(CarBlue.getPathToImage());
		enemysImageMap.put("carBlue", iibc);

		ImageIcon iioc = new ImageIcon(CarOrange.getPathToImage());
		enemysImageMap.put("carOrange", iioc);

		ImageIcon iipc = new ImageIcon(CarPurple.getPathToImage());
		enemysImageMap.put("carPurple", iipc);

		

	}

	private void initGame() {  
		
		pos_x=spawn_x;
		pos_y=spawn_y;
		
		pillCounter=1;
		fixedGameElementList= new ArrayList<FixedGameElement>();
		terrains = new ArrayList<Terrain>();
		enemys = new ArrayList<Enemy>();
		levels = new ArrayList<Integer[]>();

		decodeMap();
		
        timer = new Timer(DELAY, this);
        timer.start();

		
	}

	@Override
	public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        doDrawing(g);
	}
//dessine tout les elemets sur la carte
	private void doDrawing(Graphics g) {
    	if (inGame) {
			
			for(Terrain ter : terrains){
				g.drawImage(terrainsImageMap.get(ter.getType()).getImage(), 0, ter.getPosY(), this);
			}
			for(FixedGameElement elem: fixedGameElementList){
				if (elem instanceof Insect) {
					Insect myi = (Insect)elem;
					g.drawImage(fixedGameElementImageMap.get(myi.getType(myi.getInsectType())).getImage(), myi.getPosX(), myi.getPosY(), this);
				}else{
					g.drawImage(fixedGameElementImageMap.get(elem.getType()).getImage(), elem.getPosX(), elem.getPosY(), this);
				}
				
			}
			for(Enemy elem: enemys){
				g.drawImage(enemysImageMap.get(elem.getType()).getImage(), elem.getPosX(), elem.getPosY(), this);
			}
			g.drawImage(hole, hole_x, hole_y, this);
			//si on est invisible
			if (invincible) {
				g.drawImage(frogGod, pos_x, pos_y, this);
			}else{
				g.drawImage(frog, pos_x, pos_y, this);
			}
			
			drawUI(g);

        	Toolkit.getDefaultToolkit().sync();
        	if (lives<=0) {
        		inGame=false;
        	}

        	} else {

        	gameOver(g);
        	}        
    	}
//methode de creation de la map(back-end)
	private void initMap(int lvl){
		int currentTerrain=0;
		
		currentLevel = levels.get(lvl);
		coinCounter=currentLevel[5];
		insectCounter=currentLevel[4];

		//les 2 premiers lines
		terrains.add(new Grass(DOT_SIZE*currentTerrain));
		currentTerrain++;
		terrains.add(new Grass(DOT_SIZE*currentTerrain));
		currentTerrain++;
		//le premier segement des routes
		for(int i = 0 ; i<currentLevel[0]; i++){
			terrains.add(new Road(DOT_SIZE*currentTerrain));
			currentTerrain++;
		}
		//les bandes aux milieux
		if (currentLevel[2]!=0) {
			for(int i = 0 ; i<currentLevel[2]; i++){
				terrains.add(new Grass(DOT_SIZE*currentTerrain));
				currentTerrain++;
			}
		}
		if (currentLevel[3]!=0) {
			for(int i = 0 ; i<currentLevel[3]; i++){
				terrains.add(new Water(DOT_SIZE*currentTerrain));
				currentTerrain++;
			}
			for (int i =0;i<currentLevel[7] ;i++ ) {
				fixedGameElementList.add(new Wood(getRandomCoordinate(), getRandomOnRoadAndWaterCoordinate(1)));
			}
		}
		//on place les arbustres
		for (int i = 0; i < bushes; i++) {
			fixedGameElementList.add(new Bush(getRandomCoordinate(), getRandomOnRoadAndWaterCoordinate(2)));
		}
		//le 2eme segement des routes
		for(int i = 0 ; i<currentLevel[1] ; i++){
			terrains.add(new Road(DOT_SIZE*currentTerrain));
			currentTerrain++;
		}
		//les deux dernies lignes
		terrains.add(new Grass(DOT_SIZE*currentTerrain));
		currentTerrain++;
		terrains.add(new Grass(DOT_SIZE*currentTerrain));
		currentTerrain++;
		//game elements
		for(int i = 0 ; i<coinCounter ; i++){
			fixedGameElementList.add(new Coin(getRandomCoordinate(), getRandomOnRoadAndWaterCoordinate(0)));
		}
		Random random = new Random();
		int typeInsect;
		for(int i = 0 ; i<insectCounter ; i++){
			typeInsect = random.nextInt(3 - 0) + 0;
			fixedGameElementList.add(new Insect(getRandomCoordinate(), getRandomCoordinate(),typeInsect));
		}
		for(int i = 0 ; i<pillCounter ; i++){
			fixedGameElementList.add(new Pill(getRandomCoordinate(), getRandomCoordinate()));
		}
		for (int i = 0 ; i< currentLevel[6] ; i++) {
			int randDir = random.nextInt(2 - 0) + 0;
			enemys.add(new CarRed(getRandomCoordinate(),getRandomOnRoadAndWaterCoordinate(0)));
			enemys.add(new CarBlue(getRandomCoordinate(),getRandomOnRoadAndWaterCoordinate(0),randDir));
			enemys.add(new CarOrange(getRandomCoordinate(),getRandomOnRoadAndWaterCoordinate(0),randDir));
			enemys.add(new CarPurple(getRandomCoordinate(),getRandomOnRoadAndWaterCoordinate(0),randDir));
		}
		
		
	}
//methode qui decode le fichier avec les structures des niveaux
	private void decodeMap(){
		File file=new File("levels.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
    		String line;
    		while ((line = br.readLine()) != null) {
       			String []items = line.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\s","").split(",");
       			Integer [] level = new Integer [items.length];
       			for (int i = 0;i<items.length ;i++ ) {
       				try{
       					level[i] = Integer.parseInt(items[i]);
       				}catch(NumberFormatException nfe){
       					System.out.println("error parse int");
       				};
       			}
       			levels.add(level);
    		}
		}catch (IOException ex) {
    		ex.printStackTrace();
   		}
	}
//methode qui affiche tout les textes sur ecrans
	private void drawUI(Graphics g){
		String scoreText = "Score: "+score;
		String livesText = "Lives: "+lives;
		Font small = new Font("Helvetica", Font.BOLD, 14);
		//lis le highscore
		File file=new File("score.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
    		String line;
    		while ((line = br.readLine()) != null) {
       			highestScore = Integer.parseInt(line);
    		}
		}catch (IOException ex) {
    		ex.printStackTrace();
   		}
		String hScore="HighScore: "+highestScore;

	    g.setColor(Color.white);
	    g.setFont(small);
		//ici c'est pas des constantes magiques! c'est les positions des textes sur l'ecran
	    g.drawString(scoreText,0,14);
		g.drawString(hScore,0,28);
	    g.drawString(livesText,B_WIDTH-65,14);
	}

//methode qui affiche le texte gameover
	private void gameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}
//regarde les collisions avec les elemets fixes
	private void checkFixedGameElementCollision() {

		for(FixedGameElement elem: fixedGameElementList){ // on a ajouter la codition != wood et bush, pour pas effacer le element wood de la map
			if ((pos_x == elem.getPosX()) && (pos_y == elem.getPosY()) && ((elem.getType()!="wood") && (elem.getType()!="bush"))) {
	            elem.setPosX(void_x);
				elem.setPosY(void_y);
				
				elem.triggerAction(this);//this; c'est le tableu du jeu/board
					
	        	}
		}
	}
//methodes pour modifier le score et points de vies
	public void incScore(int valueToIncrease){
		score+= valueToIncrease*multiplier;
	}
	public void decreaseCoinAmount(){
		coinCounter-= 1;
	}
	public void decreaseLivesAmount(int value){
		if (!invincible) {
			lives-=value;
		}
			
	}
//le player est invincible
	public void godMode(){//refaire
		
		invincible= true;
		//wait les 5 secs
		Pill.godMode();
		
	}
	public static void setInvincible(boolean i){
		invincible=i;
	}

	
//check collision sur les bords de la map et le player 
	private void checkCollision() {

	        if (pos_y >= B_HEIGHT) {//bas
	            pos_y = B_HEIGHT - DOT_SIZE;
	        }

	        if (pos_y < 0) {//haut
	            pos_y=0;
	        }

	        if (pos_x >= B_WIDTH) {//droite
	        	pos_x= B_WIDTH - DOT_SIZE;
	        }

	        if (pos_x < 0) {//gauche
	        	pos_x= 0;	        
	        }
	}

//coord random sur toute la carte 
	private int getRandomCoordinate() { 

	        int r = (int) (Math.random() * RAND_POS);
	        return ((r * DOT_SIZE));
	}
//coord random sur les routes, l'eau ou la terre. On a ajouter "choice" pour pas dupliquer le code
	private int getRandomOnRoadAndWaterCoordinate(int choice) {
			int r = (int) (Math.random() * terrains.size());
			if (choice==0) {//pour une route
				while(terrains.get(r).getType()!="road"){
					r = (int) (Math.random() * terrains.size());
				}
				return ((r * DOT_SIZE));
			}else if (choice == 1) { //pour la riviere
				while(terrains.get(r).getType()!="water"){
					r = (int) (Math.random() * terrains.size());
				}
				return ((r * DOT_SIZE));
			}else if(choice == 2){
				while(terrains.get(r).getType()!="grass" && r!= hole_x){
					r = (int) (Math.random() * terrains.size());
				}
				return ((r * DOT_SIZE));
			}
			return 0;// j'ai du mettre le return 0 sinon le code functionne pas
	}
//cette methode check tout les collisions et fait bouger les voitures
	@Override
	public void actionPerformed(ActionEvent e) {

	        if (inGame) {
	        	checkFixedGameElementCollision();
	        	checkEnemyCollision();
	        	checkCollision();
	        	for (Enemy car : enemys) { // mettre dans une methode?
	        		if (car instanceof CarRed) {
	        			CarRed myc = (CarRed)car;
    					myc.followPlayer(terrains, pos_x,pos_y);
	        		}else if (car instanceof CarBlue){
	        			CarBlue myc = (CarBlue)car;
	        			myc.moveCar(pos_x,pos_y,B_WIDTH);
	        		}else if ( car instanceof CarOrange){
	        			CarOrange myc = (CarOrange)car;
	        			myc.moveCar(pos_x,pos_y,B_WIDTH);
	        		}else if ( car instanceof CarPurple){
	        			CarPurple myc = (CarPurple)car;
	        			myc.moveCar(pos_x,pos_y,B_WIDTH,coinCounter);
	        		}
    				
	    		}
	        	
        	}
	        repaint();
	}
//regarde les collisons avec les voitures
	private void checkEnemyCollision(){
		for(Enemy elem: enemys){
																							//++ on a ajouter une conditions si on est pas invisible!
			if ((pos_x/DOT_SIZE == elem.getPosX()/DOT_SIZE) && (pos_y/DOT_SIZE == elem.getPosY()/DOT_SIZE) && (!invincible)) {
				elem.triggerAction(this);//this c'est le tableau jeu/board
				pos_x=spawn_x;
				pos_y= spawn_y;
	        }
		}
	}
//mouevment du frogger
	private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
        	
            int key = e.getKeyCode();
			int speedFrog;
			int direction=-1;//->0 gauche, 1 droite, 2 haut et 3 en bas
			//si on est invinsible alors la vitesse est div par 3
			if (invincible) {
				speedFrog = DOT_SIZE/3;
			}else{
				speedFrog = DOT_SIZE;
			}

            if (key == KeyEvent.VK_LEFT) { // gauche 
                pos_x -= speedFrog;
				direction =0;
            }

            if (key == KeyEvent.VK_RIGHT) { // droite
                pos_x += speedFrog;
				direction =1;
            }

            if (key == KeyEvent.VK_UP) { //haut
               pos_y -= speedFrog;
			   direction =2;
            }

            if (key == KeyEvent.VK_DOWN) { //bas
                pos_y += speedFrog;
				direction =3;
            }
            //check si on est pas sur une riviere
            checkWater();
			//check si on est sur le trou pour finir le niveau
			checkHole();
			// on regarde si on est sur  un bush
			checkBush(speedFrog,direction);
        }
    }
//cette methode regarde a chaque deplacement du froggeur si il est pas sur un bush, si il est alors on le recule en function de quel direction il viens
	private void checkBush(int speed, int d){
		for (FixedGameElement item : fixedGameElementList) {
			if (item.getType()=="bush" && item.getPosX()==pos_x && item.getPosY()==pos_y) {
				//touts ces if's sont la meme chose que deplacement du frogger mais a l'envers 
				if(d==0){
				pos_x+=speed;
				}if (d==1) {
					pos_x-=speed;
				}if (d==2) {
					pos_y+=speed;
				}if (d==3) {
					pos_y-=speed;
				}
				return;
			}
		}
	}
    private void checkWater(){
    	if (terrains.get(pos_y/DOT_SIZE).getType()=="water" && isOnWood()) {
    		lives--;
    		//teleport to start point++
    		pos_x=spawn_x;
			pos_y= spawn_y;
    	}
    }
	private void checkHole(){
		if ((pos_x==hole_x) && (pos_y==hole_y) && (coinCounter==0)) {
			level++;
			
			if (level>=3) {//new game + commence ici
				//save score
				save();
				score=0;
				level=0;
				lives = 3;
				multiplier++;
				//initBoard();
			}
			initBoard();
		}
	}
	//on sauve le score
	private void save(){
		
		if (score>highestScore) {
			try {
				FileWriter myWriter = new FileWriter("score.txt");
				myWriter.write(""+score);
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
    private boolean isOnWood(){
    	for(FixedGameElement elem: fixedGameElementList){ 
			if ((pos_x == elem.getPosX()) && (pos_y == elem.getPosY()) && (elem.getType()=="wood")) {
	           return false;
	        }
		}
    	return true;
    }
}
