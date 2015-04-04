package code.states;

import java.util.LinkedList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import code.TowerDefenceGame;
import code.datastructures.Buildings;
import code.datastructures.Gold;
import code.datastructures.HP;
import code.datastructures.UIElement;
import code.entities.towers.BasicTower;
import code.entities.towers.BasicTowerInfo;
import code.entities.towers.Tower;

public class BuildState extends BasicGameState {
	private int stateID;
	private boolean placeMode = false;

	// GUI Stuff
	private LinkedList<UIElement> UIelements;
	private UIElement bottom;
	private UIElement ready;
	private UIElement basicTower;
	private UIElement placeholder;

	private TiledMap theMap;
	// For colliding with UI elements.
	private Rectangle mouseRect;

	public BuildState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		gc.setVSync(true);
		theMap = new TiledMap("res/testmap.tmx");
		mouseRect = new Rectangle(0, 0, 1, 1);
		

		UIelements = new LinkedList<UIElement>();
		bottom = new UIElement("res/bottommenu.png", 0, 352);
		UIelements.add(bottom);
		basicTower = new UIElement("res/basictower64.png", 4, 356);
		UIelements.add(basicTower);
		ready = new UIElement("res/ready.png", (float) (320 - 64),  20);
		UIelements.add(ready);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		theMap.render(0, 0);
		
		g.drawString("Gold:" + Gold.getGold(), 80, 10);
		g.drawString("HP:" + HP.getHP(), 150, 10);

		for (Tower tower : Buildings.getTowers()) {
			tower.draw();
		}
		
		if (!placeMode) {
			for (UIElement uie : UIelements) {
				uie.draw();
			}
			
			drawTowerInfo(g);
		}
	}

	private void drawTowerInfo(Graphics g) {
		g.drawString("DMG:"+BasicTowerInfo.getDMG(), 69, 360);
		g.drawString("ATK:"+BasicTowerInfo.getATKS(), 69, 380);
		g.drawString("PRICE:"+BasicTowerInfo.getPRICE(), 69, 400);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		

		Input input = gc.getInput();
		if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)) {
			if (placeMode) {
				float xtile = (input.getMouseX() -(input.getMouseX() % 32)) / 32;
				float ytile = (input.getMouseY() -(input.getMouseY() % 32)) / 32;
				Buildings.add(new BasicTower(xtile, ytile, "res/basictower32.png"));
				placeMode = false;
				Gold.decrease(BasicTowerInfo.getPRICE());
			}
			
			mouseRect.setX(input.getMouseX());
			mouseRect.setY(input.getMouseY());
			
			if (ready.collision(mouseRect)) {
				((WaveState) sbg.getState(TowerDefenceGame.WAVESTATE)).resert();
				sbg.enterState(TowerDefenceGame.WAVESTATE);
			}
			
			if (basicTower.collision(mouseRect) && Gold.getGold() >= BasicTowerInfo.getPRICE()) {
				placeMode = true;
			}
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
