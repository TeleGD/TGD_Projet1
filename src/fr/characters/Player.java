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

import fr.menus.MenuFinPartie;
import fr.util.Collisions;
import fr.util.Movable;
import fr.util.Rectangle;

//changer movable en hitbox
public class Player extends Movable implements Rectangle {
	
	//private double height,width;
	private boolean colplat;// y a t il eu une coll avec une plateforme a la
	// derniere frame
	private boolean vertcolthis;// il  y a eu une plateforme en dessous
	private boolean upPress, leftPress, rightPress, droitegauche, downPress;
	private int life;
	private long timekillableDying=3000;// temps d'invincibilitï¿½ï½¿ï½½ aprï¿½ï½¿ï½½s une mort
	private long timeOfDeath;
	private double comptkillable;//compteur servant au clignotement durant l'invincibilite suivant la mort
	protected boolean leftclick=false;
	
	public Player() {
		this.x=150;
		this.y=150;
		this.height=64;
		this.width=32;
		this.speedX = 0;
		this.speedY = 0;
		this.accelY = gravity;
		this.accelX = 0;
		this.colplat = false;
		this.timeOfDeath = -3000;
		this.life=3;
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
		if(System.currentTimeMillis()-this.timeOfDeath<this.timekillableDying){killable=false;}else{killable=true;}
		if(!killable){this.comptkillable=0;}else{this.comptkillable+=1;}
		
		this.posjump = this.updatePosJump(); //verifie la possibilitÃ© de sauter
		
		this.newx = x + speedX * delta;
		this.newy = y + speedY * delta;
		this.speedY += accelY;
		horizontalMove();
		moveX(delta);
		verticalMove();
		moveY(delta);
		
		this.colplat = this.vertcolthis;
	}

	// Mouvements************************************************************************

	private boolean updatePosJump() {
		// TODO Auto-generated method stub
		return false;
	}


	private void horizontalMove() {
		//Collision en allant vers la gauche
		if ((leftPress && !rightPress) || (leftPress && rightPress && !droitegauche)){
			for (int i = 0; i < fr.game.World.getPlateforms().size(); i++){
				if(Collisions.altCollisionX(this, fr.game.World.getPlateforms().get(i))){
					speedX = 0;
				}
			}
		}
		// Collision en allant vers la droite
		if ((!leftPress && rightPress) || (leftPress && rightPress && droitegauche)){
			for (int i = 0; i < fr.game.World.getPlateforms().size(); i++){
				if(Collisions.altCollisionX(this, fr.game.World.getPlateforms().get(i))){
					speedX = 0;
				}
			}
		}
	}


	private void verticalMove() {

		if (isTooLow()) {
			//le personnage meurt
			fr.game.World.game.enterState(MenuFinPartie.ID);//d, new FadeOutTransition(),new FadeInTransition());
		}
		
		if (this.posjump && upPress) {
			jump();
		}
		
		for (int i = 0; i < fr.game.World.getPlateforms().size(); i++){
			if(Collisions.altCollisionY(this, fr.game.World.getPlateforms().get(i))){
				speedY = 0;
			}
		}
	}	

//  Coucou PA, ici Arthur. On me dit dans l'oreille que tu n'es pas PA, mais Aurelien. Desole pour l'accent (et pour l'erreur).
//	Je comprenais pas ton code (vu que les collisions de PA sont au dela de ma simple condition d'humain), alors je l'ai commente. Bisous.
//	private void horizontalMove() {
//		speedX = 0;
//		if ((leftPress && !rightPress) || (leftPress && rightPress && !droitegauche)) {
//			speedX = 0;
//			for (int i = 0; i < fr.game.World.getPlateforms().size(); i++) {
//				if(Collisions.isCollisionX(this, fr.game.World.getPlateforms().get(i)) != -1){
//					speedX = -0.5;
//				}
//			}	
//		}
//		if ((!leftPress && rightPress) || (leftPress && rightPress && droitegauche)) {
//			speedX = 0;
//			for (int i = 0; i < fr.game.World.getPlateforms().size(); i++) {
//				if(Collisions.isCollisionX(this, fr.game.World.getPlateforms().get(i)) != 1){
//					speedX = 0.5;
//				}
//			}	
//		}
//		
//	}
//
//	public void verticalMove() {
//		// verticolthis et posjump sont actualisÃ©s dans updatePosJump qui est appelÃ© dans update
//		// collisionx(a,b), -1 -> a a gauche de b, 0 rien, 1 : a A droite de b
//		// collisiony(a,b) , -1 -> a au dessus de b , 0 rien, 1 -> a en dessous de b
//		
//		
//		if (isTooLow()) {
//			//le personnage meurt
//			fr.game.World.game.enterState(MenuFinPartie.ID);//d, new FadeOutTransition(),new FadeInTransition());
//		}
//		
//		if (this.posjump && upPress) {
//			jump();
//		}
//		
//		if (!vertcolthis) {	//si le personnage est en l'air, on accelere sa chute
//			this.accelY = gravity;
//		}
//		
//		this.speedY += this.accelY;
//		
//		if(vertcolthis){
//			System.out.println("bouh");
//		}
//	}
//	
//	public boolean updatePosJump(){ //renvoie true si le personnage a la possibilite de sauter
//		boolean mem = false;
//		this.vertcolthis = false;
//		for (int i = 0; i < fr.game.World.getPlateforms().size(); i++) {
//			this.vertcolthis = Collisions.altCollisionY(this, fr.game.World.getPlateforms().get(i)) || Collisions.altCollisionX(this, fr.game.World.getPlateforms().get(i)) || this.vertcolthis;
//			
//			if ((Collisions.isCollisionY(this, fr.game.World.getPlateforms().get(i)) == 1)) {
//				//si une plateforme au dessus : on saute pas
//				this.accelY = 0;
//				this.speedY = 0;
//				return false;
//			}
//			
//			if ((Collisions.isCollisionY(this, fr.game.World.getPlateforms().get(i)) == -1)) {
//				//si plateforme en dessous : mem = true
//				this.accelY = 0;
//				this.speedY = 0;
//				mem = true;
//			} 
//		}
//		return mem;
//	}

	public double getnewY() {
		return newy;
	}

	public double getnewX() {
		return newx;
	}

	public boolean getcolplat() {
		return this.colplat;
	}

	public void jump() {
		this.speedY = 0;
		this.accelY = -this.jumppower;
		this.vertcolthis = true;
	}

	private boolean isTooLow() { //renvoie true si le personne touche le bas de l'Ã©cran
		if (speedY < 0) {
			return false;
		}
		if (newy + height < 720) {
			return false;
		}
		return true;
	}

	// Les touches*******************************************************
	public void keyReleased(int key, char c) {

		switch (key) {
		case Input.KEY_Z:
			upPress = false;
			break;

		case Input.KEY_S:
			downPress = false;
			break;

		case Input.KEY_Q:
			leftPress = false;
			break;

		case Input.KEY_D:
			rightPress = false;
			break;
		}
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_Z:
			upPress = true;
			break;

		case Input.KEY_S:
			downPress = true;
			break;

		case Input.KEY_Q:
			leftPress = true;
			droitegauche = false;
			break;
		case Input.KEY_D:
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
		this.x = 150;
		this.y = 0;// 250-32;
		this.width = 16;
		this.height = 32;
		this.speedX = 0;
		this.speedY = 0;
		this.accelY = gravity;
		this.accelX = 0;
		colplat = false;
		this.killable = false;
		this.timeOfDeath = -3000;
		this.life=3;
	}


	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}


	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
}
