package circularOrbit;

import track.Track;

import java.util.*;

public abstract class ListCircularOrbit<L extends PhysicalObject, E extends PhysicalObject> extends ConcreteCircularOrbit<L, E> {
	private Map<Track<E>, List<E>> tracks = new TreeMap<>(Comparator.comparingDouble(o -> o.R));
	
	@Override
	public boolean addTrack(double r){
		return !findTrack(r) && null == tracks.put(new Track<>(r), new ArrayList<>());
	}
	
	@Override
	public boolean addObject(E newObject){
		double r = newObject.getR();
		List<E> re = tracks.get(Track.std(r));
		if(re == null){
			addTrack(r);
			List<E> tmp = tracks.get(Track.std(r));
			assert tmp != null;
			tmp.add(newObject);
			return null == tracks.put(new Track<>(r), tmp);
		}
		else{
			return re.add(newObject);
		}
	}
	
	@Override
	public Iterator<E> iterator() {
		return new iter();
	}
	
	@Override
	Map<Track<E>, List<E>> getTrack() {
		return tracks;
	}
	
	class iter implements Iterator<E>{
		private Iterator<Map.Entry<Track<E>, List<E>>> pTrack = getTrack().entrySet().iterator();
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
