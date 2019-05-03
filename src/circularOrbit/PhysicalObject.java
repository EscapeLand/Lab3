package circularOrbit;

import track.Track;

import java.util.Comparator;
import java.util.Objects;

public abstract class PhysicalObject {
	private final String name;
	protected final Track R_init;
	protected final double pos_init;
	private Track R;
	private double pos;
	private static int num = 0;
	
	public PhysicalObject(String name, double[] r, double pos) {
		this.name = name;
		this.R_init = this.R = new Track(r);
		this.pos_init = this.pos = pos;
	}
	
	public PhysicalObject(String name, double[] r) {
		this.name = name;
		this.R_init = this.R = new Track(r);
		this.pos_init = this.pos = num < 9 ? 40 * num + 40 * Math.random()
						: 360 * Math.random();
		num++;
	}
	
	public final Track getR() {
		return R;
	}
	
	public void setR(double[] r) {
		//TODO
		setR(r);
	}
	
	public void setR(Double[] r) {
		//TODO
		setR(r);
	}
	
	public void setR(Track r) { R = r; }
	
	public final double getPos() {
		return pos;
	}
	
	public void setPos(double pos) { this.pos = pos; }
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + '{' + R +
				", " + pos + '}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PhysicalObject)) return false;
		PhysicalObject that = (PhysicalObject) o;
		return Double.compare(that.pos_init, pos_init) == 0 &&
				getName().equals(that.getName()) &&
				R_init.equals(that.R_init);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName(), R_init, pos_init);
	}
	
	@Override
	public abstract PhysicalObject clone();
	
	public static Comparator<PhysicalObject> getDefaultComparator(){
		return (o1, o2) -> {
			var r = Track.compare(o1.R_init, o2.R_init);
			if(r == 0) return Double.compare(o1.pos_init, o2.pos_init);
			else return r;
		};
	}
}

