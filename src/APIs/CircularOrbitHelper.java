package APIs;

import appliactions.StellarSystem;
import circularOrbit.CircularOrbit;
import circularOrbit.CircularOrbitFactory;
import circularOrbit.DefaultCircularOrbitFactory;
import circularOrbit.PhysicalObject;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import graph.Graph;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;


public class CircularOrbitHelper<L extends PhysicalObject, E extends PhysicalObject> extends JFrame {
	private mxGraph graph;
	private Object parent;
	private double scale;
	private double length;
	private Map<Double, Double> Rs = new TreeMap<>(Double::compare);
	private Map<PhysicalObject, Object> cells = new HashMap<>();
	
	private CircularOrbitHelper(@NotNull CircularOrbit<L, E> c, int length)
	{
		super(c.toString());
		graph = new mxGraph();
		parent = graph.getDefaultParent();
		this.length = length;
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
		
		Set<Double> tracks = c.getTracks();
		
		scale = (double) length / 2 / tracks.size();
		tracks.forEach(f->{if(f > 0) Rs.put(f, (Rs.size() + 1) * scale); });
		assert Rs.size() <= tracks.size();
		
		tracks.clear();
		
		setSize((int) (length * 1.1), (int) (length * 1.1));
		Rs.values().forEach(this::circle);
		
		graph.getModel().beginUpdate();
		Consumer<PhysicalObject> addCell = x -> cells.put(x, label(x));
		try
		{
			addCell.accept(c.center());
			c.forEach(addCell);
			final Graph<PhysicalObject> pg = c.getGraph();
			pg.vertices().forEach(v->{
				var tmpo = cells.get(v);
				pg.sources(v).forEach((key, value) -> line(tmpo, cells.get(key), value.toString()));
			});
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		
	}
	
	public static<L extends PhysicalObject, E extends PhysicalObject> void visualize(CircularOrbit<L, E> c){
		CircularOrbitHelper<L, E> frame = new CircularOrbitHelper<>(c, 800);
		if(c instanceof StellarSystem) {
			Thread t = new Thread(frame::run);
			t.start();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void run() {
		final int base = 2000;
		class cnt{
			private int i = base;
		}
		
		final Map<PhysicalObject, PhysicalObject> current = new HashMap<>();
		cells.forEach((k, v)->current.put(k, k));
		
		for(final cnt t = new cnt();;t.i += base){
			cells.forEach((p, c)->{
				var newp = StellarSystem.nextTime(p, t.i);
				var tmp = xy(newp);
				double x = tmp[0], y = tmp[1];
				tmp = xy(current.get(p));
				current.put(p, newp);
				x -= tmp[0]; y -= tmp[1];
				Object[] carr = {c};
				graph.moveCells(carr, x, y);
				//System.out.println(x + ", " + y);
			});
			//System.out.println("move " + t.i);
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	private void circle(double r){
		if(r < 0) return;
		final var style = "shape=ellipse;fillColor=none;movable=0;resizable=0;editable=0;connectable=0;";
		double base = length / 2 - r;
		graph.insertVertex(parent, null, "", base, base, 2*r, 2*r, style);
	}
	
	private double[] xy(PhysicalObject p){
		final double[] ret = new double[2];
		Double r;
		if(p.getR() == -1) r = scale * (Rs.size() + 1);
		else r = Rs.get(p.getR());
		if(r == null) r = 0.0;
		ret[0] = length / 2.0 + r * Math.cos(p.getPos());
		ret[1] = length / 2.0 + r * Math.sin(p.getPos());
		return ret;
	}
	
	private Object label(@NotNull PhysicalObject p){
		final var style = "shape=ellipse;movable=0;resizable=0;editable=0;connectable=0;";
		var tmp = xy(p);
		String name  = p.getName();
		Font font = getContentPane().getComponent(0).getFont();
		FontMetrics fm = getContentPane().getComponent(0).getFontMetrics(font);
		double x = tmp[0], y = tmp[1];
		double w = fm.stringWidth(name) * 1.4;
		return graph.insertVertex(parent, p.toString(), p.getName(), x - w / 2, y - w / 2, w, w, style);
	}
	
	private Object line(Object a, Object b, String label){
		String style = "edgeStyle=none;rounded=0;orthogonalLoop=1;jettySize=auto;dashed=1;endArrow=none;endFill=0;" +
				"editable=0;bendable=0;movable=0;jumpStyle=none;";
		return graph.insertEdge(parent, null, label, a, b, style);
	}
	
	public static void main(String[] args){
		CircularOrbitFactory factory = new DefaultCircularOrbitFactory();
		CircularOrbit s;
		try {
			System.out.println("load from:");
			Scanner scanner = new Scanner(System.in);
			s = factory.CreateAndLoad(scanner.next(".*\\.txt"));
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if(s == null) return;
		visualize(s);
	}
}