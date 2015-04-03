package code;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import code.entities.Projectile;
import code.entities.Tower;
import code.entities.creeps.Creep;
import code.waves.Wave1;



public class TowerDefence extends BasicGame {
	private TiledMap theMap;
	public Queue<MapTuple> path = new LinkedList<MapTuple>();
	private Creep creep;
	
	private LinkedList<Projectile> projectiles;
	private Tower tower;
	
	// This is the spawn iterator, used to spawn creeps in order.
	private LinkedList<Creep> creeps;
	private LinkedList<Creep> spawnedCreeps = new LinkedList<Creep>();
	private Iterator<Creep> spawnIterator;
	private int spawnTimer = 0;
	private int spawnDelay;

	public TowerDefence(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	
	
	public long getTime() {
	    return System.nanoTime() / 1000000;
	}
	


	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new TowerDefence("TowerDefence"));
			appgc.setDisplayMode(640, 480, false);
			appgc.setVSync(true);
			appgc.setTargetFrameRate(60);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(TowerDefence.class.getName()).log(Level.SEVERE, null, ex);
		}
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
		MapTuple ret = dfs(theStack, been);
	}
	
	/**
	 * Depth First Search for finding the paths. DFS is good enough since there is
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
		
		System.out.println("X: " + x + " Y: " + y);
		path.add(mt);
		been[x][y] = 1;
		return dfs(theStack, been);
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		theMap.render(0, 0);
		tower.draw();
		
		for (Projectile projectile : projectiles) {
			projectile.draw();
		}
		
		for (Creep creep : spawnedCreeps) {
			creep.draw();
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		projectiles = new LinkedList<Projectile>();
		
		theMap = new TiledMap("res/testmap.tmx");	
		generatePath();	
		
		creeps = new Wave1().initWave(path);
		spawnIterator = creeps.iterator();
		spawnedCreeps.add(spawnIterator.next());
		spawnDelay = spawnedCreeps.getFirst().getSpawnDelay();
		
		tower = new Tower(2, 5, spawnedCreeps, projectiles);	
	}

	@Override
	public void update(GameContainer arg0, int delta) throws SlickException {
		if (spawnIterator.hasNext()) {
			if (spawnTimer >= spawnDelay) {
				spawnedCreeps.add(spawnIterator.next());
				spawnDelay = spawnedCreeps.getLast().getSpawnDelay();
				spawnTimer = 0;
			}
		}
		
		tower.logic(delta);
		
		for (Projectile projectile : projectiles) {
			projectile.logic(delta);
		}
		
		for (Creep creep : spawnedCreeps) {
			creep.logic(delta);
		}
		
		checkCollisions();
	}
	
	/**
	 * This function is used to check if the projectiles has collided with a creep.
	 */
	public void checkCollisions() {
		Iterator<Projectile> projectileIter = projectiles.iterator();
		while (projectileIter.hasNext()) {
			Iterator<Creep> creepIter = spawnedCreeps.iterator();
			Projectile theProjectile = projectileIter.next();
			while (creepIter.hasNext()) {
				Creep theCreep = creepIter.next();
				
				if (theProjectile.collision(theCreep.getCollisionRect())) {
					if (theCreep.decreaseHP(theProjectile.getDamage())) {
						creepIter.remove();
						// TODO add increased bitcoins.
					}
					projectileIter.remove();
				}
			}
		}
	}

}
