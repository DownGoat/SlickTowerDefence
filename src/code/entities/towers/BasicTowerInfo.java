package code.entities.towers;

public class BasicTowerInfo {
	private static int DMG = 5;
	private static int ATKS = 1000;
	private static int PRICE = 10;
	
	private BasicTowerInfo() {}

	public static int getDMG() {
		return DMG;
	}

	public static int getATKS() {
		return ATKS;
	}

	public static int getPRICE() {
		return PRICE;
	};
}
