package circularOrbit;

import graph.Graph;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @param <L> center object type.
 * @param <E> object on track.
 */
public interface CircularOrbit<L extends PhysicalObject, E extends PhysicalObject> extends Iterable<E>{
	/**
	 * add a track.
	 * @param r add a track with radius of r.
	 * @return if a track with radius r has already exist, return false; else true.
	 */
	public boolean addTrack(double r);
	
	/**
	 * remove a track, and apparently the objects on the tract are removed together.
	 * @param r the track with radius r to remove
	 * @return if a track with radius r has already exist, return true. else false.
	 */
	public boolean removeTrack(float r);
	
	/**
	 * @param newCenter change the center object to newObject.
	 * @return the previous center object.
	 */
	public L changeCentre(@Nullable L newCenter);
	
	/**
	 * @param newObject add a object to circular orbit.
	 * @return if the object has already exist, return false; else true.
	 */
	public boolean addObject(@NotNull E newObject);
	
	/**
	 * @param obj remove a object from circular orbit.
	 * @return if the object has already exist, return true; else false.
	 */
	public boolean removeObject(@NotNull E obj);
	
	/**
	 * @param a begin of relation.
	 * @param b end of relation
	 * @param val weight of the relation.
	 * @return if the relation is already exist, return false; else return true.
	 */
	public boolean addRelation(PhysicalObject a, PhysicalObject b, float val);
	
	/**
	 * @return return the relation graph of the circular orbit.
	 */
	public @NotNull Graph<PhysicalObject> getGraph();
	
	/**
	 * @param path infers a text file with regulated input.
	 * @return true if the load is complete with no error; else false.
	 * @throws IOException when cannot open the file inferred by path.
	 */
	public boolean loadFromFile(String path) throws IOException;
	
	/**
	 * find a object with its name.
	 * @param name name of the object, either L or E.
	 * @return the object.
	 */
	public PhysicalObject query(String name);
	
	/**
	 * @return all the tracks in circular orbit.
	 */
	public Set<Double> getTracks();
	
	/**
	 * @param r the radius of the track
	 * @return copy of the collection in which objects are on the given track.
	 */
	@Nullable
	public Collection<E> getObjectsOnTrack(double r);
	
	/**
	 * @return the center object.
	 */
	@Nullable
	public L center();
	
	Iterator<E> iterator();
}
