package circularOrbit;

import graph.Graph;
import org.jetbrains.annotations.Nullable;
import track.Track;

import java.util.*;
import java.util.function.Function;

public abstract class ConcreteCircularOrbit<L extends PhysicalObject, E extends PhysicalObject> implements CircularOrbit<L, E>{
	private Graph<PhysicalObject> relationship = Graph.empty();
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
	
	boolean findTrack(float r){
		return getTrack().containsKey(Track.std(r));
	}
	
	
	@Override
	public boolean removeObject(E obj){
		Object re = getTrack().get(Track.std(obj.getR()));
		if(re instanceof List || re instanceof Set){
			return Track.removeObject((Collection<E>) re, obj);
		}
		return false;
	}
	
	@Override
	public boolean addRelation(PhysicalObject a, PhysicalObject b, float val){
		relationship.add(a);
		relationship.add(b);
		return 0 == relationship.set(a, b, val) && 0 ==relationship.set(b, a, val);
	}
	@Override
	public Graph<PhysicalObject> getGraph(){
		return relationship;
	}
	
	@Override @Nullable
	public PhysicalObject query(String name){
		if(centre.getName().equals(name)) return centre;
		for(var i: getTrack().values()){
			assert i instanceof List || i instanceof Set;
			E anIf = find_if((Collection<E>) i, name, PhysicalObject::getName);
			if(anIf != null) return anIf;
		}
		return null;
	}
	
	public static<E, Q> E find_if(Collection<E> col, Q value, Function<E, Q> pred){
		for(E i: col) if(Objects.equals(value, pred.apply(i))) return i;
		return null;
	}
}