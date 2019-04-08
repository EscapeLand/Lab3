package circularOrbit;

import track.Track;

public interface CircularOrbit<L extends Object, E extends Object> {
	public static<L, E> CircularOrbit<L, E> empty(){
		return new ConcreteCircularOrbit<L, E>();
	}
	
	public boolean addTrack(float r);
	public boolean removeTrack(int r);
	
	public L changeCentre(L newCenter);
	
	public boolean addObject(int r, E newObject, float pos);
	public boolean removeObject(int r, E obj);
	
	public boolean addRelation(E a, E b);
	
	public boolean loadFromFile(String path);
}
