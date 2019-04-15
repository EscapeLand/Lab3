package circularOrbit;

import track.Track;

import java.util.*;

public abstract class SetCircularOrbit<L extends PhysicalObject, E extends PhysicalObject> extends ConcreteCircularOrbit<L, E>{
	private Map<Track<E>, Set<E>> tracks = new TreeMap<>((o1, o2)->Float.compare(o1.R, o2.R));
	
	@Override
	public boolean addTrack(float r){
		return !findTrack(r) && null == tracks.put(new Track<>(r), new TreeSet<>((o1, o2) -> Float.compare(o1.getPos(), o2.getPos())));
	}
	
	@Override
	public boolean addObject(E newObject){
		float r = newObject.getR();
		Set<E> re = tracks.get(Track.std(r));
		if(re == null) {
			assert addTrack(r);
			re = tracks.get(Track.std(r));
			assert re != null;
		}
		return re.add(newObject);
	}
	
	@Override
	public Iterator<E> iterator() {
		return new iter();
	}
	
	@Override
	Map<Track<E>, Set<E>> getTrack() {
		return tracks;
	}
	
	class iter implements Iterator<E>{
		private Iterator<Map.Entry<Track<E>, Set<E>>> pTrack = getTrack().entrySet().iterator();
		private Iterator<E> pObj = pTrack.hasNext() ? pTrack.next().getValue().iterator() : null;
		
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
			pTrack.remove();
		}
	}
}
