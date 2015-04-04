package code.entities.creeps;

import java.util.Queue;

import code.MapTuple;

public class Slime extends Creep {

	public Slime(Queue<MapTuple> path, String imgPath) {
		super(path, imgPath);
		velocity = 1000;
		spawnDelay = velocity;
		hp = 25;
		goldWorth = 2;
		rotate = false;
	}

}
