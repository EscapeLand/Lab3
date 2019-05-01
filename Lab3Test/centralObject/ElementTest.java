package test.centralObject;

import static org.junit.Assert.*;

import org.junit.Test;

import src.centralObject.CentralObject;
import src.centralObject.CentralObjectFactory;

public class ElementTest {

	@Test
	public void getNametest() throws Exception {
		CentralObject test1 = CentralObjectFactory.getElement("Ca");
		CentralObject test2 = CentralObjectFactory.getElement("N");
		assertEquals("Ca",test1.getName());
		assertEquals("N",test2.getName());
	}

}
