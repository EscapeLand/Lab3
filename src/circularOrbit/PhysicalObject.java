package circularOrbit;

import java.util.Comparator;
import java.util.Objects;

public abstract class PhysicalObject {
	private final String name;
	private double R = -1;
	private double pos = 0;
	
	public PhysicalObject(String name, double r, double pos) {
		this.name = name;
		this.R = r;
		this.pos = pos;
	}
	
	public PhysicalObject(String name) {
		this.name = name;
	}
	
	public final double getR() {
		return R;
	}
	
	public void setR(double r) { R = r; }
	
	public final double getPos() {
		return pos;
	}
	
	public void setPos(double pos) { this.pos = pos; }
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString() {
		return "PhysicalObject{" + R +
				", " + pos + '}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PhysicalObject)) return false;
		PhysicalObject that = (PhysicalObject) o;
		return getName().equals(that.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
	
	public static Comparator<PhysicalObject> getDefaultComparator(){
		return (o1, o2) -> o1.R < o2.R ? 1 :
				o1.R > o2.R ? -1: Double.compare(o1.pos, o2.pos);
	}
}

