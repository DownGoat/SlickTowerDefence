package code.entities;

import org.newdawn.slick.geom.Rectangle;

public abstract class CollisionEntity extends Entity {
	protected Rectangle collisionRect;
	

	public boolean collision(Rectangle otherRect) {
		return collisionRect.intersects(otherRect);
	}
	
	public Rectangle getCollisionRect() {
		return collisionRect;
	}
}
