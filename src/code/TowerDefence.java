package code;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public class TowerDefence  {
	public static void main(String[] args) {
        System.out.println("Starting Game...");
		AppGameContainer app;
		try {
			app = new AppGameContainer(new TowerDefenceGame(
					"TG15 TowerDefence", 640, 480));
			app.setDisplayMode(640, 480, false);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
