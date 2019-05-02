package circularOrbit;

import java.util.Comparator;
import java.util.Objects;

public abstract class PhysicalObject {
	private final String name;
	protected final double R_init;
	protected final double pos_init;
	private double R;
	private double pos;
	private static int num = 0;
	
	public PhysicalObject(String name, double r, double pos) {
		this.name = name;
		this.R_init = this.R = r;
		this.pos_init = this.pos = pos;
	}
	
	public PhysicalObject(String name, double r) {
		this.name = name;
		this.R_init = this.R = r;
		this.pos_init = this.pos = num < 12 ? 30 * num + 30 * Math.random()
						: 360 * Math.random();
		num++;
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
		return Double.compare(that.R_init, R_init) == 0 &&
				Double.compare(that.pos_init, pos_init) == 0 &&
				getName().equals(that.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName(), R_init, pos_init);
	}
	
	public static Comparator<PhysicalObject> getDefaultComparator(){
		return (o1, o2) -> o1.R_init < o2.R_init ? 1 :
				o1.R_init > o2.R_init ? -1: Double.compare(o1.pos_init, o2.pos_init);
	}
}

