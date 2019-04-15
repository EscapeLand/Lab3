package circularOrbit;

import graph.Graph;

import java.io.IOException;
import java.util.Iterator;

public interface CircularOrbit<L extends PhysicalObject, E extends PhysicalObject> extends Iterable<E>{
	public boolean addTrack(float r);
	public boolean removeTrack(float r);
	
	public L changeCentre(L newCenter);
	
	public boolean addObject(E newObject);
	public boolean removeObject(E obj);
	
	public boolean addRelation(PhysicalObject a, PhysicalObject b, float val);
	public Graph<PhysicalObject> getGraph();
	
	public boolean loadFromFile(String path) throws IOException;
	
	public PhysicalObject query(String name);

	Iterator<E> iterator();
}
