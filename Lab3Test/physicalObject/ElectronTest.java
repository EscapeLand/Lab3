package test.physicalObject;

import static org.junit.Assert.*;

import org.junit.Test;

import src.physicalObject.ElectronAPI;
import src.physicalObject.PhysicalObjectFactory;

public class ElectronTest {

	@Test
	public void getTracknumTest() throws Exception {
		ElectronAPI test1 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI test2 = PhysicalObjectFactory.getElectron(2);
		assertEquals(1,test1.getTracknum());
		assertEquals(2,test2.getTracknum());
		test1.setTracknum(2);
		test2.setTracknum(1);
		assertEquals(2,test1.getTracknum());
		assertEquals(1,test2.getTracknum());
	}
	
	@Test
	public void getCurrentangleTest() throws Exception{
		ElectronAPI test1 = PhysicalObjectFactory.getElectron(1);
		ElectronAPI test2 = PhysicalObjectFactory.getElectron(2);
		test1.setCurrentangle(0.00);
		test2.setCurrentangle(359.99);
		Double num1 = 0.00;
		Double num2 = 359.99;
		assertEquals(num1,test1.getCurrentangle());
		assertEquals(num2,test2.getCurrentangle());
	}
}
