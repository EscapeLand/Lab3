package appliactions;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import circularOrbit.CircularOrbit;
import circularOrbit.PhysicalObject;
import circularOrbit.SetCircularOrbit;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StellarSystem extends SetCircularOrbit<FixedStar, Planet> {
	private Thread loop;
	private double time = 0;
	private double timeSpan = 2000;
	private Runnable refresh;
	
	public void register(Runnable refresh) {
		this.refresh = refresh;
	}
	
	public void start(){
		loop = new Thread(refresh);
		loop.start();
	}
	
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
					List<String> list = new ArrayList<>(Arrays.asList(m.group(2).split(",")));
					if(list.size() != 8){
						System.out.println("warning: regex: Planet: not 8 args. continued. ");
						continue;
					}
					list.add(0, "Planet");
					PhysicalObject p = PhysicalObjectFactory.produce(list.toArray(new String[0]));
					assert p instanceof Planet;
					if(!addObject((Planet) p))
						System.out.println("warning: failed to add " + list.get(1));
					break;
				}
				default:
					System.out.println("warning: regex: unexpected key: " + m.group(1));
			}
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
	protected JPanel test(JFrame frame, Consumer<CircularOrbit> end) {
		var par =  super.test(frame, end);
		JPanel spec = new JPanel();
		spec.setBounds(8, par.getY() + par.getHeight() + 8, 336, 136);
		spec.setLayout(new FlowLayout(FlowLayout.CENTER, 336, 8));
		spec.setBorder(BorderFactory.createLineBorder(Color.decode("#e91e63"), 1, true));
		frame.add(spec);
		
		JPanel pnlTimeAt = new JPanel();
		JLabel lblTimeAt = new JLabel("Time at: ");
		JTextField txtTimeAt = new JTextField("18000");
		JButton btnTimeApply = new JButton("Apply");
		btnTimeApply.addActionListener(e-> {
			setTime(Double.valueOf(txtTimeAt.getText()));
			end.accept(this);
		});
		pnlTimeAt.add(lblTimeAt); pnlTimeAt.add(txtTimeAt); pnlTimeAt.add(btnTimeApply);
		spec.add(pnlTimeAt);
		
		JPanel pnlCalc = new JPanel();
		JLabel lblCalc = new JLabel("Distance between "), lblAnd = new JLabel(" and ")
				, lblRes = new JLabel();
		JTextField txtA = new JTextField("Neptune"), txtB = new JTextField("Mercury");
		JButton btnCalc = new JButton("=");
		btnCalc.addActionListener(e->{
			lblCalc.setVisible(false);
			PhysicalObject o1 = query(txtA.getText());
			PhysicalObject o2 = query(txtB.getText());
			if (o1 instanceof Planet && o2 instanceof Planet) {
				lblRes.setText(String.valueOf(CircularOrbitAPIs.getPhysicalDistance(this, o1, o2)));
			} else {
				lblRes.setText("Didn't match. ");
			}
		});
		pnlCalc.add(lblCalc); pnlCalc.add(txtA); pnlCalc.add(lblAnd); pnlCalc.add(txtB); pnlCalc.add(btnCalc);
		pnlCalc.add(lblRes); spec.add(pnlCalc);
		
		JPanel pnlCtrl = new JPanel();
		JButton btnReset = new JButton("Reset"),
				btnPause = new JButton("Pause"),
				btnTimeSpanApply = new JButton("Apply");
		JTextField txtTimeSpan = new JTextField("1600000");
		btnReset.addActionListener(e->{this.reset(); end.accept(this);});
		btnPause.addActionListener(e->{
			switch (btnPause.getText()) {
				case "Resume":
					start();
					btnPause.setText("Pause");
					break;
				case "Pause":
					loop.interrupt();
					btnPause.setText("Resume");
					break;
			}
		});
		btnTimeSpanApply.addActionListener(e->this.setTimeSpan(Double.valueOf(txtTimeSpan.getText())));
		pnlCtrl.add(btnReset); pnlCtrl.add(btnPause); pnlCtrl.add(txtTimeSpan); pnlCtrl.add(btnTimeSpanApply);
		spec.add(pnlCtrl);
		
		frame.setBounds(1000,232,364,360);
		
		return spec;
	}
	
	public void nextTime(){
		this.time += timeSpan;
		forEach(p->p.nextTime(time));
	}
	
	private void setTime(double time){
		if(loop != null) loop.interrupt();
		this.time = time;
		forEach(p->p.nextTime(time));
	}
	
	private void reset(){ setTime(0); }
	
	private void setTimeSpan(double timeSpan) {
		this.timeSpan = timeSpan;
	}
	
	@Override
	protected String[] hintForUser() {
		return PhysicalObjectFactory.hint_Planet;
	}
}

final class FixedStar extends PhysicalObject{
	public final float r;
	public final double m;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FixedStar)) return false;
		if (!super.equals(o)) return false;
		FixedStar fixedStar = (FixedStar) o;
		return Double.compare(fixedStar.getR(), getR()) == 0 &&
				Double.compare(fixedStar.m, m) == 0 &&
				getName().equals(fixedStar.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getName(), getR(), m);
	}
	
	FixedStar(String name, float r, double m) {
		super(name, 0, 0);
		this.r = r;
		this.m = m;
	}
}

final class Planet extends PhysicalObject {
	private final String color;
	private final Form form;
	public final double r;
	private final double v;
	
	enum Form{
		Solid, Liquid, Gas
	}
	enum Dir{
		CW, CCW
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Planet)) return false;
		if (!super.equals(o)) return false;
		Planet planet = (Planet) o;
		return Double.compare(planet.getR(), getR()) == 0 &&
				Double.compare(planet.v, v) == 0 &&
				getName().equals(planet.getName()) &&
				color.equals(planet.color) &&
				getForm() == planet.getForm();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getName(), color, getForm(), getR(), v);
	}
	
	void nextTime(double time) {
		setPos(pos_init + v * time);
	}
	
	private Form getForm() {
		return form;
	}
	
	Planet(String name, Form form, String color, double r, double R, double v, Dir dir, double pos) {
		super(name, R, pos);
		this.color = color;
		this.form = form;
		this.r = r;
		this.v = (dir == Dir.CW ? -1 : 1) * Math.abs(v / R);
	}
}