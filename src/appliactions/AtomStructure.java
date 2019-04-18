package appliactions;

import circularOrbit.ListCircularOrbit;
import circularOrbit.PhysicalObject;
import graph.Graph;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtomStructure extends ListCircularOrbit<Kernel, Electron> {
	Graph<Object> graph = Graph.empty();
	Caretaker caretaker = new Caretaker();
	
	@Override
	public boolean loadFromFile(String path) throws IOException {
		Pattern[] patterns = {Pattern.compile("ElementName\\s?::= ([A-Z][a-z]{0,2})"),
				Pattern.compile("NumberOfTracks\\s?::= (\\d+)"),
				Pattern.compile("NumberOfElectron\\s?::= ((?:\\d+[/;]?)+)")};
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String buffer = reader.readLine();
		Matcher m = patterns[0].matcher(buffer);
		if(!m.find() || m.groupCount() != 1){
			System.out.println("warning: regex: ElementName != 1. ");
			reader.close(); return false;
		}
		
		changeCentre(new Kernel(m.group(1)));
		
		buffer = reader.readLine();
		m = patterns[1].matcher(buffer);
		if(!m.find() || m.groupCount() != 1){
			System.out.println("warning: regex: NumberOfTracks != 1. ");
			reader.close(); return false;
		}
		
		int n = Integer.valueOf(m.group(1));
		buffer = reader.readLine();
		m = patterns[2].matcher(buffer);
		if(!m.find() || m.groupCount() != 1){
			System.out.println("warning: regex: NumberOfElectron != 1. ");
			reader.close(); return false;
		}
		
		int[] num = new int[n];
		int i = 0;
		for (String[] tmp = m.group(1).split("[/;]"); i < n; i++) {
			num[i] = Integer.valueOf(tmp[2 * i + 1]);
		}
		
		for(i = 0; i < n; i++){
			for(int j = 0; j < num[i]; j++) addObject(new Electron(i + 1));
		}
		reader.close();
		return true;
	}
	
	@Override
	public String toString() {
		return "AtomStructure";
	}
	
	/**
	 * Originator.
	 * @param from transit from
	 * @param to transit to.
	 * @return Memento of the origin state.
	 */
	private Memento<Electron> saveMemento(double from, double to){
		return new Memento<>(getObjectsOnTrack(from), getObjectsOnTrack(to));
	}
}

final class Memento<E extends PhysicalObject>{
	private Collection<E> from;
	private Collection<E> to;
	
	public Collection<E> getFrom() {
		return from;
	}
	
	public Collection<E> getTo() {
		return to;
	}
	
	Memento(Collection<E> from, Collection<E> to) {
		if(from instanceof Set) {
			this.from = new TreeSet<>(from);
			this.to = new TreeSet<>(to);
		}
		else if(from instanceof List){
			this.from = new ArrayList<>(from);
			this.to = new ArrayList<>(to);
		}
		else throw new AssertionError("'from' must be instance of set or list. ");
	}
}

final class Caretaker{
	private Map<Electron, Memento<Electron>> mementos = new TreeMap<>((o1, o2) -> o1.getR() < o2.getR() ? -1 :
			o1.getR() > o2.getR() ? 1 :
			Double.compare(o1.getPos(), o2.getPos()));
	
	public Memento<Electron> getMementos(Electron e) {
		return mementos.get(e);
	}
	
	public void setMementos(Electron e, Memento<Electron> mementos) {
		this.mementos.put(e, mementos);
	}
}

final class Electron extends PhysicalObject{
	
	Electron(double r) {
		super(r, 360 * Math.random());
	}
	
	@NotNull @Override
	public String toString() {
		return super.toString().replace("PhysicalObject", "Electron");
	}
	
	@Override
	public boolean equals(Object that){
		return that instanceof Electron;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(getR(), "e");
	}
	
	@Override
	public String getName() {
		return "e";
	}
	
	@Override
	public PhysicalObject changeR(double newr) {
		return new Electron(newr);
	}
}

final class Kernel extends PhysicalObject{
	private final String name;
	
	Kernel(String name) {
		super(0, 0);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public PhysicalObject changeR(double newr) {
		throw new RuntimeException("changeR: center");
	}
}