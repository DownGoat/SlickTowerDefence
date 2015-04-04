package code.entities.towers;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import code.datastructures.Creeps;
import code.datastructures.Projectiles;
import code.entities.Entity;
import code.entities.Projectile;
import code.entities.creeps.Creep;


public  abstract class Tower extends Entity {
	protected SpriteSheet sheet;
	protected Animation animation;
	protected long lastShot = 0;
	protected LinkedList<Projectile> projectiles;
	protected LinkedList<Creep> spawnedCreeps;
	protected static int damage;
	protected static int price;
	protected static int attackSpeed;
	
	
	public Tower(float x, float y, String imgPath) {
		this.x = x * 32;
		this.y = y * 32;
		
		try {
			sheet = new SpriteSheet(imgPath, 32, 32);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		animation = new Animation(sheet, 1000);
		
	}

	@Override
	public void draw() {
		animation.draw(x, y);
	}

	@Override
	public void logic(int delta) {
		lastShot += delta;
		animation.update(delta);
		
		
		if (lastShot >= attackSpeed) {
			fireShot();
			lastShot = 0;
		}
	}
	
	public void fireShot() {
		if (spawnedCreeps.size() == 0) {
			return;
		}
		Creep closest = spawnedCreeps.getFirst();
		float closestDistance = (float) Math.sqrt(Math.pow((this.x - closest.getX()), 2) + Math.pow((this.y - closest.getY()), 2));
		
		for(Creep creep: spawnedCreeps) {
			float distance = (float) Math.sqrt(Math.pow((this.x - creep.getX()), 2) + Math.pow((this.y - creep.getY()), 2));
			if (distance < closestDistance) {
				closestDistance = distance;
				closest = creep;
			}
		}
		
		// +16 so it shoots from the center of the tower.
		double rotation = Math.toDegrees(Math.atan2(closest.getY() - this.y, closest.getX() - this.x));
		rotation += 90;
		
		float Xdirection = closest.getX() - x + 12;
		float Ydirection = closest.getY() - y + 8;
		// Same here.
		Projectiles.getProjectiles().add(this.generateProjectile(rotation, Xdirection, Ydirection));
	}
	
	public void setSpawnedCreeps(LinkedList<Creep> spawnedCreeps) {
		this.spawnedCreeps = spawnedCreeps;
	}
	
	protected abstract Projectile generateProjectile(double rotation, float Xdirection, float Ydirection);

}
