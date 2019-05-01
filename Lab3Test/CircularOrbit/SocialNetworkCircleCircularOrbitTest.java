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
import src.circularOrbit.SocialNetworkCircleFactory;
import src.physicalObject.FriendAPI;
import src.physicalObject.PhysicalObjectFactory;
import src.track.Track;
import src.track.TrackFactory;

public class SocialNetworkCircleCircularOrbitTest {

	public List<FriendAPI> initial() throws Exception{
		List<FriendAPI> test = new ArrayList<>();
		FriendAPI test1 = PhysicalObjectFactory.getFriend("LisaWong", "25", "F");
		FriendAPI test2 = PhysicalObjectFactory.getFriend("TomWong", "61", "M");
		FriendAPI test3 = PhysicalObjectFactory.getFriend("FrankLee", "42","M");
 		test.add(test1);
		test.add(test2);
		test.add(test3);
		return test;
	}
	
	public CircularOrbit<CentralObject,FriendAPI> socialnetworkcircletest() throws Exception{
		ConcreteCircularOrbitFactory<CentralObject,FriendAPI> socialnetworkcirclefactory = new SocialNetworkCircleFactory();
		CircularOrbit<CentralObject,FriendAPI> socialnetworkcircle = socialnetworkcirclefactory.getCircularOrbit();
		CentralObject testcenter = CentralObjectFactory.getCentralUser("TommyWong","30","M");
		socialnetworkcircle.setcentralobject(testcenter);
		Track test1 = TrackFactory.getStellarSystemTrack("1.00");
		Track test2 = TrackFactory.getStellarSystemTrack("2.00");
		Track test3 = TrackFactory.getStellarSystemTrack("3.00");
		socialnetworkcircle.addTrack(test1);
		socialnetworkcircle.addTrack(test2);
		socialnetworkcircle.addTrack(test3);
		socialnetworkcircle.addphysicalobject(test1, initial().get(0));
		socialnetworkcircle.addphysicalobject(test2, initial().get(1));
		socialnetworkcircle.addphysicalobject(test3, initial().get(2));
		return socialnetworkcircle;
	}
	
	@Test
	public void getcentralobjecttest() throws Exception {
		CircularOrbit<CentralObject,FriendAPI> test = socialnetworkcircletest();
		CentralObject testcenter = CentralObjectFactory.getCentralUser("TommyWong","30","M");
		assertEquals(testcenter,test.getcentralobject());
	}

	@Test
	public void getMaptest() throws Exception{
		CircularOrbit<CentralObject,FriendAPI> test = socialnetworkcircletest();
		Set<Track> testSet = new HashSet<>();
		Track test1 = TrackFactory.getStellarSystemTrack("1.00");
		Track test2 = TrackFactory.getStellarSystemTrack("2.00");
		Track test3 = TrackFactory.getStellarSystemTrack("3.00");
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
		CircularOrbit<CentralObject,FriendAPI> test = socialnetworkcircletest();
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
	public void getPhyPhyMap() throws Exception{
		CircularOrbit<CentralObject,FriendAPI> test = socialnetworkcircletest();
		test.addPhyPhyrelationship(initial().get(0),initial().get(1), 1.00);
		test.addPhyPhyrelationship(initial().get(1),initial().get(2), 0.50);
		test.addPhyPhyrelationship(initial().get(2),initial().get(0), 0.20);
		Set<FriendAPI> testSet = new HashSet<>();
		Set<FriendAPI> testSet1 = new HashSet<>();
		Set<FriendAPI> testSet2 = new HashSet<>();
		Set<FriendAPI> testSet3 = new HashSet<>();
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
	public void removeTracktest() throws Exception{
		CircularOrbit<CentralObject,FriendAPI> test = socialnetworkcircletest();
		Track test1 = TrackFactory.getStellarSystemTrack("1.00");
		Track test2 = TrackFactory.getStellarSystemTrack("2.00");
		Track test3 = TrackFactory.getStellarSystemTrack("3.00");
		Set<Track> testSet = new HashSet<>();
		testSet.add(test1);
		testSet.add(test2);
		test.removeTrack(test3);
		assertEquals(testSet,test.getMap().keySet());
	}
	
	@Test
	public void transittest() throws Exception{
		CircularOrbit<CentralObject,FriendAPI> test = socialnetworkcircletest();
		Track test2 = TrackFactory.getStellarSystemTrack("2.00");
		test.transit(initial().get(0), test2);
		List<FriendAPI> testList = new ArrayList<>();
		testList.add(initial().get(0));
		testList.add(initial().get(1));
		assertTrue(test.getMap().get(test2).contains(initial().get(0)));
		assertTrue(test.getMap().get(test2).contains(initial().get(1)));
	}
	
	@Test
	public void movetest() throws Exception{
		CircularOrbit<CentralObject,FriendAPI> test = socialnetworkcircletest();
		test.move(initial().get(0), 1.00);
		Track test1 = TrackFactory.getStellarSystemTrack("1.00");
		Double num = 1.00;
		assertEquals(num,test.getMap().get(test1).get(0).getCurrentangle());
	}
}
