package test.APIs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import src.APIs.CircularOrbitAPIs;
import src.centralObject.CentralObject;
import src.centralObject.CentralObjectFactory;
import src.circularOrbit.AtomStructureFactory;
import src.circularOrbit.CircularOrbit;
import src.circularOrbit.ConcreteCircularOrbitFactory;
import src.circularOrbit.SocialNetworkCircleFactory;
import src.circularOrbit.StellarSystemFactory;
import src.physicalObject.ElectronAPI;
import src.physicalObject.FriendAPI;
import src.physicalObject.PhysicalObjectFactory;
import src.physicalObject.PlanetAPI;
import src.track.Track;
import src.track.TrackFactory;

public class CircularOrbitAPIsTest {

	private CircularOrbit<CentralObject,PlanetAPI> stellarsystemTest1;
	private CircularOrbit<CentralObject,ElectronAPI> atomstructureTest1;
	private CircularOrbit<CentralObject,FriendAPI> socialnetworkcircleTest1;
	
	private CircularOrbit<CentralObject,PlanetAPI> stellarsystemTest2;
	private CircularOrbit<CentralObject,ElectronAPI> atomstructureTest2;
	private CircularOrbit<CentralObject,FriendAPI> socialnetworkcircleTest2;
	
	private List<ElectronAPI> electronlist = new ArrayList<>();
	
	public void StellarSystemCircularOrbitAPIInitial() throws Exception{
		ConcreteCircularOrbitFactory<CentralObject,PlanetAPI> stellarsystemfactory = new StellarSystemFactory();
		CircularOrbit<CentralObject,PlanetAPI> stellarsystem1 = stellarsystemfactory.getCircularOrbit();
		CentralObject testcenter1 = CentralObjectFactory.getStellar("Sun","6.96392e5","1.9885e30");
		stellarsystem1.setcentralobject(testcenter1);
		Track test1 = TrackFactory.getStellarSystemTrack("1.49e8");
		Track test2 = TrackFactory.getStellarSystemTrack("1.49e6");
		Track test3 = TrackFactory.getStellarSystemTrack("2e8");
		stellarsystem1.addTrack(test1);
		stellarsystem1.addTrack(test2);
		stellarsystem1.addTrack(test3);
		PlanetAPI p1 = PhysicalObjectFactory.getPlanet("Earth","Solid","Blue","6378.137","1.49e8","29.783","CW","0");
		PlanetAPI p2 = PhysicalObjectFactory.getPlanet("Saturn","Liquid","Red","2378","1.49e6","2.33e5","CCW","39.21");
		PlanetAPI p3 = PhysicalObjectFactory.getPlanet("Jupiter","Gas","Blue","1637.007","2e8","30","CW","70");
		stellarsystem1.addphysicalobject(test1, p1);
		stellarsystem1.addphysicalobject(test2, p2);
		stellarsystem1.addphysicalobject(test3, p3);	
		stellarsystem1.addCenPhyrelationship(p1, 1.00);
		stellarsystem1.addCenPhyrelationship(p2, 1.00);
		stellarsystem1.addCenPhyrelationship(p3, 1.00);
		stellarsystem1.addPhyPhyrelationship(p1, p2, 1.00);
		stellarsystem1.addPhyPhyrelationship(p2, p3, 1.00);
		stellarsystemTest1=stellarsystem1;
		
		CircularOrbit<CentralObject,PlanetAPI> stellarsystem2 = stellarsystemfactory.getCircularOrbit();
		CentralObject testcenter2 = CentralObjectFactory.getStellar("NewSun","6.96392e5","1.9885e30");
		stellarsystem2.setcentralobject(testcenter2);
		Track test4 = TrackFactory.getStellarSystemTrack("1.49e8");
		Track test5 = TrackFactory.getStellarSystemTrack("1.49e6");
		Track test6 = TrackFactory.getStellarSystemTrack("2e8");
		stellarsystem2.addTrack(test1);
		stellarsystem2.addTrack(test2);
		stellarsystem2.addTrack(test3);
		PlanetAPI p4 = PhysicalObjectFactory.getPlanet("Mars","Solid","Red","637.137","9.99e10","1000.93","CCW","110");
		PlanetAPI p5 = PhysicalObjectFactory.getPlanet("Neptune","Liquid","Yellow","6627.137","1.49e5","9293.05","CCW","360");
		PlanetAPI p6 = PhysicalObjectFactory.getPlanet("Jupiter","Gas","Blue","1637.007","2e8","30","CW","70");
		stellarsystem2.addphysicalobject(test4, p4);
		stellarsystem2.addphysicalobject(test5, p5);
		stellarsystem2.addphysicalobject(test6, p6);
		stellarsystemTest2=stellarsystem2;
	}
	
