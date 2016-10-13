package fr.decor;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.characters.Player;
import fr.game.Game;
import fr.util.Entity;
import fr.util.Rectangle;

public class Plateform extends Entity implements Rectangle {

	Image texture;

	// Setters *****************************************************
	public void setPosition(float newx, float newy) {
		x = newx;
		y = newy;
	}

	public void setSize(float newwidth, float newheight) {
		width = newwidth;
		height = newheight;
	}

	// Constructeur***********************************************
	public Plateform(int indexX, int indexY, int sizeX, int sizeY) {
		this.x = indexX*Game.DENSITE_X;
		this.newx = this.x;
		this.newy = this.y;
		this.y = indexY*Game.DENSITE_Y;
		this.width = sizeX*Game.DENSITE_X;
		this.height = sizeY*Game.DENSITE_Y;
	}
	
	public Plateform(Plateform p){
		this.x = p.x;
		this.y = p.y;
		this.newx = p.newx;
		this.newy = p.newy;
		this.width = p.width;
		this.height = p.height;
	}

	// Fonction de jeu*********************************************
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.blue);
		g.fillRect((float) x, (float) y, (float) width, (float) height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO
		this.newx = this.x;
		this.newy = this.y;
	}
	

	@Override
	public double getnewX() {
		return x;
	}

	@Override
	public double getnewY() {
		return y;
	}

	public boolean collPlayer(Player player){
		if(player.getspeedY()<0){return false;}
		if(player.getnewY()+player.getHeight()<this.y){return false;}
		if(player.getY()>this.y){return false;}
		if(player.getX()>this.x+this.width){return false;}
		if(player.getX()+player.getWidth()<this.x){return false;}
		return true;

	}
	
	


}
