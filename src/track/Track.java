package track;

import circularOrbit.PhysicalObject;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class Track<E extends PhysicalObject> {
	public final float R;
	
	public Track(float R){
		this.R = R;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Track<?> track = (Track<?>) o;
		return Float.compare(track.R, R) == 0;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(R);
	}
	
	
	public static<E extends PhysicalObject> boolean addObject(Set<E> orbit, E newObj){
		return !orbit.contains(newObj) && orbit.add(newObj);
	}
	
	public static<E extends PhysicalObject> boolean removeObject(Collection<E> orbit, E obj){
		return orbit.remove(obj);
	}
	
	public static<E extends PhysicalObject> Track<E> std(float R){
		return new Track<>(R);
	}
}