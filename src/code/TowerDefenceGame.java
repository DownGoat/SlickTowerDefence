package code;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import code.states.BuildState;
import code.states.WaveState;

public class TowerDefenceGame extends StateBasedGame {
	public static final int WAVESTATE = 0;
	public static final int BUILDSTATE = 1;


	public static int frameWidth;
	public static int frameHeight;

	public TowerDefenceGame(String name, int frameWidth, int frameHeight) {
		super(name);

		this.addState(new WaveState(WAVESTATE));
		this.addState(new BuildState(BUILDSTATE));


		TowerDefenceGame.frameHeight = frameHeight;
		TowerDefenceGame.frameWidth = frameWidth;

		this.enterState(BUILDSTATE);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(WAVESTATE).init(gc, this);
		this.getState(BUILDSTATE).init(gc, this);

	}
}
