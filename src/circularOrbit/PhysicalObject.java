package circularOrbit;

import java.util.Objects;

public abstract class PhysicalObject {
	private final double R;
	private final double pos;
	
	public PhysicalObject(double r, double pos) {
		R = r;
		this.pos = pos;
	}
	
	public final double getR() {
		return R;
	}
	
	public final double getPos() {
		return pos;
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
		return Double.compare(that.getR(), getR()) == 0;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getR());
	}
	
	public abstract String getName();
	public abstract PhysicalObject changeR(double newr);
}

