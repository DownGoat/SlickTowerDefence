package code;

/**
 * 
 * @author dwg
 *
 * This class is used to represent a tuple containing the x and y cords
 * of something on the map.
 */
public class MapTuple {
	private int x;
	private int y;
	
	public MapTuple(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
