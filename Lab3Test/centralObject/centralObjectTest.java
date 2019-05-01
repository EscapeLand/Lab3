package test.centralObject;

import static org.junit.Assert.*;

import org.junit.Test;

import src.centralObject.CentralObject;
import src.centralObject.CentralObjectFactory;

public class centralObjectTest {

	@Test
	public void getCentralObjecttest() throws Exception {
		CentralObject test1 = CentralObjectFactory.getElement("A");
		CentralObject test2 = CentralObjectFactory.getElement("B");
		CentralObject test3 = CentralObjectFactory.getStellar("a","1","1");
		CentralObject test4 = CentralObjectFactory.getStellar("b","2","2");
		CentralObject test5 = CentralObjectFactory.getCentralUser("a","30","M");
		CentralObject test6 = CentralObjectFactory.getCentralUser("b","20","F");
		assertEquals(test1.getClass(),test2.getClass());
		assertEquals(test3.getClass(),test4.getClass());
		assertEquals(test5.getClass(),test6.getClass());
	}

}
