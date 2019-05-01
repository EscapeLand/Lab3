package test.centralObject;

import static org.junit.Assert.*;

import org.junit.Test;

import src.centralObject.CentralObject;
import src.centralObject.CentralObjectFactory;

public class StellarTest {

	@Test
	public void getNametest() throws Exception {
		CentralObject test1 = CentralObjectFactory.getStellar("Jupiter","12.28","12.28");
		CentralObject test2 = CentralObjectFactory.getStellar("Mercury","1.9885e30","1.9885e30");
		assertEquals("Jupiter",test1.getName());
		assertEquals("Mercury",test2.getName());
	}
	
	@Test
	public void getRadiustest() throws Exception{
		CentralObject test1 = CentralObjectFactory.getStellar("Jupiter","12.28","12.28");
		CentralObject test2 = CentralObjectFactory.getStellar("Mercury","1.9885e30","1.9885e30");
		CentralObject test3 = CentralObjectFactory.getStellar("Mars","12","12");
		Double testnum1 = Double.valueOf(12.28).doubleValue();
		Double testnum2 = Double.valueOf("1.9885e30").doubleValue();
		Double testnum3 = Double.valueOf("12").doubleValue();
		assertEquals(testnum1,test1.getRadius());
		assertEquals(testnum2,test2.getRadius());
		assertEquals(testnum3,test3.getRadius());
	}
	
	@Test
	public void getMasstest() throws Exception{
		CentralObject test1 = CentralObjectFactory.getStellar("Jupiter","12.28","12.28");
		CentralObject test2 = CentralObjectFactory.getStellar("Mercury","1.9885e30","1.9885e30");
		CentralObject test3 = CentralObjectFactory.getStellar("Mars","12","12");
		Double testnum1 = Double.valueOf(12.28).doubleValue();
		Double testnum2 = Double.valueOf("1.9885e30").doubleValue();
		Double testnum3 = Double.valueOf("12").doubleValue();
		assertEquals(testnum1,test1.getMass());
		assertEquals(testnum2,test2.getMass());
		assertEquals(testnum3,test3.getMass());
	}

}
