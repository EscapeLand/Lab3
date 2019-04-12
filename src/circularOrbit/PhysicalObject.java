package circularOrbit;

import java.util.Objects;

public abstract class PhysicalObject {
	private final float R;
	private final float pos;
	
	public PhysicalObject(float r, float pos) {
		R = r;
		this.pos = pos;
	}
	
	public final float getR() {
		return R;
	}
	
	public final float getPos() {
		return pos;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PhysicalObject)) return false;
		PhysicalObject that = (PhysicalObject) o;
		return Float.compare(that.getR(), getR()) == 0;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getR());
	}
	
	public abstract String getName();
}
