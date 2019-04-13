package circularOrbit;

import graph.Graph;
import org.jetbrains.annotations.NotNull;
import track.Track;

import java.io.IOException;
import java.util.*;

public abstract class ConcreteCircularOrbit<L, E extends PhysicalObject> implements CircularOrbit<L, E>{
	private Graph<Object> relationship = Graph.empty();
	private L centre = null;
	
	abstract Map getTrack();
	
	@Override
	public boolean removeTrack(float r){
		return null != getTrack().remove(Track.std(r));
	}
	
	@Override
	public L changeCentre(L newCenter){
		L prev = centre;
		centre = newCenter;
		return prev;
	}
	
	protected boolean findTrack(float r){
		return getTrack().containsKey(Track.std(r));
	}
	
	
	@Override
	public boolean removeObject(float r, E obj){
		Object re = getTrack().get(Track.std(r));
		if(re instanceof List || re instanceof Set){
			return Track.removeObject((Collection<E>) re, obj);
		}
		return false;
	}
	
	@Override
	public boolean addRelation(E a, E b){
		return 0 == relationship.set(a, b, 1) && 0 ==relationship.set(b, a, 1);
	}
	@Override
	public Graph<Object> getGraph(){
		return relationship;
	}
	
	@Override
	public boolean loadFromFile(String path) throws IOException {
		return true;
	}
}