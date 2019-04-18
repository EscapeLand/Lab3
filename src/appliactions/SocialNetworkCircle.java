package appliactions;

import circularOrbit.PhysicalObject;
import circularOrbit.SetCircularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static APIs.CircularOrbitAPIs.find_if;

public final class SocialNetworkCircle extends SetCircularOrbit<CentralUser, User> {
	@Override
	public boolean loadFromFile(String path) throws IOException {
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<Quad> params = new ArrayList<>();
		Set<String[]> record = new HashSet<>();
		String center = null;
		for(String buffer = reader.readLine(); buffer != null; buffer = reader.readLine()) {
			if(buffer.isEmpty()) continue;
			Matcher m = Pattern.compile("([a-zA-Z]+)\\s?::=\\s?<(.*)>").matcher(buffer);
			if(!m.find() || m.groupCount() != 2){
				System.out.println("warning: regex: group count != 2, continued. ");
				continue;
			}
			List<String> list = new ArrayList<>(Arrays.asList(m.group(2).split("\\s*,\\s*")));
			if(list.size() != 3){
				System.out.println("warning: regex: not 3 args. continued. ");
				continue;
			}
			switch (m.group(1)){
				case "CentralUser":
					center = list.get(0);
					list.add(0, "CentralUser");
					changeCentre((CentralUser) PhysicalObjectFactory.produce(list.toArray(new String[0])));
					break;
				case "Friend":
					Quad anIf = find_if(params, q -> Objects.equals(q.name, list.get(0)));
					if(anIf == null){
						params.add(new Quad(list.get(0), Integer.valueOf(list.get(1)), Enum.valueOf(Gender.class, list.get(2))));
					}
					else{
						anIf.name = list.get(0);
						anIf.age = Integer.valueOf(list.get(1));
						anIf.gender = Enum.valueOf(Gender.class, list.get(2));
					}
					break;
				case "SocialTie":
					record.add(list.toArray(new String[0]));
					break;
				default:
					System.out.println("warning: regex: unexpected key: " + m.group(1));
			}
		}
		reader.close();
		assert center != null;
		
		boolean flag = false;
		do{
			flag = false;
			for(String[] list: record){
				Quad anIf = list[0].equals(center) ? new Quad() : find_if(params, q -> q.name.equals(list[0]));
				if(anIf == null){
					System.out.println("vertex not include: " + list[0]);
					continue;
				}
				if(anIf.r == -1) continue;
				Quad anIf2 = find_if(params, q -> q.name.equals(list[1]));
				if(anIf2 == null){
					System.out.println("vertex not include: " + list[0]);
					continue;
				}
				if(anIf2.r == -1 || anIf2.r > anIf.r + 1){
					flag = true;
					anIf2.r = anIf.r + 1;
				}
			}
			if(!flag) break;
		} while(null != find_if(params, q->q.r == -1));
		
		params.forEach(q->{assert addObject(q.toUser());});
		
		for (String[] list : record) {
			PhysicalObject q1 = query(list[0]);
			PhysicalObject q2 = query(list[1]);
			assert q1 != null && q2 != null;
			addRelation(q1, q2, Float.valueOf(list[2]));
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "SocialNetworkCircle";
	}
}

enum Gender{
	M, F
}

final class User extends PhysicalObject {
	User(Double r, String name, int age, Gender gender) {
		super(r, 360 * Math.random());
		this.Name = name;
		this.gender = gender;
		this.age = age;
	}
	
	private Gender getGender() {
		return gender;
	}
	
	private int getAge() {
		return age;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		if (!super.equals(o)) return false;
		User user = (User) o;
		return getAge() == user.getAge() &&
				getName().equals(user.getName()) &&
				getGender() == user.getGender();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getName(), getGender(), getAge());
	}
	
	private final String Name;
	private final Gender gender;
	private final int age;

	
	@Override
	public String getName() {
		return Name;
	}
	
	@Override
	public PhysicalObject changeR(double newr) {
		return new User(newr, Name, age, gender);
	}
	
	@Override
	public String toString() {
		return "<" + Name +
				", " + age +
				", " + gender.toString() +
				'>';
	}
}

final class CentralUser extends PhysicalObject{
	private final String Name;
	private final Gender gender;
	private final int age;
	
	CentralUser(String name, int age, Gender gender) {
		super(0, 0);
		Name = name;
		this.gender = gender;
		this.age = age;
	}
	
	public String getName() {
		return Name;
	}
	
	@Override
	public PhysicalObject changeR(double newr) {
		throw new RuntimeException("changeR: center");
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public int getAge() {
		return age;
	}
	
	@Override
	public String toString() {
		return "<" + Name +
				", " + age +
				", " + gender.toString() +
				'>';
	}
}

final class Quad{
	int r = -1;
	String name;
	int age;
	Gender gender;
	
	Quad(String name, int age, Gender gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	
	Quad() { r = 0; }
	
	User toUser(){
		return new User(((double) r), name, age, gender);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Quad)) return false;
		Quad quad = (Quad) o;
		return age == quad.age &&
				name.equals(quad.name) &&
				gender == quad.gender;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, age, gender);
	}
}