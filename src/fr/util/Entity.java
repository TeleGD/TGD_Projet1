package fr.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Entity {
	
	// les variables
	protected double x,y;
	protected double newX,newY;
	protected double speedX,speedY;
	protected double accelX,accelY;
	protected double width, height;
	protected int life;
	protected boolean killable;
	
	
	//Nececessaire pour les jeux****************************************************
	public abstract void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException;
	public abstract void update(GameContainer container, StateBasedGame game, int delta) throws SlickException;

	
	//Setters*********************************************************************
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	
	
	//Getters***********************************************************************
	public double getAccelX() {
		return accelX;
	}
	public double getAccelY() {
		return accelY;
	}
	public void setNewX(double newX) {
		this.newX = newX;
	}
	public void setNewY(double newY) {
		this.newY = newY;
	}
	public void setAccelX(double accelX) {
		this.accelX = accelX;
	}
	public void setAccelY(double accelY) {
		this.accelY = accelY;
	}
	public double getY() {
		return y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getnewY() {
		//Methode forcee par la bibliotheque...
		return newY;
	}
	
	public double getnewX() {
		//Methode forcee par la bibliotheque...
		return newX;
	}
	
	public boolean isKillable() {
		return killable;
	}
	public void setKillable(boolean killable) {
		this.killable = killable;
	}
	public double getNewX() {
		return newX;
	}
	public void setNewx(double newx) {
		this.newX = newx;
	}
	public double getNewY() {
		return newY;
	}
	public void setNewy(double newy) {
		this.newY = newy;
	}
	public double getSpeedX() {
		return speedX;
	}
	public void setSpeedX(double speedx) {
		this.speedX = speedx;
	}
	public double getSpeedY() {
		return speedY;
	}
	public void setSpeedY(double speedy) {
		this.speedY = speedy;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	
	@Override
	public String toString(){
		return "x="+x+" y="+y+" width="+width+" height="+height;
	}
	//methode qui determine si un point(x,y) est contenu dans la inbox de l'entity
	public boolean containsPoint(int x,int y){
		return this.x<=x &&  x<=this.x+width && this.y<=y && y<=this.y+height;
	}

	



}
