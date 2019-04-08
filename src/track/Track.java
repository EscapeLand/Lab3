package track;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Track<E> {
	private final float r;
	private Map<E, Float> orbit = new HashMap<>();
	
	public Track(float r){
		this.r = r;
	}
	
	public boolean move(E which, float newPos){
		return orbit.containsKey(which) && null != orbit.put(which, newPos);
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Track<?> track = (Track<?>) o;
		return Float.compare(track.r, r) == 0;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r);
	}
	
	public float getR() {
		return r;
	}
	
	public boolean addObject(E newObj, float pos){
		return !orbit.containsKey(newObj) && null == orbit.put(newObj, pos);
	}
	
	public boolean removeObject(E obj){
		return null != orbit.remove(obj);
	}
}