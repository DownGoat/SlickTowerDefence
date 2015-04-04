package code.datastructures;

public class HP {
	private static int HP = 30;
	
	private HP() {}

	public static int getHP() {
		return HP;
	}
	
	public static void decrement() {
		HP -= 1;
	}
}
