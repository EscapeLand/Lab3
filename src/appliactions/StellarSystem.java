package appliactions;

import circularOrbit.ConcreteCircularOrbit;

import java.util.Objects;

public class StellarSystem extends ConcreteCircularOrbit<FixedStar, Planet> {

}

final class FixedStar{
	public final String name;
	public final float r;
	public final double m;
	
	public FixedStar(String name, float r, double m) {
		this.name = name;
		this.r = r;
		this.m = m;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FixedStar fixedStar = (FixedStar) o;
		return name.equals(fixedStar.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}

final class Planet{
	enum Form{
		Solid
	}
	enum Dir{
		CW, CCW
	}
	public final String name;
	public final String color;
	private final Form form;
	public final float r;
	public final double m;
	public final double v;
	
	public Form getForm() {
		return form;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Planet planet = (Planet) o;
		return name.equals(planet.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	public Planet(String name, String color, Form form, float r, double m, Dir dir, double v) {
		this.name = name;
		this.color = color;
		this.form = form;
		this.r = r;
		this.m = m;
		this.v = dir == Dir.CCW ? v : -v;
	}
}