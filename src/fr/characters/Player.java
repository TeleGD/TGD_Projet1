package fr.characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import fr.Behavior.BeCollision;
import fr.Behavior.CanPassByBelow;
import fr.decor.Plateform;
import fr.game.Game;
import fr.menus.MenuFinPartie;
import fr.util.Collisions;
import fr.util.Entity;
import fr.util.Movable;
import fr.util.Rectangle;


public class Player extends Movable implements Rectangle {
	
	private boolean colplat;// y a t il eu une coll avec une plateforme a la
	// derniere frame
	private boolean vertcolthis;// il  y a eu une plateforme en dessous
	private boolean upPress, leftPress, rightPress, droitegauche, downPress;
	private int life;
	private long timekillableDying=3000;// temps d'invincibilite d une mort
	private long timeOfDeath;
	private double comptkillable;//compteur servant au clignotement durant l'invincibilite suivant la mort
	protected boolean leftclick=false;
	protected BeCollision coli = new CanPassByBelow();
	protected int[][] tv;
	protected double speed; //Pour unifier la vitesse, delta c'est le mal
	private double fallTime; //Pour pas avoir besoin de l'acc�l�ration et simplifier la gestion de la ch�te !
	private double jumpTime; //Pour g�rer les sauts
	
	public Player() {
		this.fallTime=0;
		this.jumpTime=1000;
		this.x=200;
		this.y=0;
		this.newX =  this.x;
		this.newY = this.y;
		this.height=64;
		this.width=32;
		this.speedX = 0;
		this.speedY = 0;
		this.accelY = 0;
		this.accelX = 0;
		this.colplat = false;
		this.timeOfDeath = -3000;
		this.life=3;
		this.gravity=0.5;
		this.speed = 5;
	}


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		/*if((System.currentTimeMillis()-this.timeOfDeath<this.timekillableDying)&&(this.comptkillable>=5)){
			if((this.comptkillable>=10)){
				this.comptkillable=0;
			}
		}else{*/
			g.setColor(Color.green);
			g.fillRect((float) x, (float) y, (float) width, (float) height);
		//	}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.x = this.newX;
		this.y = this.newY;
		if(System.currentTimeMillis()-this.timeOfDeath<this.timekillableDying){killable=false;}else{killable=true;}
		if(!killable){this.comptkillable=0;}else{this.comptkillable+=1;}

