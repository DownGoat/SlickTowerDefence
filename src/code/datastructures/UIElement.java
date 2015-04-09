package code.datastructures;



import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class UIElement {
	private Image img;
	private Rectangle rect;
	protected float x;
	protected float y;
	
	public UIElement(String imgPath, float x, float y) {
		try {
			this.img = new Image(imgPath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.rect = new Rectangle(x, y, img.getWidth(), img.getHeight());
		this.x = x;
		this.y = y;
	}
	
	public boolean collision(Rectangle other) {
		return rect.intersects(other);
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public void draw() {
		img.draw(x, y);
	}

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
}
