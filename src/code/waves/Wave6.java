package code.waves;

import java.util.LinkedList;
import java.util.Queue;

import code.MapTuple;
import code.datastructures.Creeps;
import code.entities.creeps.Creep;
import code.entities.creeps.Rat;
import code.entities.creeps.Slime;

public class Wave6 extends Wave {

	@Override
	public void initWave(Queue<MapTuple> path) {
LinkedList<Creep> creeps = Creeps.getCreeps();		
		
		for (int x = 0; x < 40; x++) {
			creeps.add((Creep) new Rat(path, "res/rat/rat_animation.png"));
		}
		
		for (int x = 0; x < 40; x++) {
			creeps.add((Creep) new Slime(path, "res/slime/slime_animation.png"));
		}
		
		Creeps.shuffel();
	}

}
