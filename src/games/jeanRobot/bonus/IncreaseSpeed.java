package games.jeanRobot.bonus;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import games.jeanRobot.characters.BasicPlayer;
import games.jeanRobot.characters.Player;

public class IncreaseSpeed extends Bonus{

	@Override
	/**
	 * augmente la vitesse sur l'axe des x
	 */
	public void comportment(Player player) {
		BasicPlayer basicPlayer = (BasicPlayer) player;
		this.tempsActivation = System.currentTimeMillis();
		basicPlayer.setSpeedX(basicPlayer.getSpeedX()*2);
		// TODO verriefier si le wait est juste pour la fonction ou pour tout le jeu
		while (this.tempsActivation+this.duration>System.currentTimeMillis()){
			//la boucle permet d'attendre la durée duration
		}
		basicPlayer.setSpeedX(basicPlayer.getSpeedX()/2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub

	}


}
