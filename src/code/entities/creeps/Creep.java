package code.entities.creeps;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import code.MapTuple;
import code.entities.CollisionEntity;


public abstract class Creep extends CollisionEntity {
	protected SpriteSheet sheet;
	protected long lastMove = 0;
	protected int hp;
	protected int velocity;
	protected int spawnDelay;
	protected Queue<MapTuple> localPath = new LinkedList<MapTuple>();
	protected int goldWorth;
	protected Animation animation;
	protected float rotation;
	protected boolean rotate = true;
	
	
	public Creep(Queue<MapTuple> path, String imgPath) {
		
		try {
			sheet = new SpriteSheet(imgPath, 32, 32);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		animation = new Animation(sheet, 750);
		rotation = 0;
		
		// Copy
		for (MapTuple mt : path) {
			localPath.add(mt);
		}
		// Must multiply with 32 since the map tuple is the tile cord, and not pixel cord. 
		this.x = (float) (localPath.peek().getX() * 32);
		this.y = (float) (localPath.peek().getY() * 32);

		collisionRect = new Rectangle(x, y, animation.getImage(0).getWidth(), animation.getImage(0).getHeight());		
	}
	
	

	@Override
	public void draw() {
		if (rotate) {
			animation.getCurrentFrame().setRotation(rotation);
		}
		
		animation.draw(x, y);
	}
	
	@Override
	public void logic(int delta) {
		lastMove += delta;
		
		animation.update(delta);
		
		float oldx = x;
		float oldy = y;
		
		if (lastMove >= velocity) {
			MapTuple mt = localPath.poll();
			this.x = (float) (mt.getX() * 32);
			this.y = (float) (mt.getY() * 32);

			collisionRect.setX(x);
			collisionRect.setY(y);
			
			lastMove = 0;
		}
		
		
		if (oldx - x < 0) {
			rotation = 90;
		}
		else if (oldx - x > 0) {
			rotation = -90;
		}
		else if (oldy - y > 0) {
			rotation = 0;
		}
		else if (oldy - y < 0) {
			rotation = 180;
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
		return localPath;
	}

	public void setPath(Queue<MapTuple> path) {
		this.localPath = path;
	}



	public int getSpawnDelay() {
		return spawnDelay;
	}


	public long getLastMove() {
		return lastMove;
	}



	public void setLastMove(long lastMove) {
		this.lastMove = lastMove;
	}



	public int getHp() {
		return hp;
	}



	public void setHp(int hp) {
		this.hp = hp;
	}



	public int getVelocity() {
		return velocity;
	}



	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}



	public Queue<MapTuple> getLocalPath() {
		return localPath;
	}



	public void setLocalPath(Queue<MapTuple> localPath) {
		this.localPath = localPath;
	}



	public int getGoldWorth() {
		return goldWorth;
	}



	public void setGoldWorth(int goldWorth) {
		this.goldWorth = goldWorth;
	}



	public void setSpawnDelay(int spawnDelay) {
		this.spawnDelay = spawnDelay;
	}
}
