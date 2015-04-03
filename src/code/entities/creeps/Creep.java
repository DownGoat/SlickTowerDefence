package code.entities.creeps;

import java.util.Queue;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import code.MapTuple;
import code.entities.CollisionEntity;


public abstract class Creep extends CollisionEntity {
	protected Image img;
	private Queue<MapTuple> path;
	protected long lastMove = 0;
	protected int hp;
	protected int velocity;
	protected int spawnDelay;
	
	
	public Creep(Queue<MapTuple> path, Image img) {
		// Must multiply with 32 since the map tuple is the tile cord, and not pixel cord. 
		this.x = (float) (path.peek().getX() * 32);
		this.y = (float) (path.peek().getY() * 32);
		this.setPath(path);
		this.img = img;
		collisionRect = new Rectangle(x, y, img.getWidth(), img.getHeight());		
	}
	
	

	@Override
	public void draw() {
		img.draw(x, y);
	}
	
	@Override
	public void logic(int delta) {
		lastMove += delta;
		
		if (lastMove >= velocity) {
			System.out.println("lastMove " + lastMove + " is higher than vel " + velocity);
			MapTuple mt = getPath().poll();
			this.x = (float) (mt.getX() * 32);
			this.y = (float) (mt.getY() * 32);

			collisionRect.setX(x);
			collisionRect.setY(y);
			
			lastMove = 0;
		}
	
		
	}
	
	public boolean decreaseHP(int damage) {
		hp -= damage;
		
		if ( hp <= 0) {
			return true;
		}
		
		return false;
	}

	public Queue<MapTuple> getPath() {
		return path;
	}

	public void setPath(Queue<MapTuple> path) {
		this.path = path;
	}



	public int getSpawnDelay() {
		// TODO Auto-generated method stub
		return 0;
	}
}