	public void AtomStructureCircularOrbitAPIInitial() throws Exception{
		ConcreteCircularOrbitFactory<CentralObject,ElectronAPI> atomstructurefactory = new AtomStructureFactory();
		CircularOrbit<CentralObject,ElectronAPI> atomstructure1 = atomstructurefactory.getCircularOrbit();
		CentralObject testelement1 = CentralObjectFactory.getElement("Rb");
		atomstructure1.setcentralobject(testelement1);
		Track test1 = TrackFactory.getAtomStructureTrack("1.00");
		Track test2 = TrackFactory.getAtomStructureTrack("2.00");
		Track test3 = TrackFactory.getAtomStructureTrack("3.00");
		atomstructure1.addTrack(test1);
		atomstructure1.addTrack(test2);
		atomstructure1.addTrack(test3);
		ElectronAPI e1 = PhysicalObjectFactory.getElectron(0);
		ElectronAPI e2 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI e3 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI e4 = PhysicalObjectFactory.getElectron(2);
		ElectronAPI e5 = PhysicalObjectFactory.getElectron(2);
		ElectronAPI e6 = PhysicalObjectFactory.getElectron(2);
		electronlist.add(e1);
		electronlist.add(e2);
		electronlist.add(e3);
		electronlist.add(e4);
		electronlist.add(e5);
		electronlist.add(e6);
		atomstructure1.addphysicalobject(test1, e1);
		atomstructure1.addphysicalobject(test2, e2);
		atomstructure1.addphysicalobject(test2, e3);
		atomstructure1.addphysicalobject(test3, e4);
		atomstructure1.addphysicalobject(test3, e5);
		atomstructure1.addphysicalobject(test3, e6);
		atomstructure1.addCenPhyrelationship(e1, 1.00);
		atomstructure1.addCenPhyrelationship(e2, 1.00);
		atomstructure1.addCenPhyrelationship(e3, 1.00);
		atomstructure1.addPhyPhyrelationship(e1, e2, 1.00);
		atomstructure1.addPhyPhyrelationship(e2, e3, 1.00);
		
		atomstructureTest1=atomstructure1;
		
		CircularOrbit<CentralObject,ElectronAPI> atomstructure2 = atomstructurefactory.getCircularOrbit();
		CentralObject testelement2 = CentralObjectFactory.getElement("Er");
		atomstructure2.setcentralobject(testelement2);
		Track test4 = TrackFactory.getAtomStructureTrack("1.00");
		Track test5 = TrackFactory.getAtomStructureTrack("2.00");
		Track test6 = TrackFactory.getAtomStructureTrack("3.00");
		atomstructure2.addTrack(test4);
		atomstructure2.addTrack(test5);
		atomstructure2.addTrack(test6);
		ElectronAPI e7 = PhysicalObjectFactory.getElectron(0);
		ElectronAPI e8 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI e9 = PhysicalObjectFactory.getElectron(2);
		atomstructure2.addphysicalobject(test4, e7);
		atomstructure2.addphysicalobject(test5, e8);
		atomstructure2.addphysicalobject(test6, e9);
		atomstructureTest2=atomstructure2;
		
	}
	
