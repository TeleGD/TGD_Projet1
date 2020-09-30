package games.jeanRobot.projectiles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.jeanRobot.characters.Player;
import games.jeanRobot.World;
import games.jeanRobot.util.Movable;
import games.jeanRobot.util.Rectangle;

public class Projectile extends Movable implements Rectangle{
// Base projectile : straight trajectory.


	private boolean destructed;
	private boolean allied;
	protected Image sprite;
	protected int angle;
	protected double speed;

	public Projectile(double x, double y, double speedX, double speedY){
		super(x,y,3,3);
		this.speedX = speedX;
		this.speedY = speedY;
		speed = Math.sqrt(Math.pow(speedX, 2)+Math.pow(speedY, 2));
		loadImage("/images/jeanRobot/Projectiles/tir_tourelle.png");
		destructed = false;
		angle = 0;
		setAllied(false);

	}

	public Projectile(double x, double y, double speedX, double speedY, boolean allied){
		super(x,y,3,3);
		this.speedX = speedX;
		this.speedY = speedY;
		speed = Math.sqrt(Math.pow(speedX, 2)+Math.pow(speedY, 2));
		this.setAllied(allied);
		if(allied) loadImage("/images/jeanRobot/proj1.png");
		else loadImage("/images/jeanRobot/Projectiles/tir_tourelle.png");
		destructed = false;
		angle = 0;
	}

	public Projectile(double x,double y, double speed, int angle, boolean allied){
		super(x,y,3,3);
		this.speedX = speed*Math.cos(angle);
		this.speedY = speed*Math.sin(angle);
		this.setAllied(allied);
		if(allied) loadImage("/images/jeanRobot/proj1.png");
		else loadImage("/images/jeanRobot/Projectiles/tir_tourelle.png");
		destructed = false;
		this.angle = angle;
		sprite.rotate(angle);
	}

	private void loadImage(String path){
		sprite=AppLoader.loadPicture(path);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(!destructed) g.drawImage(sprite,(float)x,(float)y);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		moveX(delta);
		moveY(delta);
	}

	public void destruct(){
		this.destructed = true;
	}

	public boolean isDestructed(){
		return destructed;
	}

	public boolean isAllied() {
		return allied;
	}

	public void setAllied(boolean allied) {
		this.allied = allied;
	}

	/**
	 * Est appellée pendant l'update de player
	 * @param Le player actuel
	 */
	public void collPlayer(StateBasedGame game, Player player) {
		int colPlayer = games.jeanRobot.util.Collisions.colPlayerProjectile(player,this);
		if (colPlayer != 0){
			World.getPlayer().lifelost(game);
			this.destructed = true;
		}
	}
}
