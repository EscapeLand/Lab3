package test.CircularOrbit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import src.centralObject.CentralObject;
import src.centralObject.CentralObjectFactory;
import src.circularOrbit.CircularOrbit;
import src.circularOrbit.ConcreteCircularOrbitFactory;
import src.circularOrbit.StellarSystemFactory;
import src.physicalObject.PhysicalObjectFactory;
import src.physicalObject.PlanetAPI;
import src.track.Track;
import src.track.TrackFactory;

public class StellarSystemCircularOrbitTest {

	public List<PlanetAPI> initial() throws Exception {
		List<PlanetAPI> test = new ArrayList<>();
		PlanetAPI test1 = PhysicalObjectFactory.getPlanet("Earth","Solid","Blue","6378.137","1.49e8","29.783","CW","0");
		PlanetAPI test2 = PhysicalObjectFactory.getPlanet("Saturn","Liquid","Red","2378","1.49e6","2.33e5","CCW","39.21");
		PlanetAPI test3 = PhysicalObjectFactory.getPlanet("Jupiter","Gas","Blue","1637.007","2e8","30","CW","70");
 		test.add(test1);
		test.add(test2);
		test.add(test3);
		return test;
	}
	
	public CircularOrbit<CentralObject,PlanetAPI> stellarsystemtest() throws Exception{
		ConcreteCircularOrbitFactory<CentralObject,PlanetAPI> stellarsystemfactory = new StellarSystemFactory();
		CircularOrbit<CentralObject,PlanetAPI> stellarsystem = stellarsystemfactory.getCircularOrbit();
		CentralObject testcenter = CentralObjectFactory.getStellar("Sun","6.96392e5","1.9885e30");
		stellarsystem.setcentralobject(testcenter);
		Track test1 = TrackFactory.getStellarSystemTrack("1.49e8");
		Track test2 = TrackFactory.getStellarSystemTrack("1.49e6");
		Track test3 = TrackFactory.getStellarSystemTrack("2e8");
		stellarsystem.addTrack(test1);
		stellarsystem.addTrack(test2);
		stellarsystem.addTrack(test3);
		stellarsystem.addphysicalobject(test1, initial().get(0));
		stellarsystem.addphysicalobject(test2, initial().get(1));
		stellarsystem.addphysicalobject(test3, initial().get(2));
		return stellarsystem;
	}
	
	@Test
	public void getcentralobjecttest() throws Exception{
		CircularOrbit<CentralObject,PlanetAPI> test = stellarsystemtest();
		CentralObject testcenter = CentralObjectFactory.getStellar("Sun","6.96392e5","1.9885e30");
		assertEquals(testcenter,test.getcentralobject());
	}
	
	
	
	@Test
	public void getMaptest() throws Exception{
		CircularOrbit<CentralObject,PlanetAPI> test = stellarsystemtest();
		Set<Track> testSet = new HashSet<>();
		Track test1 = TrackFactory.getStellarSystemTrack("1.49e8");
		Track test2 = TrackFactory.getStellarSystemTrack("1.49e6");
		Track test3 = TrackFactory.getStellarSystemTrack("2e8");
		testSet.add(test1);
		testSet.add(test2);
		testSet.add(test3);
		assertEquals(testSet,test.getMap().keySet());
		assertEquals(initial().get(0),test.getMap().get(test1).get(0));
		assertEquals(initial().get(1),test.getMap().get(test2).get(0));
		assertEquals(initial().get(2),test.getMap().get(test3).get(0));
	}
	
	@Test
	public void getCenPhyMaptest() throws Exception{
		CircularOrbit<CentralObject,PlanetAPI> test = stellarsystemtest();
		test.addCenPhyrelationship(initial().get(0), 1.00);
		test.addCenPhyrelationship(initial().get(1), 0.50);
		test.addCenPhyrelationship(initial().get(2), 0.20);
		Double num1 = 1.00;
		Double num2 = 0.50;
		Double num3 = 0.20;
		assertEquals(num1,test.getCenPhyMap().get(test.getcentralobject()).get(initial().get(0)));
		assertEquals(num2,test.getCenPhyMap().get(test.getcentralobject()).get(initial().get(1)));
		assertEquals(num3,test.getCenPhyMap().get(test.getcentralobject()).get(initial().get(2)));
	}
	
	@Test
	public void getPhyPhyMaptest() throws Exception{
		CircularOrbit<CentralObject,PlanetAPI> test = stellarsystemtest();
		test.addPhyPhyrelationship(initial().get(0),initial().get(1), 1.00);
		test.addPhyPhyrelationship(initial().get(1),initial().get(2), 0.50);
		test.addPhyPhyrelationship(initial().get(2),initial().get(0), 0.20);
		Set<PlanetAPI> testSet = new HashSet<>();
		Set<PlanetAPI> testSet1 = new HashSet<>();
		Set<PlanetAPI> testSet2 = new HashSet<>();
		Set<PlanetAPI> testSet3 = new HashSet<>();
		testSet.add(initial().get(0));
		testSet.add(initial().get(1));
		testSet.add(initial().get(2));
		testSet1.add(initial().get(1));
		testSet2.add(initial().get(2));
		testSet3.add(initial().get(0));
		assertEquals(testSet,test.getPhyPhyMap().keySet());
		assertEquals(testSet1,test.getPhyPhyMap().get(initial().get(0)).keySet());
		assertEquals(testSet2,test.getPhyPhyMap().get(initial().get(1)).keySet());
		assertEquals(testSet3,test.getPhyPhyMap().get(initial().get(2)).keySet());
		Double num1 = 1.00;
		Double num2 = 0.50;
		Double num3 = 0.20;
		assertEquals(num1,test.getPhyPhyMap().get(initial().get(0)).get(initial().get(1)));
		assertEquals(num2,test.getPhyPhyMap().get(initial().get(1)).get(initial().get(2)));
		assertEquals(num3,test.getPhyPhyMap().get(initial().get(2)).get(initial().get(0)));
	}
	
	@Test
	public void removeTrackTest() throws Exception {
		CircularOrbit<CentralObject,PlanetAPI> test = stellarsystemtest();
		Track test1 = TrackFactory.getStellarSystemTrack("1.49e8");
		Track test2 = TrackFactory.getStellarSystemTrack("1.49e6");
		Track test3 = TrackFactory.getStellarSystemTrack("2e8");
		Set<Track> testSet = new HashSet<>();
		testSet.add(test1);
		testSet.add(test2);
		test.removeTrack(test3);
		assertEquals(testSet,test.getMap().keySet());
	}
	
	@Test
	public void transittest() throws Exception{
		CircularOrbit<CentralObject,PlanetAPI> test = stellarsystemtest();
		Track test2 = TrackFactory.getStellarSystemTrack("1.49e6");
		test.transit(initial().get(0), test2);
		List<PlanetAPI> testList = new ArrayList<>();
		testList.add(initial().get(0));
		testList.add(initial().get(1));
		assertEquals(testList,test.getMap().get(test2));
	}
	
	@Test
	public void movetest() throws Exception{
		CircularOrbit<CentralObject,PlanetAPI> test = stellarsystemtest();
		test.move(initial().get(0), 1.00);
		Track test1 = TrackFactory.getStellarSystemTrack("1.49e8");
		Double num = 1.00;
		assertEquals(num,test.getMap().get(test1).get(0).getCurrentangle());
	}
}
