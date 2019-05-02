package circularOrbit;

import graph.Graph;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

import static APIs.CircularOrbitAPIs.*;
import static appliactions.PhysicalObjectFactory.*;

public abstract class ConcreteCircularOrbit<L extends PhysicalObject, E extends PhysicalObject>
		implements CircularOrbit<L, E>
{
	private Graph<PhysicalObject> relationship = Graph.empty();
	private L centre = null;
	
	protected abstract <C extends Collection<E>> Map<Track<E>, C> getTrack();
	protected abstract String[] hintForUser();
	@Override
	public boolean removeTrack(Double r){
		return null != getTrack().remove(Track.std(r));
	}
	
	@Override
	public L changeCentre(L newCenter){
		L prev = centre;
		centre = newCenter;
		return prev;
	}
	
	boolean findTrack(double r){
		return getTrack().containsKey(Track.std(r));
	}
	
	@Override
	public boolean moveObject(E obj, double to) {
		double from = obj.getR();
		if(from == to) return true;
		var cfrom = getTrack().get(Track.std(from));
		var cto = getTrack().get(Track.std(to));
		if(cfrom == null || cto == null) return false;
		
		var tomove = find_if(cfrom, e->e.equals(obj));
		if (tomove == null || !cfrom.remove(obj)) {
			return false;
		}
		tomove.setR(to);
		return cto.add(tomove);
	}
	
	@Override
	public boolean removeObject(@NotNull E obj){
		Collection<E> re = getTrack().get(Track.std(obj.getR()));
		if(re instanceof List || re instanceof Set){
			return Track.removeObject(re, obj);
		}
		return false;
	}
	
	@Override
	public boolean setRelation(@NotNull PhysicalObject a, @NotNull PhysicalObject b, float val){
		relationship.add(a);
		relationship.add(b);
		return 0 == relationship.set(a, b, val) && 0 ==relationship.set(b, a, val);
	}
	
	@NotNull @Override
	public Graph<PhysicalObject> getGraph(){
		return relationship;
	}
	
	@Override @Nullable
	public PhysicalObject query(String objName){
		final String name = objName.trim();
		if(centre.getName().equals(name)) return centre;
		for(var i: getTrack().values()){
			assert i instanceof List || i instanceof Set;
			E anIf = find_if(i, (p)-> Objects.equals(p.getName(), name));
			if(anIf != null) return anIf;
		}
		return null;
	}
	
	@Override
	public L center() {
		return centre;
	}
	
	@Override
	public Set<Double> getTracks() {
		Set<Double> r = new TreeSet<>();
		getTrack().keySet().forEach(t->r.add(t.R));
		
		return r;
	}
	
	@Override
	public boolean transit(double from, double to, int number) {
		throw new RuntimeException("only AtomStructure can transit. ");
	}
	
	protected JPanel test(JFrame frame, Consumer<CircularOrbit> end){
		JPanel common = new JPanel();
		common.setBounds(8, 8, 336, 160);
		common.setLayout(new FlowLayout(FlowLayout.CENTER, 336, 8));
		common.setBorder(BorderFactory.createLineBorder(Color.decode("#673ab7"), 1, true));
		frame.add(common);
		
		JPanel trackOP = new JPanel();
		JPanel objOP = new JPanel();
		JPanel entropy = new JPanel();
		var ops = new String[]{"Add", "Remove"};
		
		JComboBox<String> trackops = new JComboBox<>(ops);
		JTextField tracknum = new JTextField("-1");
		JButton trackExec = new JButton("Execute");
		
		trackExec.addActionListener(e -> {
			Double d = Double.valueOf(tracknum.getText());
			switch (trackops.getSelectedIndex()){
				case 0:
					addTrack(d);
					break;
				case 1:
					removeTrack(d);
					break;
				default: return;
			}
			end.accept(this);
		});
		
		trackOP.add(trackops); trackOP.add(tracknum); trackOP.add(trackExec);
		common.add(trackOP);
		
		JComboBox<String> objops = new JComboBox<>(ops);
		JComboBox<Double> objTidx = new JComboBox<>(getTracks().toArray(new Double[1]));
		JButton objExec = new JButton("Execute");
		objops.addItemListener(e->{
			String item = (String) e.getItem();
			switch (item){
				case "Add": objTidx.setVisible(false); break;
				case "Remove": objTidx.setVisible(true); break;
				default: break;
			}
		});
		objExec.addActionListener(e -> {
			switch(objops.getSelectedIndex()){
				case 0:
					var form = promptForm(frame, "Add object", hintForUser());
					switch (form.length){
						case 1: form = insert_copy(form, "Electron", 0); break;
						case 3: form = insert_copy(form, "User", 0); break;
						case 8: form = insert_copy(form, "Planet", 0); break;
						default: break;
					}
					var p = produce(form);
					if(p != null) addObject((E) p);
					break;
				case 1:
					Double r = (Double) objTidx.getSelectedItem();
					if(r != null) {
						var elm = find_if(getTrack().get(Track.std(r)), o->o.getName().equals(
							prompt(frame, "Remove object", "the name of the object: ", null)));
						if(elm == null) alert(frame, "remove", "no such object...");
						else removeObject(elm);
					}
					break;
				default: return;
			}
			end.accept(this);
		});
		
		objOP.add(objops); objOP.add(objTidx); objOP.add(objExec);
		common.add(objOP);
		objTidx.setVisible(false);
		
		JButton btnent = new JButton("Calculate Entropy");
		JLabel lblrst = new JLabel("");
		btnent.addActionListener(e-> lblrst.setText(String.valueOf(getObjectDistributionEntropy(this))));
		entropy.add(btnent); entropy.add(lblrst);
		common.add(entropy);
		
		return common;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}