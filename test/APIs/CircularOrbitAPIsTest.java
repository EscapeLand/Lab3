package APIs;

import appliactions.StellarSystem;
import circularOrbit.PhysicalObject;
import org.junit.Test;

import java.io.IOException;


public class CircularOrbitAPIsTest {
	private StellarSystem s = new StellarSystem();
	private StellarSystem sc = new StellarSystem();
	
	public CircularOrbitAPIsTest(){
		try{
			s.loadFromFile("input/StellarSystem.txt");
			sc.loadFromFile("input/StellarSystem_Medium.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getObjectDistributionEntropy() {
		System.out.println(CircularOrbitAPIs.getObjectDistributionEntropy(s));
	}
	
	@Test
	public void getLogicalDistance() {
	}
	
	@Test
	public void getPhysicalDistance() {
		PhysicalObject e1 = s.iterator().next();
		
		System.out.println(CircularOrbitAPIs.getPhysicalDistance(s, e1, e1));
	}
	
	@Test
	public void getDifference() {
		var d = CircularOrbitAPIs.getDifference(s, sc);
		System.out.println(d.toString());
	}
	
}