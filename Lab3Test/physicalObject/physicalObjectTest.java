package test.physicalObject;

import static org.junit.Assert.*;

import org.junit.Test;

import src.physicalObject.ElectronAPI;
import src.physicalObject.FriendAPI;
import src.physicalObject.PhysicalObjectFactory;
import src.physicalObject.PlanetAPI;

public class physicalObjectTest {

	@Test
	public void getPhysicalObjecttest() throws Exception {
		PlanetAPI test1 = PhysicalObjectFactory.getPlanet("Earth","Solid","Blue","6378.137","1.49e8","29.783","CW","0");
		PlanetAPI test2 = PhysicalObjectFactory.getPlanet("Mercury","Solid","Dark","1378.137","1.49e7","69","CW","20.085");
		ElectronAPI test3 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI test4 = PhysicalObjectFactory.getElectron(2);
		FriendAPI test5 = PhysicalObjectFactory.getFriend("LisaWong", "25", "F");
		FriendAPI test6 = PhysicalObjectFactory.getFriend("TomWong", "61", "M");
		assertEquals(test1.getClass(),test2.getClass());
		assertEquals(test3.getClass(),test4.getClass());
		assertEquals(test5.getClass(),test6.getClass());
	}

}
