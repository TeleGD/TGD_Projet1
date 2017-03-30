package fr.game;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Music;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.characters.BasicPlayer;
import fr.characters.Bat;
import fr.characters.Player;
import fr.characters.enemies.BasicEnnemy;
import fr.characters.enemies.Enemy1;
import fr.characters.enemies.Ennemy;
import fr.characters.enemies.EnnemyShooter;
import fr.decor.*;
import fr.projectiles.Projectile;
import fr.bonus.Bonus;
import fr.bonus.GunBonus;
import fr.bonus.LevelEnd;
import fr.bonus.BatBonus;

public class World extends BasicGameState {

	private static final String REPERTOIRE_NIVEAU = "levels";
	public static int ID = 0;
	private static Player Nico;
	private static ArrayList<Plateform> plateforms = null;
	private static ArrayList<Ennemy> enemies = null;
	private static ArrayList<Projectile> projectiles = null;
	private static ArrayList<Bonus> bonuss = null;
	public static StateBasedGame game;
	private static int score; //entier corespondant au score
	private static Decor decor;
	public static Music Mbackground;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		game = arg1;
	}

	

	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		reset();
		Mbackground = new Music("music/oui.ogg");
		Mbackground.loop();
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		decor.render(arg0,arg1,arg2);
		
		for(Bonus b : bonuss)
		{
			b.render(arg0, arg1, arg2);
		}
		for (int i=0; i<plateforms.size();i++){
			plateforms.get(i).render(arg0, arg1, arg2);
			
		}
		for (int i=0; i<enemies.size();i++){
			enemies.get(i).render(arg0, arg1, arg2);
		}
		for(Projectile p : projectiles){
			p.render(arg0, arg1, arg2);
		}
		Nico.render(arg0, arg1, arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Mbackground.getPosition();
		
		Nico.update(arg0, arg1, arg2);
		decor.update(arg0,arg1,arg2);
		for (int i=0; i<plateforms.size();i++){
			plateforms.get(i).update(arg0, arg1, arg2);
		}
		int i = 0;
		while (i<enemies.size()){
			enemies.get(i).update(arg0, arg1, arg2);
			if (enemies.get(i).isDestructed()){
				Nico.addScore(enemies.get(i).getScore());
				enemies.remove(i);
			}
			else{
				i++;
			}
		}
		
		i = 0;
		while (i < projectiles.size()){
			projectiles.get(i).update(arg0, arg1, arg2);
			if (projectiles.get(i).isDestructed()){
				projectiles.remove(i);
			}
			else{
				i++;
			}
		}
		
		for(Bonus b : bonuss)
		{
			b.update(arg0, arg1, arg2);
		}
	}

	//Souris*****************************************************************************
	public void mousePressed(int button,int x,int y){
	}


	@Override
	public int getID() {
		return ID;
	}

	public void keyReleased(int key, char c) {
		Nico.keyReleased(key, c);
	}

	public void keyPressed(int key, char c) {
		Nico.keyPressed(key, c);
		if (key == Input.KEY_ESCAPE) {
			System.exit(0);
		}
	}

	public static void reset(){
		Nico=new Bat(new BasicPlayer());
		plateforms= new ArrayList<Plateform>();
		bonuss=new ArrayList<Bonus>();
		enemies=new ArrayList<Ennemy>();
		projectiles = new ArrayList<Projectile>();
		try {
			decor = new Decor("img/brick.png","img/background.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		chargerNiveau("niveau");
		
		score = 0;
		boolean chargerOk=chargerNiveau("niveau");
		if(!chargerOk){
			System.out.println("niveau 1 non charge");
			plateforms.add(new Plateform(4,4,10,1));
		}
		enemies.add(new Enemy1(new EnnemyShooter(new BasicEnnemy(plateforms.get(5)))));
		enemies.add(new Enemy1(new BasicEnnemy(plateforms.get(3))));
		
		
	}
	
	
	//Getters*******************************************************************************
	public static Player getPlayer(){
		return Nico;
	}
	public static ArrayList<Ennemy> getEnemies(){
		return enemies;
	}
	public static ArrayList<Plateform> getPlateforms(){
		return plateforms;
	}
	
	public static ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public static ArrayList<Bonus> getBonus(){
		return bonuss;
	}

	public static int getScore() {
		// C'est pour le menu de fin de partie surtout
		return score;
	}
	
	//Setters*******************************************************************************
	
	public void setScore(int i) {
		score = i;
	}
	
	public static void addProjectile(Projectile p){
		if(p!=null) projectiles.add(p);
	}
	
	//Modified*******************************************************************************
	public void plus50score()
	{
		score += 50;
	}
	
	private static boolean chargerNiveau(String niveau) {
		File f=new File(niveau);
		if(f.exists()){
			System.out.println("Le niveau "+niveau+" n'existe pas dans le repertoire "+REPERTOIRE_NIVEAU);
			return false;
		}
		try {
			BufferedReader br=new BufferedReader(new FileReader(REPERTOIRE_NIVEAU+File.separator+niveau));
			String ligne;
			while((ligne=br.readLine())!=null){
				//Def des plateformes
				if(ligne.startsWith("Plateform")){
					Plateform p=new Plateform(ligne);
					plateforms.add(p);
				}
				else if(ligne.startsWith("DeathBloc"))
				{
					DeathBloc p =new DeathBloc(ligne);
					plateforms.add(p);
				}
				else if(ligne.startsWith("ElevatorTrap")){
					ElevatorTrap p= new ElevatorTrap(ligne);
					plateforms.add(p);
				}
				//Def des ennemies
				else if(ligne.startsWith("EnnemyShooter")){
					String[] ligne2 = ligne.split(" ");
					int i = Integer.parseInt(ligne2[1]);
					enemies.add(new Enemy1(new EnnemyShooter(new BasicEnnemy(plateforms.get(i-1)))));
				}
				//Def des bonus
				else if(ligne.startsWith("LevelEnd")){
					String[] ligne2 = ligne.split(" ");
					bonuss.add(new LevelEnd(
							Double.parseDouble(ligne2[1]), 
							Double.parseDouble(ligne2[2]),
							Double.parseDouble(ligne2[3]), 
							Double.parseDouble(ligne2[4]),
							Nico));
				}
				else if (ligne.startsWith("BatBonus")){
					String[] ligne2 = ligne.split(" ");
					bonuss.add(new BatBonus(
							Double.parseDouble(ligne2[1]),
							Double.parseDouble(ligne2[2]),
							Double.parseDouble(ligne2[3]),
							Double.parseDouble(ligne2[4]),
							Nico));					
				}
				else if (ligne.startsWith("GunBonus")){
					String[] ligne2 = ligne.split(" ");
					bonuss.add(new GunBonus(
							Double.parseDouble(ligne2[1]),
							Double.parseDouble(ligne2[2]),
							Double.parseDouble(ligne2[3]),
							Double.parseDouble(ligne2[4]),
							Nico));
				}
				
			}
			br.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	public static void setPlayer(Player p){
		Nico = p;
	}
	

}
