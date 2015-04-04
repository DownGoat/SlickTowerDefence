package code.datastructures;

import java.util.LinkedList;

import code.entities.Projectile;
import code.entities.towers.Tower;

/**
 * Singleton used to access the buildings.
 * @author dwg
 *
 */
public class Projectiles {
	private static LinkedList<Projectile> projectiles = null;
	
	public static LinkedList<Projectile> getProjectiles() {
		if (projectiles == null) {
			projectiles = new LinkedList<Projectile>();
		}
		
		return projectiles;
	}
	
	public static void add(Projectile projectile) {
		if (projectiles == null) {
			projectiles = new LinkedList<Projectile>();
		}
		
		projectiles.add(projectile);
	}
	
	private Projectiles(){};

}
