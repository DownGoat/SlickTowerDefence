package code.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;


public class Projectile extends CollisionEntity {
	private Image img;
	private Vector2f velocity = new Vector2f(5.0f, 5.0f);
	private Vector2f direction;
	private int damage;
	
	public Projectile(double rotation, float x, float y, float xdirection, float ydirection) {
		this.x = x;
		this.y = y;
		damage = 5;
		direction = new Vector2f(xdirection, ydirection).normalise();

		
		try {
			img = new Image("res/arrow.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		collisionRect = new Rectangle(x, y, img.getWidth(), img.getHeight());
		
		img.setRotation((float) rotation);
	}

	@Override
	public void draw() {
		img.draw(x, y);

	}

	@Override
	public void logic(int delta) {
		this.x += direction.normalise().getX() * 0.5f * delta;
		this.y += direction.normalise().getY() * 0.5f * delta;
		
		collisionRect.setX(x);
		collisionRect.setY(y);
	}
	
	public int getDamage() {
		return damage;
	}

}
