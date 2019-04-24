package circularOrbit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import track.Track;

import java.util.*;

public abstract class SetCircularOrbit<L extends PhysicalObject, E extends PhysicalObject> extends ConcreteCircularOrbit<L, E>{
	protected Map<Track<E>, Set<E>> tracks = new TreeMap<>(Comparator.comparingDouble(o -> o.R));
	
	@Override
	public boolean addTrack(double r){
		return !findTrack(r) && null == tracks.put(new Track<>(r), new TreeSet<>(Comparator.comparingDouble(PhysicalObject::getPos)));
	}
	
	@Override
	public boolean addObject(@NotNull E newObject){
		double r = newObject.getR();
		Set<E> re = tracks.get(Track.std(r));
		if(re == null) {
			assert addTrack(r);
			re = tracks.get(Track.std(r));
			assert re != null;
		}
		return re.add(newObject);
	}
	
	@Override
	public Set<E> getObjectsOnTrack(double r) {
		return new TreeSet<>(tracks.get(Track.std(r)));
	}
	
	@Override @NotNull
	public Iterator<E> iterator() {
		return new iter();
	}
	
	@Override
	protected Map<Track<E>, Set<E>> getTrack() {
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
