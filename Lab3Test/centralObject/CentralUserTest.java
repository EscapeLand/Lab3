package test.centralObject;

import static org.junit.Assert.*;

import org.junit.Test;

import src.centralObject.CentralObject;
import src.centralObject.CentralObjectFactory;

public class CentralUserTest {

	@Test
	public void test() throws Exception {
		CentralObject test5 = CentralObjectFactory.getCentralUser("a","30","M");
		CentralObject test6 = CentralObjectFactory.getCentralUser("b","20","F");
		assertEquals("a",test5.getName());
		assertEquals("b",test6.getName());
		assertEquals(30,test5.getAge());
		assertEquals(20,test6.getAge());
		assertEquals("M",test5.getSex());
		assertEquals("F",test6.getSex());
	}

}