	public void SocialNetworkCircleCircularOrbitAPIInitial() throws Exception{
		ConcreteCircularOrbitFactory<CentralObject,FriendAPI> socialnetworkcirclefactory = new SocialNetworkCircleFactory();
		CircularOrbit<CentralObject,FriendAPI> socialnetworkcircle1 = socialnetworkcirclefactory.getCircularOrbit();
		CentralObject testcenter1 = CentralObjectFactory.getCentralUser("TommyWong","30","M");
		socialnetworkcircle1.setcentralobject(testcenter1);
		Track test1 = TrackFactory.getStellarSystemTrack("1.00");
		Track test2 = TrackFactory.getStellarSystemTrack("2.00");
		Track test3 = TrackFactory.getStellarSystemTrack("3.00");
		FriendAPI f1 = PhysicalObjectFactory.getFriend("LisaWong", "25", "F");
		FriendAPI f2 = PhysicalObjectFactory.getFriend("TomWong", "61", "M");
		FriendAPI f3 = PhysicalObjectFactory.getFriend("FrankLee", "42","M");
		socialnetworkcircle1.addTrack(test1);
		socialnetworkcircle1.addTrack(test2);
		socialnetworkcircle1.addTrack(test3);
		socialnetworkcircle1.addphysicalobject(test1, f1);
		socialnetworkcircle1.addphysicalobject(test2, f2);
		socialnetworkcircle1.addphysicalobject(test3, f3);
		socialnetworkcircle1.addCenPhyrelationship(f1, 1.00);
		socialnetworkcircle1.addCenPhyrelationship(f2, 1.00);
		socialnetworkcircle1.addCenPhyrelationship(f3, 1.00);
		socialnetworkcircle1.addPhyPhyrelationship(f1, f2, 1.00);
		socialnetworkcircle1.addPhyPhyrelationship(f2, f3, 1.00);
		socialnetworkcircleTest1 = socialnetworkcircle1;
		
		CircularOrbit<CentralObject,FriendAPI> socialnetworkcircle2 = socialnetworkcirclefactory.getCircularOrbit();
		CentralObject testcenter2 = CentralObjectFactory.getCentralUser("TommyWong","30","M");
		socialnetworkcircle2.setcentralobject(testcenter2);
		Track test4 = TrackFactory.getStellarSystemTrack("1.00");
		Track test5 = TrackFactory.getStellarSystemTrack("2.00");
		Track test6 = TrackFactory.getStellarSystemTrack("3.00");
		FriendAPI f4 = PhysicalObjectFactory.getFriend("DavidChen", "55", "M");
		FriendAPI f5 = PhysicalObjectFactory.getFriend("TomWong", "61", "M");
		FriendAPI f6 = PhysicalObjectFactory.getFriend("PonyMa", "47", "M");
		socialnetworkcircle2.addTrack(test4);
		socialnetworkcircle2.addTrack(test5);
		socialnetworkcircle2.addTrack(test6);
		socialnetworkcircle2.addphysicalobject(test4, f4);
		socialnetworkcircle2.addphysicalobject(test5, f5);
		socialnetworkcircle2.addphysicalobject(test6, f6);
		socialnetworkcircleTest2 = socialnetworkcircle2;	
	}
	
	@Test
	public void getObjectDistributionEntropytest() throws Exception {
		StellarSystemCircularOrbitAPIInitial();
		AtomStructureCircularOrbitAPIInitial();
		SocialNetworkCircleCircularOrbitAPIInitial();
		CircularOrbitAPIs<CentralObject,PlanetAPI> test1 = CircularOrbitAPIs.getStellarSystemCircularOrbitAPI();
		CircularOrbitAPIs<CentralObject,ElectronAPI> test2 = CircularOrbitAPIs.getAtomStructureCircularOrbitAPI();
		CircularOrbitAPIs<CentralObject,FriendAPI> test3 = CircularOrbitAPIs.getSocialNetworkCircleDifferenceAPI();
		System.out.printf("%f\n",test1.getObjectDistributionEntropy(stellarsystemTest1));
		System.out.printf("%f\n",test1.getObjectDistributionEntropy(stellarsystemTest2));
		System.out.printf("%f\n",test2.getObjectDistributionEntropy(atomstructureTest1));
		System.out.printf("%f\n",test2.getObjectDistributionEntropy(atomstructureTest2));
		System.out.printf("%f\n",test3.getObjectDistributionEntropy(socialnetworkcircleTest1));
		System.out.printf("%f\n",test3.getObjectDistributionEntropy(socialnetworkcircleTest2));
	}
	
	@Test
	public void getLogicalDistancetest() throws Exception{
		StellarSystemCircularOrbitAPIInitial();
		AtomStructureCircularOrbitAPIInitial();
		SocialNetworkCircleCircularOrbitAPIInitial();
		CircularOrbitAPIs<CentralObject,PlanetAPI> test1 = CircularOrbitAPIs.getStellarSystemCircularOrbitAPI();
		CircularOrbitAPIs<CentralObject,ElectronAPI> test2 = CircularOrbitAPIs.getAtomStructureCircularOrbitAPI();
		CircularOrbitAPIs<CentralObject,FriendAPI> test3 = CircularOrbitAPIs.getSocialNetworkCircleDifferenceAPI();
		PlanetAPI p1 = PhysicalObjectFactory.getPlanet("Earth","Solid","Blue","6378.137","1.49e8","29.783","CW","0");
		PlanetAPI p2 = PhysicalObjectFactory.getPlanet("Saturn","Liquid","Red","2378","1.49e6","2.33e5","CCW","39.21");
		FriendAPI f1 = PhysicalObjectFactory.getFriend("LisaWong", "25", "F");
		FriendAPI f2 = PhysicalObjectFactory.getFriend("TomWong", "61", "M");
		System.out.printf("%d\n",test1.getLogicalDistance(stellarsystemTest1, p1, p2));
		System.out.printf("%d\n",test2.getLogicalDistance(atomstructureTest1, electronlist.get(0), electronlist.get(1)));
		System.out.printf("%d\n",test3.getLogicalDistance(socialnetworkcircleTest1, f1, f2));
	}
	
}
