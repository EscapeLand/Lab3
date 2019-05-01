package test.physicalObject;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import src.physicalObject.PhysicalObjectFactory;
import src.physicalObject.PlanetAPI;

public class PlanetTest {
	
	public List<PlanetAPI> initial() throws Exception {
		List<PlanetAPI> test = new ArrayList<>();
		PlanetAPI test1 = PhysicalObjectFactory.getPlanet("Earth","Solid","Blue","6378.137","1.49e8","29.783","CW","0");
		PlanetAPI test2 = PhysicalObjectFactory.getPlanet("Saturn","Liquid","Red","2378","1.49e6","2.33e5","CCW","39.21");
		test.add(test1);
		test.add(test2);
		return test;
	}

	@Test
	public void getNametest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		assertEquals("Earth",test1.getName());
		assertEquals("Saturn",test2.getName());
	}
	
	@Test
	public void getTypetest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		assertEquals("Solid",test1.getType());
		assertEquals("Liquid",test2.getType());
	}
	
	@Test
	public void getColortest() throws Exception {
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		assertEquals("Blue",test1.getColor());
		assertEquals("Red",test2.getColor());
	}
	
	@Test
	public void getRadiustest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		Double num1 = Double.valueOf("6378.137").doubleValue();
		Double num2 = Double.valueOf("2378").doubleValue();
		assertEquals(num1,test1.getRadius());
		assertEquals(num2,test2.getRadius());
	}
	
	@Test
	public void getOrbitRadiustest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		Double num1 = Double.valueOf("1.49e8").doubleValue();
		Double num2 = Double.valueOf("1.49e6").doubleValue();
		assertEquals(num1,test1.getOrbitRadius());
		assertEquals(num2,test2.getOrbitRadius());
	}
	
	@Test
	public void getOrbitAtest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		Double num1 = Double.valueOf("1.49e8").doubleValue();
		Double num2 = Double.valueOf("1.49e6").doubleValue();
		assertEquals(num1,test1.getOrbitA());
		assertEquals(num2,test2.getOrbitA());
	}
	
	@Test
	public void getOrbitBtest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		Double num1 = Double.valueOf("1.49e8").doubleValue();
		Double num2 = Double.valueOf("1.49e6").doubleValue();
		assertEquals(num1,test1.getOrbitB());
		assertEquals(num2,test2.getOrbitB());
	}
	
	@Test
	public void getVelocity() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		Double num1 = Double.valueOf("29.783").doubleValue();
		Double num2 = Double.valueOf("2.33e5").doubleValue();
		assertEquals(num1,test1.getVelocity());
		assertEquals(num2,test2.getVelocity());
	}
	
	@Test
	public void getDirctiontest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		assertEquals("CW",test1.getDirection());
		assertEquals("CCW",test2.getDirection());
	}
	
	@Test
	public void getInitialAngletest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		Double num1 = Double.valueOf("0").doubleValue();
		Double num2 = Double.valueOf("39.21").doubleValue();
		assertEquals(num1,test1.getInitialAngle());
		assertEquals(num2,test2.getInitialAngle());
	}
	
	@Test
	public void getCurrentAngletest() throws Exception{
		List<PlanetAPI> test = initial();
		PlanetAPI test1 = test.get(0);
		PlanetAPI test2 = test.get(1);
		Double num1 = Double.valueOf("0").doubleValue();
		Double num2 = Double.valueOf("39.21").doubleValue();
		assertEquals(num1,test1.getCurrentangle());
		assertEquals(num2,test2.getCurrentangle());
		test1.setCurrentangle(num2);
		test2.setCurrentangle(num1);
		assertEquals(num2,test1.getCurrentangle());
		assertEquals(num1,test2.getCurrentangle());
	}
}
