package test.track;

import static org.junit.Assert.*;

import org.junit.Test;
import src.track.Track;
import src.track.TrackFactory;

public class trackTest {

	@Test
	public void testGetTrack() throws Exception {
		Track test1 = TrackFactory.getStellarSystemTrack("1.00e9");
		Track test2 = TrackFactory.getStellarSystemTrack("1.00e9");
		Track test3 = TrackFactory.getAtomStructureTrack("1.00e9");
		Track test4 = TrackFactory.getAtomStructureTrack("1.00e9");
		Track test5 = TrackFactory.getSocialNetworkCircleTrack("1.00e9");
		Track test6 = TrackFactory.getSocialNetworkCircleTrack("1.00e9");
		assertEquals(test1.getClass(),test2.getClass());
		assertEquals(test3.getClass(),test4.getClass());
		assertEquals(test5.getClass(),test6.getClass());
	}

	@Test
	public void StellarSystemTracktest() throws Exception{
		Track test = TrackFactory.getStellarSystemTrack("1.00e9");
		Double temp = Double.valueOf("1.00e9").doubleValue();
		assertEquals(temp,test.getOrbitRadius());
		assertEquals(temp,test.getOrbitA());
		assertEquals(temp,test.getOrbitB());
	}
	
	@Test
	public void AtomStructureTracktest() throws Exception{
		Track test = TrackFactory.getAtomStructureTrack("1.00e9");
		Double temp = Double.valueOf("1.00e9").doubleValue();
		assertEquals(temp,test.getOrbitRadius());
	}
	
	@Test
	public void SocialNetworkCircletest() throws Exception{
		Track test = TrackFactory.getSocialNetworkCircleTrack("1.00e9");
		Double temp = Double.valueOf("1.00e9").doubleValue();
		assertEquals(temp,test.getOrbitRadius());
	}
}
