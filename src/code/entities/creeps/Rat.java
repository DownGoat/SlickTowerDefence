package code.entities.creeps;

import java.util.Queue;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import code.MapTuple;

public class Rat extends Creep {

	public Rat(Queue<MapTuple> path, Image img) {
		super(path, img);
		velocity = 750;
		spawnDelay = velocity;
		hp = 5;
	}
	
	

}
