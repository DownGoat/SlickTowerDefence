package code.entities.creeps;

import java.util.Queue;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import code.MapTuple;

public class Rat extends Creep {

	public Rat(Queue<MapTuple> path, String img) {
		super(path, img);
		velocity = 600;
		spawnDelay = velocity;
		hp = 15;
		goldWorth = 1;
		
		// Freezes the game when pressing ready
		//animation.setSpeed(velocity);
	}
	
	

}
