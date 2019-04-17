package APIs;

import circularOrbit.CircularOrbit;
import circularOrbit.PhysicalObject;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import factory.CircularOrbitFactory;
import factory.DefaultCircularOrbitFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CircularOrbitHelper<L extends PhysicalObject, E extends PhysicalObject> extends JFrame {
	private mxGraph graph;
	private Object parent;
	private double length;
	private Map<Float, Integer> Rs = new TreeMap<>(Float::compare);
	
	public CircularOrbitHelper(@NotNull CircularOrbit<L, E> c, int length)
	{
		super(c.toString());
		graph = new mxGraph();
		parent = graph.getDefaultParent();
		this.length = length;
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
		
		Maxer m = new Maxer();
		
		Set<Float> tracks = c.getTracks();
		
		tracks.forEach(m::max);
		final float scale = m.m / length * 2;
		
		tracks.forEach(f->Rs.put(f, (int) (f / scale)));
		if(hasRepetition(Rs.values())){
			Rs.clear();
			float scale2 = (float) length / 2 / tracks.size();
			tracks.forEach(f->Rs.put(f, (int) ((Rs.size() + 1) * scale2)));
			assert Rs.size() == tracks.size();
		}
		tracks.clear();
		
		setSize((int) (length * 1.1), (int) (length * 1.1));
		Rs.values().forEach(this::circle);
		
		graph.getModel().beginUpdate();
		try
		{
			label(c.center());
			c.forEach(this::label);
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		
	}
	
	public static<L extends PhysicalObject, E extends PhysicalObject> void visualize(CircularOrbit<L, E> c){
		CircularOrbitHelper<L, E> frame = new CircularOrbitHelper<>(c, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
	
	private void circle(int r){
		final var style = "shape=ellipse;whiteSpace=wrap;aspect=fixed;fillColor=none;movable=0;resizable=0;";
		double base = length / 2 - r;
		graph.insertVertex(parent, null, "", base, base, 2*r, 2*r, style);
	}
	
	private Object label(PhysicalObject p){
		final var style = "shape=ellipse;whiteSpace=wrap;aspect=fixed;resizable=0;";
		Integer r = Rs.get(p.getR());
		if(r == null) r = 0;
		double x = length / 2.0 + r * Math.cos(p.getPos());
		double y = length / 2.0 + r * Math.sin(p.getPos());
		String name  = p.getName();
		Font font = getContentPane().getComponent(0).getFont();
		FontMetrics fm = getContentPane().getComponent(0).getFontMetrics(font);
		final double w = fm.stringWidth(name) * 1.4;
		return graph.insertVertex(parent, p.toString(), name, x - w / 2, y - w / 2, w, w, style);
	}
	
	public static void main(String[] args){
		CircularOrbitFactory factory = new DefaultCircularOrbitFactory();
		CircularOrbit s;
		try {
			s = factory.CreateAndLoad("input/AtomicStructure.txt");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if(s == null) return;
		visualize(s);
	}
	
	public static <E> boolean hasRepetition(Iterable<E> col){
		Set<E> visit = new HashSet<>();
		for (E e : col) {
			if(!visit.add(e)) return true;
		}
		return false;
	}
}

class Maxer{
	float m = 0;
	void max(float newm){
		m = Math.max(newm, m);
	}
}