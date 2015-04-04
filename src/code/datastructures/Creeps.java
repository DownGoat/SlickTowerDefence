package code.datastructures;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import code.entities.creeps.Creep;
import code.entities.towers.Tower;

/**
 * Singleton used to access the buildings.
 * @author dwg
 *
 */
public class Creeps {
	private static LinkedList<Creep> creeps = null;
	
	public static LinkedList<Creep> getCreeps() {
		if (creeps == null) {
			creeps = new LinkedList<Creep>();
		}
		
		return creeps;
	}
	
	public static void add(Creep tower) {
		if (creeps == null) {
			creeps = new LinkedList<Creep>();
		}
		
		creeps.add(tower);
	}
	
	public static void clear() {
		if (creeps == null) {
			creeps = new LinkedList<Creep>();
		}
		
		creeps.clear();
	}
	
	public static void shuffel() {
		Collections.shuffle(creeps);
	}
	
	private Creeps(){};

}
