package appliactions;

import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.PhysicalObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StellarSystem extends ConcreteCircularOrbit<FixedStar, Planet> {
	@Override
	public boolean loadFromFile(String path) throws IOException {
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		for(String buffer = reader.readLine(); buffer != null; buffer = reader.readLine()) {
			if(buffer.isEmpty()) continue;
			Matcher m = Pattern.compile("([a-zA-Z]+)\\s?::=\\s?<(.*)>").matcher(buffer);
			if(!m.find() || m.groupCount() != 2){
				System.out.println("warning: regex: group count != 2, continued. ");
				continue;
			}
			switch (m.group(1)){
				case "Stellar":
				{
					String[] list = m.group(2).split(",");
					if(list.length != 3){
						System.out.println("warning: regex: Stellar: not 3 args. continued. ");
						continue;
					}
					FixedStar f = new FixedStar(list[0], Float.valueOf(list[1]), Double.valueOf(list[2]));
					changeCentre(f);
					break;
				}
				case "Planet":
				{
					String[] list = m.group(2).split(",");
					if(list.length != 8){
						System.out.println("warning: regex: Planet: not 8 args. continued. ");
						continue;
					}
					Planet p = new Planet(list[0], Enum.valueOf(Planet.Form.class, list[1]), list[2], Float.valueOf(list[3]),
							Float.valueOf(list[4]), Double.valueOf(list[5]), Enum.valueOf(Planet.Dir.class, list[6]), Float.valueOf(list[7]));
					addTrack(Float.valueOf(list[4]));
					if(!addObject(Float.valueOf(list[4]), p))
						System.out.println("warning: failed to add " + list[0]);
					break;
				}
				default:
					System.out.println("warning: regex: unexpected key: " + m.group(1));
			}
		}
		return true;
	}
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

final class Planet extends PhysicalObject {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Planet)) return false;
		if (!super.equals(o)) return false;
		Planet planet = (Planet) o;
		return Float.compare(planet.getR(), getR()) == 0 &&
				Double.compare(planet.v, v) == 0 &&
				name.equals(planet.name) &&
				color.equals(planet.color) &&
				getForm() == planet.getForm();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, color, getForm(), getR(), v);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	enum Form{
		Solid, Liquid, Gas
	}
	enum Dir{
		CW, CCW
	}
	private final String name;
	public final String color;
	private final Form form;
	public final float r;
	public final double v;
	
	public Form getForm() {
		return form;
	}
	
	public Planet(String name, Form form, String color, float r, float R, double v, Dir dir, Float pos) {
		super(R, pos);
		this.name = name;
		this.color = color;
		this.form = form;
		this.r = r;
		this.v = dir == Dir.CCW ? v : -v;
	}
}