		// this.posjump = this.updatePosJump(); //verifie la possibilite de sauter		
		//if(Math.abs(this.speedY) > 1) speedY = 1; // limitation de vitesse.
		altMove();
		// this.speedY += accelY; on en a pas besoin en utilisant ma gestion de ch�te
		this.newX = x + speedX;
		this.newY = y + speedY;
	}

	// Mouvements************************************************************************

	/*private boolean updatePosJump() {
		// TODO Auto-generated method stub
		return false;
	}
*/
	private void altMove(){
		/**
		 * Fonction qui g�re le mouvement
		 */
		//int[][] tv = BeCollision.altMoveByCollision(this, this.coli);
		if (isTooLow()) {
			//le personnage meurt
			fr.game.World.game.enterState(MenuFinPartie.ID);//d, new FadeOutTransition(),new FadeInTransition());
			reset();
		}
		horizontalMove();
		verticalMove();
	}

	private void horizontalMove() {
		/**
		 * Fonction qui permet de g�rer le d�placement lat�ral
		 */
		speedX = 0;
		boolean bougeable = true;
		if ((leftPress && !rightPress) || (leftPress && rightPress && !droitegauche)) {
			speedX=-speed;
			this.tv = BeCollision.altMoveByCollision(this, this.coli);
			if (this.coli instanceof CanPassByBelow ){
				for (int i = 0; i < tv.length; i++) {
					if (tv[i][0] == 1){
						bougeable = false;
					}
				}
				speedX=0;
				if (bougeable){
					speedX=-speed;
				}
			}
		}
		if ((!leftPress && rightPress) || (leftPress && rightPress && droitegauche)) {
			speedX=speed;
			this.tv = BeCollision.altMoveByCollision(this, this.coli);
			if (this.coli instanceof CanPassByBelow ){
				for (int i = 0; i < tv.length; i++) {
					if (tv[i][0] == -1){
						bougeable = false;
					} 
				}
				speedX=0;
				if (bougeable){
					speedX=speed;
				}
			}
		}	
	}

	public void verticalMove() {
		/**
		 * Fonction qui permet de g�rer la ch�te et les sauts 
		 */
		speedY = 0;
		boolean bougeable = true;
		//Ici on g�re les sauts
		if ((upPress && !downPress) && (this.jumpTime<25*this.jumppower)) { //on limite la taille max du saut tout en permettant au joueur de g�rer la puissance de ses sauts :D
			speedY=-speed;
			this.tv = BeCollision.altMoveByCollision(this, this.coli);
			if (this.coli instanceof CanPassByBelow ){
				for (int i = 0; i < tv.length; i++) {
					if (tv[i][1] == 1){
						bougeable = false;
					}
				}
				speedY=0;
				if (bougeable){
					speedY= -speed * Math.sqrt((16/(1+jumpTime))); // pour un saut plus classe
					this.jumpTime += 1;
				}
			}
		}
		//La on g�re la ch�te
		else {
			speedY= this.gravity * this.fallTime;
			this.tv = BeCollision.altMoveByCollision(this, this.coli);
			if (this.coli instanceof CanPassByBelow ){
				for (int i = 0; i < tv.length; i++) {
					if (tv[i][1] == -1){
						bougeable = false;
						this.jumpTime=0; //On permet les sauts
						this.fallTime= this.fallTime/2; //On met �a pour donner l'impression que le personnage touche la plateforme ! Faudrait rendre �a plus propre
					}
				}
				speedY=0;
				if (bougeable){
					speedY = this.gravity * this.fallTime;
					fallTime += 1.5;
				}
			}
		}	
	}
	
	// BOUH CACA, je sais pas pourquoi on a cod� �a avant -_-
	public boolean updatePosJump(){ //renvoie true si le personnage a la possibilite de sauter
		/*
		boolean mem = false;
		this.vertcolthis = false;
		for (int i = 0; i < fr.game.World.getPlateforms().size(); i++) {
			this.vertcolthis = (Collisions.isCollisionY(this, fr.game.World.getPlateforms().get(i))!=0 || Collisions.isCollisionX(this, fr.game.World.getPlateforms().get(i))!=0 || this.vertcolthis);
			
			/*
			if ((Collisions.isCollisionY(this, fr.game.World.getPlateforms().get(i)) == 1)) {
				//si une plateforme au dessus : on saute pas
				this.accelY = 0;
				this.speedY = 0;
				return false;
			}
			*/
			
			/* l'autre gère ça
			if ((Collisions.isCollisionY(this, fr.game.World.getPlateforms().get(i)) == -1)) {
				//si plateforme en dessous : mem = true
				this.accelY = 0;
				this.speedY = 0;
				mem = true;
			} 
			*/
		
		return true;
	}
	
	private boolean isTooLow() { //renvoie true si le personne touche le bas de l'ecran
		if (this.getSpeedY() < 0) {
			return false;
		}
		if (this.getNewY() + this.getHeight() < 720) {
			return false;
		}
		return true;
	}

	public boolean getcolplat() {
		return this.colplat;
	}
	
	// Les touches*******************************************************
	public void keyReleased(int key, char c) {

		switch (key) {
		case Input.KEY_Z:
			
		case Input.KEY_UP:
			this.jumpTime = 1000*this.jumppower; //Lorsque l'on relache la touche, on ne doit pas pouvoir double jump
			upPress = false;
			break;

		case Input.KEY_DOWN:
			downPress = false;
			break;

		case Input.KEY_Q:
		case Input.KEY_LEFT:
			leftPress = false;
			break;

		case Input.KEY_D:
		case Input.KEY_RIGHT:
			rightPress = false;
			break;
		}
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_Z:
			
		case Input.KEY_UP:	
			upPress = true;
			break;

		case Input.KEY_DOWN:
			downPress = true;
			break;

		case Input.KEY_Q:
		case Input.KEY_LEFT:
			leftPress = true;
			droitegauche = false;
			break;
		case Input.KEY_D:
		case Input.KEY_RIGHT:
			rightPress = true;
			droitegauche = true;
			break;
		}
	}

	//souris**********************************************************************
	public void mousePressed(int button, int x,int y){
	}

	// Autres
	// **************************************************************************
	public void lifelost() {
		this.life -= 1;
		this.comptkillable=0;
		this.timeOfDeath = System.currentTimeMillis();
		if (life == 0) {
			fr.game.World.game.enterState(MenuFinPartie.ID, new FadeOutTransition(),
					new FadeInTransition());
		}
	}

	public boolean iskillable(){
		return killable;
	}

	public int getlife(){
		return life;
	}

	public void reset(){
		this.fallTime=0;
		this.jumpTime=0;
		this.x=200;
		this.y=0;
		this.newX =  this.x;
		this.newY = this.y;
		this.height=64;
		this.width=32;
		this.speedX = 0;
		this.speedY = 0;
		this.accelY = 0;
		this.accelX = 0;
		this.colplat = false;
		this.timeOfDeath = -3000;
		this.life=3;
		this.gravity=0.1;
		this.speed = 5;
	}

}
