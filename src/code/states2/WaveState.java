package code.states;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import code.MapTuple;
import code.TowerDefenceGame;
import code.datastructures.Buildings;
import code.datastructures.Creeps;
import code.datastructures.Gold;
import code.datastructures.HP;
import code.datastructures.Projectiles;
import code.entities.Projectile;
import code.entities.creeps.Creep;
import code.entities.towers.Tower;
import code.waves.*;
import code.waves.Wave1;

public class WaveState extends BasicGameState {
	private int stateID;
	private TiledMap theMap;
	
	public Queue<MapTuple> path = new LinkedList<MapTuple>();
	private LinkedList<Creep> spawnedCreeps = new LinkedList<Creep>();
	private LinkedList<Wave> waves;
	
	private Iterator<Creep> spawnIterator;
	private int spawnTimer = 0;
	private int spawnDelay;
	

	public WaveState(int stateID) {
		this.stateID = stateID;	
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		gc.setVSync(true);
		
		theMap = new TiledMap("res/testmap.tmx");		
		generatePath();
		
		loadWaves();		
	}

	public void resert() {
		Creeps.clear();
		waves.poll().initWave(path);
		
		
		
		spawnIterator = Creeps.getCreeps().iterator();
		spawnedCreeps.add(spawnIterator.next());
		spawnDelay = spawnedCreeps.getFirst().getSpawnDelay();
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
		
		for (Projectile projectile : Projectiles.getProjectiles()) {
			projectile.draw();
		}
		
		for (Creep creep : spawnedCreeps) {
			creep.draw();
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		if (spawnedCreeps.size() == 0) {
			sbg.enterState(TowerDefenceGame.BUILDSTATE);
		}
		
		for (Tower tower : Buildings.getTowers()) {
			tower.setSpawnedCreeps(spawnedCreeps);
		}
		
		if (spawnIterator.hasNext()) {
			if (spawnTimer >= spawnDelay) {
				spawnedCreeps.add(spawnIterator.next());
				spawnDelay = spawnedCreeps.getLast().getSpawnDelay();
				spawnTimer = 0;
			}
		}
		spawnTimer += delta;
				
		for (Tower tower : Buildings.getTowers()) {
			tower.logic(delta);
		}
		
		for (Projectile projectile : Projectiles.getProjectiles()) {
			projectile.logic(delta);
		}
		
		Iterator<Creep> creepIter = spawnedCreeps.iterator();
		while (creepIter.hasNext()) {
			Creep creep = creepIter.next();
			
			if (creep.getX() == ((LinkedList<MapTuple>) path).getLast().getX() * 32 &&
					creep.getY() == ((LinkedList<MapTuple>) path).getLast().getY() * 32) {
					creepIter.remove();
					HP.decrement();
					continue;
			}
			
			creep.logic(delta);
			
		}
		for (Creep creep : spawnedCreeps) {
			creep.logic(delta);
		}
		
		checkCollisions();
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

	public void generatePath() {
		int startX = 0, startY = 0;
		
		// Find the starting point
		for (int x = 0; x < theMap.getWidth(); x++) {
			for (int y = 0; y < theMap.getHeight(); y++) {
				int tileID = theMap.getTileId(x, y, 0);
				String value = theMap.getTileProperty(tileID, "position", "-1");
				
				// The starting point has the position value 1.
				if ("1".equals(value)) {
					startX = x;
					startY = y;
					x = theMap.getWidth();
					y = theMap.getHeight();
				}
			}
		}
		int been[][] = new int[theMap.getWidth()][theMap.getHeight()];
		for (int x = 0; x < theMap.getWidth(); x++) {
			for (int y = 0; y < theMap.getHeight(); y++) {
				been[x][y] = 0;
			}
		}
		Stack<MapTuple> theStack = new Stack<MapTuple>();	
		theStack.push(new MapTuple(startX, startY));
		dfs(theStack, been);
	}
	
	/**
	 * Depth First Search for finding the paths. DFS is
		waves = LinkedList<Wave>() good enough since there is
	 * only one path
	 * @param theStack
	 * @param been
	 * @return
	 */
	public MapTuple dfs(Stack<MapTuple> theStack, int been[][]) {
		MapTuple mt = theStack.pop();
		int x = mt.getX();
		int y = mt.getY();
		
		int tileID = theMap.getTileId(x, y, 2);
		
		// If the tile is not walkable continue without adding more areas to the stack.
		if (!"1".equals(theMap.getTileProperty(tileID, "walkable", "0"))) {
			been[x][y] = 1;
			return dfs(theStack, been);
		}
		
		// Check if it is the end position.
		int otherID = theMap.getTileId(x, y, 0);
		if ("0".equals(theMap.getTileProperty(otherID, "position", "-1"))) {
			been[x][y] = 1;
			return mt;
		}
		
	
		// Add the surrounding tiles to the stack if they have not been visited before.
		if (x >= 1 && been[x - 1][y] != 1) {
			theStack.push(new MapTuple(x - 1, y));
		}
		
		if (x < theMap.getWidth() - 1 && been[x + 1][y] != 1) {
			theStack.push(new MapTuple(x + 1, y));
		}
		
		if (y >= 1 && been[x][y - 1] != 1) {
			theStack.push(new MapTuple(x, y - 1));
		}
		
		if (y < theMap.getHeight() - 1 && been[x][y + 1] != 1) {
			theStack.push(new MapTuple(x, y + 1));
		}
		
		path.add(mt);
		been[x][y] = 1;
		return dfs(theStack, been);
	}
	
	public void checkCollisions() {
		Iterator<Projectile> projectileIter = Projectiles.getProjectiles().iterator();
		while (projectileIter.hasNext()) {
			Iterator<Creep> creepIter = spawnedCreeps.iterator();
			Projectile theProjectile = projectileIter.next();
			
			boolean cont = true;
			while (creepIter.hasNext() && cont) {
				Creep theCreep = creepIter.next();
				
				if (theProjectile.collision(theCreep.getCollisionRect())) {
					if (theCreep.decreaseHP(theProjectile.getDamage())) {
						Gold.increase(theCreep.getGoldWorth());
						creepIter.remove();
					}
					projectileIter.remove();
					cont = false;
				}
			}
		}
	}
	
	public void loadWaves() {
		waves = new LinkedList<Wave>();
		waves.add(new Wave1());
		waves.add(new Wave2());
		waves.add(new Wave3());
		waves.add(new Wave4());
		waves.add(new Wave5());
		waves.add(new Wave6());
	}
}
