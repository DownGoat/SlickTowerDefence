package code.waves;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import code.MapTuple;
import code.datastructures.Creeps;
import code.entities.creeps.Creep;
import code.entities.creeps.Rat;

public class Wave1 extends Wave {

	public void initWave(Queue<MapTuple> path) {
		LinkedList<Creep> creeps = Creeps.getCreeps();

		for (int x = 0; x < 5; x++) {
			creeps.add((Creep) new Rat(path, "res/rat/rat_animation.png"));
		}
		
	}
}
