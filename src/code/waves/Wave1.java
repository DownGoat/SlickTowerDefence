package code.waves;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import code.MapTuple;
import code.entities.creeps.Creep;
import code.entities.creeps.Rat;

public class Wave1 extends Wave {

	public LinkedList<Creep> initWave(Queue<MapTuple> path) {
		LinkedList<Creep> creeps = new LinkedList<Creep>();
		Image img = null;
		
		try {
			img = new Image("res/rat.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int x = 0; x < 5; x++) {
			creeps.add((Creep) new Rat(path, img));
		}
		
		return creeps;
	}
}
