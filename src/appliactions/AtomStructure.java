package appliactions;

import circularOrbit.ListCircularOrbit;
import circularOrbit.PhysicalObject;
import graph.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtomStructure extends ListCircularOrbit<String, Electron> {
	Graph<Object> graph = Graph.empty();
	
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
		
		changeCentre(m.group(1));
		
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
			for(int j = 0; j < num[i]; j++) addObject(i + 1, new Electron(i + 1));
		}
		reader.close();
		return true;
	}
}

final class Electron extends PhysicalObject{
	
	public Electron(float r) {
		super(r, 0);
	}
	
	@Override
	public boolean equals(Object that){
		if(that instanceof Electron) return true;
		else return false;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(getR(), "Electron");
	}
	
	@Override
	public String getName() {
		return "Electron";
	}
}