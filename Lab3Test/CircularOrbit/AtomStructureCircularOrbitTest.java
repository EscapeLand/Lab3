package test.CircularOrbit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import src.centralObject.CentralObject;
import src.centralObject.CentralObjectFactory;
import src.circularOrbit.AtomStructureFactory;
import src.circularOrbit.CircularOrbit;
import src.circularOrbit.ConcreteCircularOrbitFactory;
import src.physicalObject.ElectronAPI;
import src.physicalObject.PhysicalObjectFactory;
import src.track.Track;
import src.track.TrackFactory;

public class AtomStructureCircularOrbitTest {

	public final List<ElectronAPI> testlist = new ArrayList<>(); 
	
	public void initial() throws Exception {
		ElectronAPI test1 = PhysicalObjectFactory.getElectron(0);
		ElectronAPI test2 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI test3 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI test4 = PhysicalObjectFactory.getElectron(2);
		ElectronAPI test5 = PhysicalObjectFactory.getElectron(2);
		ElectronAPI test6 = PhysicalObjectFactory.getElectron(2);
 		testlist.add(test1);
		testlist.add(test2);
		testlist.add(test3);
		testlist.add(test4);
		testlist.add(test5);
		testlist.add(test6);
	}

	public CircularOrbit<CentralObject,ElectronAPI> atomstructuretest() throws Exception{
		initial();
		ConcreteCircularOrbitFactory<CentralObject,ElectronAPI> atomstructurefactory = new AtomStructureFactory();
		CircularOrbit<CentralObject,ElectronAPI> atomstructure = atomstructurefactory.getCircularOrbit();
		CentralObject testelement = CentralObjectFactory.getElement("Rb");
		atomstructure.setcentralobject(testelement);
		Track test1 = TrackFactory.getAtomStructureTrack("1.00");
		Track test2 = TrackFactory.getAtomStructureTrack("2.00");
		Track test3 = TrackFactory.getAtomStructureTrack("3.00");
		atomstructure.addTrack(test1);
		atomstructure.addTrack(test2);
		atomstructure.addTrack(test3);
		atomstructure.addphysicalobject(test1, testlist.get(0));
		atomstructure.addphysicalobject(test2, testlist.get(1));
		atomstructure.addphysicalobject(test2, testlist.get(2));
		atomstructure.addphysicalobject(test3, testlist.get(3));
		atomstructure.addphysicalobject(test3, testlist.get(4));
		atomstructure.addphysicalobject(test3, testlist.get(5));
		return atomstructure;
	}
	
	@Test
	public void getcentralobjecttest() throws Exception{
		CircularOrbit<CentralObject,ElectronAPI> test = atomstructuretest();
		CentralObject testcenter = CentralObjectFactory.getElement("Rb");
		assertEquals(testcenter,test.getcentralobject());
	}
	
	@Test
	public void getMaptest() throws Exception{
		CircularOrbit<CentralObject,ElectronAPI> test = atomstructuretest();
		Track test1 = TrackFactory.getAtomStructureTrack("1.00");
		Track test2 = TrackFactory.getAtomStructureTrack("2.00");
		Track test3 = TrackFactory.getAtomStructureTrack("3.00");
		assertEquals(1,test.getMap().get(test1).size());
		assertEquals(2,test.getMap().get(test2).size());
		assertEquals(3,test.getMap().get(test3).size());
	}
	
	@Test
	public void getCenPhyMaptest() throws Exception{
		CircularOrbit<CentralObject,ElectronAPI> test = atomstructuretest();
		test.addCenPhyrelationship(testlist.get(0), 1.00);
		test.addCenPhyrelationship(testlist.get(1), 0.50);
		test.addCenPhyrelationship(testlist.get(2), 0.20);
		Double num1 = 1.00;
		Double num2 = 0.50;
		Double num3 = 0.20;
		assertEquals(num1,test.getCenPhyMap().get(test.getcentralobject()).get(testlist.get(0)));
		assertEquals(num2,test.getCenPhyMap().get(test.getcentralobject()).get(testlist.get(1)));
		assertEquals(num3,test.getCenPhyMap().get(test.getcentralobject()).get(testlist.get(2)));
	}
	
	@Test
	public void getPhyPhyMaptest() throws Exception{
		CircularOrbit<CentralObject,ElectronAPI> test = atomstructuretest();
		test.addPhyPhyrelationship(testlist.get(0),testlist.get(1), 1.00);
		test.addPhyPhyrelationship(testlist.get(1),testlist.get(2), 0.50);
		test.addPhyPhyrelationship(testlist.get(2),testlist.get(0), 0.20);
		Set<ElectronAPI> testSet = new HashSet<>();
		Set<ElectronAPI> testSet1 = new HashSet<>();
		Set<ElectronAPI> testSet2 = new HashSet<>();
		Set<ElectronAPI> testSet3 = new HashSet<>();
		testSet.add(testlist.get(0));
		testSet.add(testlist.get(1));
		testSet.add(testlist.get(2));
		testSet1.add(testlist.get(1));
		testSet2.add(testlist.get(2));
		testSet3.add(testlist.get(0));
		assertEquals(testSet,test.getPhyPhyMap().keySet());
		assertEquals(testSet1,test.getPhyPhyMap().get(testlist.get(0)).keySet());
		assertEquals(testSet2,test.getPhyPhyMap().get(testlist.get(1)).keySet());
		assertEquals(testSet3,test.getPhyPhyMap().get(testlist.get(2)).keySet());
		Double num1 = 1.00;
		Double num2 = 0.50;
		Double num3 = 0.20;
		assertEquals(num1,test.getPhyPhyMap().get(testlist.get(0)).get(testlist.get(1)));
		assertEquals(num2,test.getPhyPhyMap().get(testlist.get(1)).get(testlist.get(2)));
		assertEquals(num3,test.getPhyPhyMap().get(testlist.get(2)).get(testlist.get(0)));
	}
	
	@Test
	public void removetracktest() throws Exception{
		CircularOrbit<CentralObject,ElectronAPI> test = atomstructuretest();
		Track test1 = TrackFactory.getAtomStructureTrack("1.00");
		Track test2 = TrackFactory.getAtomStructureTrack("2.00");
		Track test3 = TrackFactory.getAtomStructureTrack("3.00");
		test.removeTrack(test1);
		Set<Track> testSet = new HashSet<>();
		testSet.add(test2);
		testSet.add(test3);
		assertEquals(testSet,test.getMap().keySet());
	}
	
	@Test
	public void transittest() throws Exception{
		CircularOrbit<CentralObject,ElectronAPI> test = atomstructuretest();
		Track test1 = TrackFactory.getAtomStructureTrack("1.00");
		test.transit(testlist.get(1), test1);
		assertEquals(2,test.getMap().get(test1).size());
	}
}
