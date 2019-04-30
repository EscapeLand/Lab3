package appliactions;

import circularOrbit.CircularOrbit;
import circularOrbit.PhysicalObject;
import circularOrbit.SetCircularOrbit;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static APIs.CircularOrbitAPIs.find_if;

public final class SocialNetworkCircle extends SetCircularOrbit<CentralUser, User> {
	@Override
	public boolean loadFromFile(String path) throws IOException {
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<User> params = new ArrayList<>();
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
					params.add(new User(list.get(0), Integer.valueOf(list.get(1)),
							Enum.valueOf(Gender.class, list.get(2))));
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
				User anIf = list[0].equals(center) ? new User(0.0, "", 0, null) :
						find_if(params, q -> q.getName().equals(list[0]));
				if(anIf == null){
					System.out.println("vertex not include: " + list[0]);
					continue;
				}
				if(anIf.getR() == -1) continue;
				User anIf2 = find_if(params, q -> q.getName().equals(list[1]));
				if(anIf2 == null){
					System.out.println("vertex not include: " + list[0]);
					continue;
				}
				if(anIf2.getR() == -1 || anIf2.getR() > anIf.getR() + 1){
					flag = true;
					anIf2.setR(anIf.getR() + 1);
				}
			}
			if(!flag) break;
		} while(null != find_if(params, q->q.getR() == -1));
		
		params.forEach(this::addObject);
		
		for (String[] list : record) {
			PhysicalObject q1 = query(list[0]);
			PhysicalObject q2 = query(list[1]);
			assert q1 != null && q2 != null;
			addRelation(q1, q2, Float.valueOf(list[2]));
		}
		
		return true;
	}
	
	@Override
	public void process(Consumer refresh) {
		JFrame frame = new JFrame(getClass().getSimpleName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(null);
		this.test(frame, refresh);
		
		frame.setVisible(true);
	}
	
	@Override
	protected JPanel test(JFrame frame, Consumer<CircularOrbit> end) {
		JPanel par = super.test(frame, end);
		JPanel spec = new JPanel();
		spec.setBounds(8, par.getY() + par.getHeight() + 8, 336, 48);
		spec.setLayout(new FlowLayout(FlowLayout.CENTER, 336, 8));
		spec.setBorder(BorderFactory.createLineBorder(Color.decode("#e91e63"), 1, true));
		frame.add(spec);
		
		return spec;
	}
	
	@Override
	protected String[] hintForUser() {
		return PhysicalObjectFactory.hint_User;
	}
}

enum Gender{
	M, F
}

final class User extends PhysicalObject {
	private final Gender gender;
	private final int age;
	
	User(Double r, String name, int age, Gender gender) {
		super(name, r, 360 * Math.random());
		this.gender = gender;
		this.age = age;
	}
	
	User(String name, int age, Gender gender) {
		super(name, -1);
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
				getGender() == user.getGender();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getGender(), getAge());
	}
	
	@Override
	public String toString() {
		return "<" + getName() +
				", " + age +
				", " + gender.toString() +
				'>';
	}
}

final class CentralUser extends PhysicalObject{
	private final Gender gender;
	private final int age;
	
	CentralUser(String name, int age, Gender gender) {
		super(name, 0, 0);
		this.gender = gender;
		this.age = age;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public int getAge() {
		return age;
	}
	
	@Override
	public String toString() {
		return "<" + getName() +
				", " + age +
				", " + gender.toString() +
				'>';
	}
}