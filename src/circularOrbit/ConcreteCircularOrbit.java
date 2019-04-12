package circularOrbit;

import graph.Graph;
import org.jetbrains.annotations.NotNull;
import track.Track;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class ConcreteCircularOrbit<L, E extends PhysicalObject> implements CircularOrbit<L, E>{
	private Map<Track<E>, Set<E>> tracks = new TreeMap<>((o1, o2) -> Float.compare(o1.R, o2.R));
	private Graph<Object> relationship = Graph.empty();
	private L centre = null;
	
	@Override
	public boolean addTrack(float r){
		return !findTrack(r) && null == tracks.put(new Track<>(r), new TreeSet<>((o1, o2) -> Float.compare(o1.getPos(), o2.getPos())));
	}
	@Override
	public boolean removeTrack(float r){
		return null != tracks.remove(Track.std(r));
	}
	
	@Override
	public L changeCentre(L newCenter){
		L prev = centre;
		centre = newCenter;
		return prev;
	}
	
	private boolean findTrack(float r){
		return tracks.containsKey(Track.std(r));
	}
	
	@Override
	public boolean addObject(float r, E newObject){
		Set<E> re = tracks.get(Track.std(r));
		if(re == null){
			addTrack(r);
			Set<E> tmp = tracks.get(Track.std(r));
			assert tmp != null;
			tmp.add(newObject);
			return null == tracks.put(new Track<>(r), tmp);
		}
		else{
			if(re.contains(newObject)) return false;
			else return re.add(newObject);
		}
	}
	@Override
	public boolean removeObject(float r, E obj){
		Set<E> re = tracks.get(Track.std(r));
		if(re == null) return false;
		return Track.removeObject(re, obj);
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
	
	public int size(){
		int sum = 0;
		for (Set<E> track : tracks.values()) {
			sum += track.size();
		}
		return sum;
	}
	
	@NotNull @Override
	public Iterator<E> iterator() {
		class iter implements Iterator<E>{
			private Iterator<Map.Entry<Track<E>, Set<E>>> pTrack = tracks.entrySet().iterator();
			private Iterator<E> pObj = pTrack.next().getValue().iterator();
			
			@Override
			public boolean hasNext() {
				return pTrack.hasNext() || pObj.hasNext();
			}
			
			@Override
			public E next() {
				if(pObj.hasNext()) return pObj.next();
				else if(pTrack.hasNext()) {
					pObj = pTrack.next().getValue().iterator();
					return pObj.next();
				}
				else return null;
			}
			
			@Override
			public void remove() {
			
			}
		}
		return new iter();
	}
	
	public static<T, R> T find_if(Collection<T> col, R obj, Function<T, R> pred){
		for (T t : col) {
			if(Objects.equals(obj, pred.apply(t))) return t;
		}
		return null;
	}
	
	public static<Tl, Tr, R> Tr find_if(Map<Tl, Tr> col, R obj, Function<Tl, R> pred){
		for (Map.Entry<Tl, Tr> t : col.entrySet()) {
			if(Objects.equals(obj, pred.apply(t.getKey()))) return t.getValue();
		}
		return null;
	}
}