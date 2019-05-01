package test.track;

import static org.junit.Assert.*;

import org.junit.Test;

import src.track.Track;
import src.track.TrackFactory;

public class StellarSystemTrackTest {

/*	public Track<PlanetAPI> initial() throws Exception{
		Track<PlanetAPI> testtrack = TrackFactory.getStellarSystemTrack("1.00e9");
		PlanetAPI test1 = PhysicalObjectFactory.getPlanet();
		PlanetAPI test2 = PhysicalObjectFactory.getPlanet();
		PlanetAPI test3 = PhysicalObjectFactory.getPlanet();
		test1.setName("p1");
		test2.setName("p2");
		test3.setName("p3");
		test1.setType("Gas");
		test2.setType("Liquid");
		test3.setType("Solid");
		test1.setColor("Grey");
		test2.setColor("Red");
		test3.setColor("Orange");
		test1.setRadius("7515.80");
		test2.setRadius("9396.67");
		test3.setRadius("280.39");
		test1.setOrbitRadius("1.00e9");
		test2.setOrbitRadius("1.00e9");
		test3.setOrbitRadius("1.00e9");
		test1.setVelocity("4322");
		test2.setVelocity("8283");
		test3.setVelocity("4779");
		test1.setDirection("CW");
		test2.setDirection("CCW");
		test3.setDirection("CCW");
		test1.setInitialAngle("219");
		test2.setInitialAngle("237");
		test3.setInitialAngle("16");
		testtrack.setPhysicalObject(test1);
		testtrack.setPhysicalObject(test2);
		testtrack.setPhysicalObject(test3);
		return testtrack;
	}
	
	public List<PlanetAPI> initiallist() throws Exception {
		List<PlanetAPI> test = new ArrayList<>();
		PlanetAPI test1 = PhysicalObjectFactory.getPlanet();
		PlanetAPI test2 = PhysicalObjectFactory.getPlanet();
		PlanetAPI test3 = PhysicalObjectFactory.getPlanet();
		test1.setName("p1");
		test2.setName("p2");
		test3.setName("p3");
		test1.setType("Gas");
		test2.setType("Liquid");
		test3.setType("Solid");
		test1.setColor("Grey");
		test2.setColor("Red");
		test3.setColor("Orange");
		test1.setRadius("7515.80");
		test2.setRadius("9396.67");
		test3.setRadius("280.39");
		test1.setOrbitRadius("1.00e9");
		test2.setOrbitRadius("1.00e9");
		test3.setOrbitRadius("1.00e9");
		test1.setVelocity("4322");
		test2.setVelocity("8283");
		test3.setVelocity("4779");
		test1.setDirection("CW");
		test2.setDirection("CCW");
		test3.setDirection("CCW");
		test1.setInitialAngle("219");
		test2.setInitialAngle("237");
		test3.setInitialAngle("16");
		test.add(test1);
		test.add(test2);
		test.add(test3);
		return test;
	}*/
	
	public Track initial() throws Exception{
		Track test = TrackFactory.getStellarSystemTrack("1.00e9");
		return test;
	}
	
	@Test
	public void testGetOrbitRadius() throws Exception {
		Double temp = Double.valueOf("1.00e9").doubleValue();
		assertEquals(temp,initial().getOrbitRadius());
	}

	@Test
	public void testGetOrbitA() throws Exception {
		Double temp = Double.valueOf("1.00e9").doubleValue();
		assertEquals(temp,initial().getOrbitA());
	}

	@Test
	public void testGetOrbitB() throws Exception {
		Double temp = Double.valueOf("1.00e9").doubleValue();
		assertEquals(temp,initial().getOrbitB());
	}

}
