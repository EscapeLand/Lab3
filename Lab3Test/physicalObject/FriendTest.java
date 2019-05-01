package test.physicalObject;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import src.physicalObject.FriendAPI;
import src.physicalObject.PhysicalObjectFactory;

public class FriendTest {

	public List<FriendAPI> initial() throws Exception {
		List<FriendAPI> test = new ArrayList<>();
		FriendAPI test1 = PhysicalObjectFactory.getFriend("LisaWong", "25", "F");
		FriendAPI test2 = PhysicalObjectFactory.getFriend("TomWong", "61", "M");
		test.add(test1);
		test.add(test2);
		return test;
	}
	
	@Test
	public void getNametest() throws Exception{
		List<FriendAPI> test = initial();
		FriendAPI test1 = test.get(0);
		FriendAPI test2 = test.get(1);
		assertEquals("LisaWong",test1.getName());
		assertEquals("TomWong",test2.getName());
	}
	
	@Test
	public void getAgetest() throws Exception{
		List<FriendAPI> test = initial();
		FriendAPI test1 = test.get(0);
		FriendAPI test2 = test.get(1);
		assertEquals(25,test1.getAge());
		assertEquals(61,test2.getAge());
	}

	@Test
	public void getSextest() throws Exception{
		List<FriendAPI> test = initial();
		FriendAPI test1 = test.get(0);
		FriendAPI test2 = test.get(1);
		assertEquals("F",test1.getSex());
		assertEquals("M",test2.getSex());
	}
	
	@Test
	public void getTracknum() throws Exception{
		List<FriendAPI> test = initial();
		FriendAPI test1 = test.get(0);
		FriendAPI test2 = test.get(1);
		test1.setTracknum(0);
		test2.setTracknum(1);
		assertEquals(0,test1.getTracknum());
		assertEquals(1,test2.getTracknum());
	}
	
	@Test
	public void getCurrentAngletest() throws Exception{
		List<FriendAPI> test = initial();
		FriendAPI test1 = test.get(0);
		FriendAPI test2 = test.get(1);
		test1.setCurrentangle(0.00);
		test2.setCurrentangle(359.99);
		Double num1 = 0.00;
		Double num2 = 359.99;
		assertEquals(num1,test1.getCurrentangle());
		assertEquals(num2,test2.getCurrentangle());
	}
}
