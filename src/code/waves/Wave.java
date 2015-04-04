package code.waves;

import java.util.LinkedList;
import java.util.Queue;

import code.MapTuple;
import code.entities.creeps.Creep;

public abstract class Wave {	
	public abstract void initWave(Queue<MapTuple> path);
}
