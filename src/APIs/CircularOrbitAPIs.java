package APIs;

import circularOrbit.CircularOrbit;
import circularOrbit.PhysicalObject;
import graph.Graph;

import java.util.*;
import java.util.function.Predicate;

public class CircularOrbitAPIs {
	public static<L extends PhysicalObject, E extends PhysicalObject> double getObjectDistributionEntropy(CircularOrbit<L, E> c){
		Map<Double, Float> p = new HashMap<>();
		int sum = 0;
		for (E i : c) {
			Float tmp = p.get(i.getR());
			if(tmp == null) tmp = 0.0f;
			p.put(i.getR(), tmp + 1.0f);
			sum++;
		}
		for(Map.Entry<Double, Float> i: p.entrySet()) p.put(i.getKey(), i.getValue() / sum);
		
		float H = 0;
		for(Float i: p.values()) H -= i * Math.log(i);
		return H;
	}
	
	public static<L extends PhysicalObject, E extends PhysicalObject> int getLogicalDistance (CircularOrbit<L, E> c, E a, E b){
		Graph<PhysicalObject> graph = c.getGraph();
		if(!graph.vertices().containsAll(Arrays.asList(a, b))) return -1;
		if(a == b) return 0;
		else{
			Set<PhysicalObject> que = new HashSet<>();
			int r = findNext(graph, a, b, que);
			que.clear();
			return r;
		}
	}
	
	private static<E extends PhysicalObject> int findNext(Graph<PhysicalObject> graph, E a, E b, Set<PhysicalObject> que) {
		que.add(a);
		Set<PhysicalObject> next = graph.targets(a).keySet();
		if(next.contains(b)) return que.size();
		else {
			Set<Integer> forcmp = new HashSet<>();
			
			for(PhysicalObject i: next) {
				if(que.contains(i)) continue;
				int r = findNext(graph, i, b, new HashSet<>(que));
				if(r > 0) forcmp.add(r);
			}
			if(forcmp.isEmpty()) return -1;
			else return Collections.min(forcmp);
		}
	}
	
	public static<L extends PhysicalObject, E extends PhysicalObject> double getPhysicalDistance (CircularOrbit<L, E> c, PhysicalObject e1, PhysicalObject e2){
		return oppositeSide(Math.abs(e1.getPos() - e2.getPos()), e1.getR(), e2.getR());
	}
	
	public static<L extends PhysicalObject, E extends PhysicalObject> Difference getDifference (CircularOrbit<L, E> c1, CircularOrbit<L, E> c2){
		List<E> lc1 = new ArrayList<>();
		List<E> lc2 = new ArrayList<>();
		c1.forEach(lc1::add);
		c2.forEach(lc2::add);
		
		Map<Double, Integer> Rc1 = new TreeMap<>(Double::compare);
		Map<Double, Integer> Rc2 = new TreeMap<>(Double::compare);
		
		lc1.forEach(x->Rc1.put(x.getR(), Rc1.get(x.getR()) == null ? 1 : Rc1.get(x.getR()) + 1));
		lc2.forEach(x->Rc2.put(x.getR(), Rc2.get(x.getR()) == null ? 1 : Rc2.get(x.getR()) + 1));
		
		int m = lc1.size() > lc2.size() ? lc1.size() : lc2.size();
		
		int[] trackDif = new int[m];
		Iterator<Integer> Ic1 = Rc1.values().iterator();
		Iterator<Integer> Ic2 = Rc2.values().iterator();
		int i = 0;
		while(Ic1.hasNext() || Ic2.hasNext()){
			trackDif[i++] = Ic1.hasNext() && Ic2.hasNext() ? Ic1.next() - Ic2.next() :
					Ic1.hasNext() ? Ic1.next() : -Ic2.next();
		}
		
		Map<Double, Set<E>> OBJDif1 = new TreeMap<>(Double::compare);
		Map<Double, Set<E>> OBJDif2 = new TreeMap<>(Double::compare);
		
		for (E e : lc1) {
			if(!lc2.contains(e)) {
				Set<E> tmp = OBJDif1.get(e.getR());
				if(tmp != null) tmp.add(e);
				else{
					tmp = new HashSet<>();
					tmp.add(e);
					OBJDif1.put(e.getR(), tmp);
				}
			}
			else if(!OBJDif1.containsKey(e.getR())) OBJDif1.put(e.getR(), null);
		}
		
		for (E e : lc2) {
			if(!lc1.contains(e)) {
				Set<E> tmp = OBJDif2.get(e.getR());
				if(tmp != null) tmp.add(e);
				else{
					tmp = new HashSet<>();
					tmp.add(e);
					OBJDif2.put(e.getR(), tmp);
				}
			}
			else if(!OBJDif2.containsKey(e.getR())) OBJDif2.put(e.getR(), null);
		}
		
		return new Difference<>(Rc1.size() - Rc2.size(), trackDif, new ArrayList<>(OBJDif1.values()), new ArrayList<>(OBJDif2.values()));
	}
	
	private static double oppositeSide(double includeAngle, double l1, double l2){
		return Math.sqrt(l1 * l1 + l2 * l2 - 2 * l1 * l2 * Math.cos(includeAngle));
	}
	
	public static <E> E find_if(Iterable<E> col, Predicate<E> pred){
		for (E e : col) {
			if(pred.test(e)) return e;
		}
		return null;
	}
}

class Difference<E extends PhysicalObject>{
	public final int trackDif;
	private final int[] trackNumDif;
	private final List<Set<E>> OBJDif1;
	private final List<Set<E>> OBJDif2;
	
	public Difference(int trackDif, int[] trackNumDif, List<Set<E>> OBJDif1, List<Set<E>> OBJDif2) {
		this.trackDif = trackDif;
		this.trackNumDif = trackNumDif;
		this.OBJDif1 = OBJDif1;
		this.OBJDif2 = OBJDif2;
	}
	
	public int[] getTrackNumDif() {
		return trackNumDif.clone();
	}
	
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("轨道数差异: ").append(trackDif);
		int m = Math.max(OBJDif1.size(), OBJDif2.size());
		
		for (int i = 0; i < m; i++) {
			
			s.append("\n轨道").append(i+1).append("的物体数量差异: ").append(trackNumDif[i]);
			s.append("; 物体差异: {");
			if(i < OBJDif1.size()) OBJDif1.get(i).forEach(x->s.append(x.getName()).append(", "));
			s.append("} - {");
			if(i < OBJDif2.size()) OBJDif2.get(i).forEach(x->s.append(x.getName()).append(", "));
			s.append("}");
		}
		
		return s.toString();
	}
	
	public List<Set<E>> getOBJDif1() {
		return new ArrayList<>(OBJDif1);
	}
	
	public List<Set<E>> getOBJDif2() {
		return new ArrayList<>(OBJDif2);
	}
}