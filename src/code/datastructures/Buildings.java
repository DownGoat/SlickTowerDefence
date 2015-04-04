package code.datastructures;

import java.util.LinkedList;

import code.entities.towers.Tower;

/**
 * Singleton used to access the buildings.
 * @author dwg
 *
 */
public class Buildings {
	private static LinkedList<Tower> towers = null;
	
	public static LinkedList<Tower> getTowers() {
		if (towers == null) {
			towers = new LinkedList<Tower>();
		}
		
		return towers;
	}
	
	public static void add(Tower tower) {
		if (towers == null) {
			towers = new LinkedList<Tower>();
		}
		
		towers.add(tower);
	}
	
	private Buildings(){};

}
