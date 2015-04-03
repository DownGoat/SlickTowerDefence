package code.entities;

public abstract class Entity {
	protected float x;
	protected float y;
	protected float xspeed;
	protected float yspeed;
	
	public Entity() {
		
	}
	
	public Entity(float x, float y, float xspeed, float yspeed) {
		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
	}
	
	public abstract void draw();


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public float getXspeed() {
		return xspeed;
	}


	public void setXspeed(float xspeed) {
		this.xspeed = xspeed;
	}


	public double getYspeed() {
		return yspeed;
	}


	public void setYspeed(float yspeed) {
		this.yspeed = yspeed;
	}

	public abstract void logic(int delta);
	
}
