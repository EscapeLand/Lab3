package track;

import circularOrbit.PhysicalObject;

import java.util.*;

public class Track<E extends PhysicalObject> {
	private final double[] R;
	static public final Comparator<Track> defaultComparator = (a, b) -> Arrays.compare(a.R, b.R);
	
	public Track(double[] R){
		switch (R.length){
			case 1: this.R = new double[]{R[0], R[0]}; break;
			case 2:
				var max = Math.max(R[0], R[1]);
				var min = Math.min(R[0], R[1]);
				this.R = new double[]{max, min};
				break;
			default: throw new IllegalArgumentException("length of R: " + R.length);
		}
	}
	
	public Track(Double[] R){
		switch (R.length){
			case 1: this.R = new double[]{R[0], R[0]}; break;
			case 2:
				var max = Math.max(R[0], R[1]);
				var min = Math.min(R[0], R[1]);
				this.R = new double[]{max, min};
				break;
			default: throw new IllegalArgumentException("length of R: " + R.length);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Track)) return false;
		Track<?> track = (Track<?>) o;
		return Arrays.equals(track.R, R);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(R[0], R[1]);
	}
	
	public static<E extends PhysicalObject> boolean addObject(Set<E> orbit, E newObj){
		return !orbit.contains(newObj) && orbit.add(newObj);
	}
	
	public static<E extends PhysicalObject> boolean removeObject(Collection<E> orbit, E obj){
		return orbit.remove(obj);
	}
	
	@Override
	public String toString() {
		if(R[0] == R[1]) return String.valueOf(R[1]);
		else return Arrays.toString(R);
	}
	
	public static int compare(Track a, Track b){
		return defaultComparator.compare(a, b);
	}
	
	public double[] getRect() {
		return R.clone();
	}
	
	public Double[] getRect_alt(){
		return new Double[]{R[0], R[1]};
	}
}