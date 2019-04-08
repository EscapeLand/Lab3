package circularOrbit;

import org.jetbrains.annotations.Nullable;
import track.Track;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E>{
	private Set<Track<E>> tracks = new HashSet<>();
	private Map<Object, Object> relationship = new HashMap<>();
	private L centre = null;
	
	public boolean addTrack(float r){
		return tracks.add(new Track<>(r));
	}
	public boolean removeTrack(int r){
		return tracks.removeIf(x -> x.getR() == r);
	}
	
	public L changeCentre(L newCenter){
		L prev = centre;
		centre = newCenter;
		return prev;
	}
	
	@Nullable
	private Track<E> findTrack(int r){
		for (Track<E> track : tracks) {
			if(track.getR() == r) return track;
		}
		return null;
	}
	
	public boolean addObject(int r, E newObject, float pos){
		Track re = findTrack(r);
		if(re == null) return false;
		
		return re.addObject(newObject, pos);
	}
	public boolean removeObject(int r, E obj){
		Track re = findTrack(r);
		if(re == null) return false;
		
		return re.removeObject(obj);
	}
	
	public boolean addRelation(E a, E b){
		return null == relationship.put(a, b);
	}
	
	public boolean loadFromFile(String path){
		return true;
	}
}
