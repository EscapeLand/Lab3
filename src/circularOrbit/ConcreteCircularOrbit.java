package circularOrbit;

import graph.Graph;
import org.jetbrains.annotations.NotNull;
import track.Track;

import java.util.*;

import static APIs.CircularOrbitAPIs.find_if;

public abstract class ConcreteCircularOrbit<L extends PhysicalObject, E extends PhysicalObject> implements CircularOrbit<L, E>{
	private Graph<PhysicalObject> relationship = Graph.empty();
	private L centre = null;
	
	abstract <C extends Collection<E>> Map<Track<E>, C> getTrack();

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
	
	boolean findTrack(double r){
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
	public boolean addRelation(@NotNull PhysicalObject a, @NotNull PhysicalObject b, float val){
		relationship.add(a);
		relationship.add(b);
		return 0 == relationship.set(a, b, val) && 0 ==relationship.set(b, a, val);
	}
	@Override
	public Graph<PhysicalObject> getGraph(){
		return relationship;
	}
	
	@Override
	public PhysicalObject query(String name){
		if(centre.getName().equals(name)) return centre;
		for(var i: getTrack().values()){
			assert i instanceof List || i instanceof Set;
			E anIf = find_if(i, (p)-> Objects.equals(p.getName(), name));
			if(anIf != null) return anIf;
		}
		return null;
	}
	
	@Override
	public L center() {
		return centre;
	}
	
	@Override
	public Collection<E> getObjectsOnTrack(double r) {
		return getTrack().get(Track.std(r));
	}
	
	@Override
	public Set<Double> getTracks() {
		Set<Double> r = new TreeSet<>();
		getTrack().keySet().forEach(t->r.add(t.R));
		
		return r;
	}
}