package circularOrbit;

import graph.Graph;

import java.io.IOException;
import java.util.Iterator;

public interface CircularOrbit<L extends Object, E extends PhysicalObject> extends Iterable<E>{
	public static<L, E extends PhysicalObject> CircularOrbit<L, E> empty_set() {
		return new SetCircularOrbit<>();
	}
	
	public static<L, E extends PhysicalObject> CircularOrbit<L, E> empty_list() {
		return new ListCircularOrbit<>();
	}

	public boolean addTrack(float r);
	public boolean removeTrack(float r);
	
	public L changeCentre(L newCenter);
	
	public boolean addObject(float r, E newObject);
	public boolean removeObject(float r, E obj);
	
	public boolean addRelation(E a, E b);
	public Graph<Object> getGraph();
	
	public boolean loadFromFile(String path) throws IOException;

	Iterator<E> iterator();
}
