package test.APIs;

import static org.junit.Assert.*;

import org.junit.Test;

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
		stellarsystemTest1=stellarsystem1;
		
		CircularOrbit<CentralObject,PlanetAPI> stellarsystem2 = stellarsystemfactory.getCircularOrbit();
		CentralObject testcenter = CentralObjectFactory.getStellar("Sun","6.96392e5","1.9885e30");
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
		atomstructure1.addphysicalobject(test1, e1);
		atomstructure1.addphysicalobject(test2, e2);
		atomstructure1.addphysicalobject(test2, e3);
		atomstructure1.addphysicalobject(test3, e4);
		atomstructure1.addphysicalobject(test3, e5);
		atomstructure1.addphysicalobject(test3, e6);
		atomstructureTest1=atomstructure1;
		
		CircularOrbit<CentralObject,ElectronAPI> atomstructure2 = atomstructurefactory.getCircularOrbit();
		CentralObject testelement2 = CentralObjectFactory.getElement("Er");
		atomstructure2.setcentralobject(testelement2);
		Track test4 = TrackFactory.getAtomStructureTrack("1.00");
		Track test5 = TrackFactory.getAtomStructureTrack("2.00");
		Track test6 = TrackFactory.getAtomStructureTrack("3.00");
		atomstructure1.addTrack(test4);
		atomstructure1.addTrack(test5);
		atomstructure1.addTrack(test6);
		ElectronAPI e7 = PhysicalObjectFactory.getElectron(0);
		ElectronAPI e8 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI e9 = PhysicalObjectFactory.getElectron(2);
		atomstructure1.addphysicalobject(test4, e7);
		atomstructure1.addphysicalobject(test5, e8);
		atomstructure1.addphysicalobject(test6, e9);
		atomstructureTest2=atomstructure2;
		
	}
	
	public void SocialNetworkCircleCircularOrbitAPI() throws Exception{
		ConcreteCircularOrbitFactory<CentralObject,FriendAPI> socialnetworkcirclefactory = new SocialNetworkCircleFactory();
		CircularOrbit<CentralObject,FriendAPI> socialnetworkcircle1 = socialnetworkcirclefactory.getCircularOrbit();
		CentralObject testcenter = CentralObjectFactory.getCentralUser("TommyWong","30","M");
		socialnetworkcircle1.setcentralobject(testcenter);
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
		socialnetworkcircleTest1 = socialnetworkcircle1;
		
		CircularOrbit<CentralObject,FriendAPI> socialnetworkcircle2 = socialnetworkcirclefactory.getCircularOrbit();
	}
	
	@Test
	public void test() {
		
	}

}
