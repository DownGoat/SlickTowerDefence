package code.entities;

import java.util.LinkedList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import code.entities.creeps.Creep;


public class Tower extends Entity {
	public Image img;
	private long lastShot = 0;
	private LinkedList<Creep> creeps;
	private LinkedList<Projectile> projectiles;
	
	public Tower(float x, float y, LinkedList<Creep> creeps, LinkedList<Projectile> projectiles) {
		this.x = x * 32;
		this.y = y * 32;
		this.creeps = creeps;
		this.projectiles = projectiles;
		
		try {
			img = new Image("res/tower.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void draw() {
		img.draw(x, y);
	}

	@Override
	public void logic(int delta) {
		lastShot += delta;
		
		if (lastShot >= 1000) {
			fireShot();
			lastShot = 0;
		}
	}
	
	public void fireShot() {
		Creep closest = creeps.getFirst();
		float closestDistance = (float) Math.sqrt(Math.pow((this.x - closest.getX()), 2) + Math.pow((this.y - closest.getY()), 2));
		
		for(Creep creep: creeps) {
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
		projectiles.add(new Projectile(rotation, x + 12, y + 8, Xdirection, Ydirection));
		
		
		
	}

}
