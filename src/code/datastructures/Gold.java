package code.datastructures;

public class Gold {
	private static int gold = 15;
	
	private Gold() {}

	public static int getGold() {
		return gold;
	}
	
	public static void increase(int amount) {
		gold += amount;
	}
	
	public static void decrease(int amount) {
		gold -= amount;
	}
}
