package appliactions;

import circularOrbit.CircularOrbit;
import circularOrbit.ListCircularOrbit;
import circularOrbit.PhysicalObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtomStructure extends ListCircularOrbit<Kernel, Electron> {
	private Caretaker caretaker = new Caretaker();
	
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
	protected String[] hintForUser() {
		return PhysicalObjectFactory.hint_Electron;
	}
	
	@Override
	public boolean transit(double from, double to, int number) {
		if(from == to) return false;
		if(!tracks.containsKey(Track.std(from)) || !tracks.containsKey(Track.std(to))) return false;
		boolean up = to > from;
		int n = number;
		var lfrom = tracks.get(Track.std(from));
		//if(n > lfrom.size()) return false;
		
		assert lfrom != null;
		caretaker.setMementos(from, to, saveMemento(from, to));
		
		for (int i = 0; i < lfrom.size(); i++) {
			Electron e = lfrom.get(i);
			if (e.isGround() == up && moveObject(e, to)) {
				i--;
				e.switchState(!up);
				if (--n == 0) break;
			}
		}
		
		if(n > 0) {
			recover(from, to, up);
			return false;
		}
		return true;
	}
	
	@Override
	public void process(Consumer<CircularOrbit> refresh) {
		JFrame frame = new JFrame(getClass().getSimpleName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(null);
		this.test(frame, refresh);
		
		frame.setVisible(true);
	}
	
	@Override
	protected JPanel test(JFrame frame, Consumer<CircularOrbit> refresh) {
		var par = super.test(frame, refresh);
		JPanel spec = new JPanel();
		spec.setBounds(8, par.getY() + par.getHeight() + 8, 336, 48);
		spec.setLayout(new FlowLayout(FlowLayout.CENTER, 336, 8));
		spec.setBorder(BorderFactory.createLineBorder(Color.decode("#e91e63"), 1, true));
		frame.add(spec);
		
		JPanel panel = new JPanel();
		spec.add(panel);
		panel.setBounds(8, 176, 336, 32);
		
		JComboBox<Double> cmbS1 = new JComboBox<>(getTracks().toArray(new Double[0]));
		JComboBox<Double> cmbS2 = new JComboBox<>(getTracks().toArray(new Double[0]));
		JButton btnTrsit = new JButton("Transit");
		JTextField txtNum = new JTextField("10");
		
		panel.add(cmbS1); panel.add(btnTrsit); panel.add(cmbS2); panel.add(txtNum);
		frame.setBounds(1000,232,364,280);
		
		btnTrsit.addActionListener(e -> {
			Double from = (Double) cmbS1.getSelectedItem();
			Double to = (Double) cmbS2.getSelectedItem();
			assert from != null && to != null;
			transit(from, to, Integer.valueOf(txtNum.getText()));
			refresh.accept(this);
		});
		
		return spec;
	}
	
	private void recover(double from, double to, boolean state){
		var rec = caretaker.getMementos(from, to);
		if(rec == null) throw new RuntimeException("cannot recover: " + from + "->" + to);
		rec.getFrom().forEach(e->{e.setR(from); e.switchState(state);});         //recover rep;
		tracks.put(Track.std(from), rec.getFrom());
		rec.getTo().forEach(e->{e.setR(to); e.switchState(state);});             //recover rep;
		tracks.put(Track.std(to), rec.getTo());
		caretaker.destroyMementos(from, to);
	}
	/**
	 * Originator.
	 * @param from transit from
	 * @param to transit to.
	 * @return Memento of the origin state.
	 */
	private Memento<Electron, List<Electron>> saveMemento(double from, double to){
		return new Memento<>(getObjectsOnTrack(from), getObjectsOnTrack(to));
	}
}

final class Memento<E extends PhysicalObject, C extends Collection<E>>{
	private C from;
	private C to;
	
	C getFrom() { return from; }
	
	public C getTo() { return to; }
	
	Memento(C from, C to) {
		this.from = from;
		this.to = to;
	}
}

final class Caretaker{
	final class pair{
		double first;
		double second;
		
		pair(double first, double second) {
			this.first = first;
			this.second = second;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			pair pair = (pair) o;
			return Double.compare(pair.first, first) == 0 &&
					Double.compare(pair.second, second) == 0;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(first, second);
		}
	}
	private Map<pair, Memento<Electron, List<Electron>>> mementos = new HashMap<>();
	
	@Nullable
	Memento<Electron, List<Electron>> getMementos(double from, double to) {
		return mementos.get(new pair(from, to));
	}
	
	void setMementos(double from, double to, Memento<Electron, List<Electron>> mementos) {
		this.mementos.put(new pair(from, to), mementos);
	}
	
	void destroyMementos(double from, double to){
		mementos.remove(new pair(from, to));
	}
}

final class Electron extends PhysicalObject{
	private ElectronState state = new Ground();
	
	Electron(double r) {
		super("e", r, 360 * Math.random());
	}
	
	void switchState(boolean ground){
		if(ground) state = new Ground();
		else state = new Excited();
	}
	
	boolean isGround(){
		return state.isGround();
	}
	
	@NotNull @Override
	public String toString() {
		return super.toString().replace("PhysicalObject", "Electron");
	}
}

final class Kernel extends PhysicalObject{
	
	Kernel(String name) {
		super(name, 0, 0);
	}
}