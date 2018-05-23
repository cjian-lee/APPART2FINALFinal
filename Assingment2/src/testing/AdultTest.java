package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Adult;

public class AdultTest {

	Adult adult1;
	Adult adult2;
	
	@Before
	public void setUp() {
		adult1= new Adult("Zoe Foster","pic.jpg", "Founder of ZFX", "F", 28, "VIC"); 
		adult2= new Adult("Mark Bourne", "bourne.jpg", "fighting", "M", 30, "VIC");
	}
	
	@Test
	public void testGetName() {
		assertEquals("Zoe Foster",adult1.getName());
	}
	@Test
	public void testGetName1() {
		assertEquals("Mark Bourne",adult2.getName());
	}
	
	@Test
	public void testGetImage() {
		assertEquals("pic.jpg",adult1.getImage());
	}
	
	@Test
	public void testGetImage1() {
		assertEquals("bourne.jpg",adult2.getImage());
	}

	@Test
	public void testGetStatus() {
		assertEquals("Founder of ZFX",adult1.getStatus());
	}
	@Test
	public void testGetStatus1() {
		assertEquals("fighting",adult2.getStatus());
	}
	@Test
	public void testGetGender() {
		assertEquals("F",adult1.getGender());
	}
	@Test
	public void testGetGender1() {
		assertEquals("M",adult2.getGender());
	}
	@Test
	public void testGetAge() {
		assertEquals(28,adult1.getAge());
	}
	@Test
	public void testGetAge1() {
		assertEquals(30,adult2.getAge());
	}
	@Test
	public void testGetState() {
		assertEquals("VIC",adult1.getState());
	}
	@Test
	public void testGetState1() {
		assertEquals("VIC",adult2.getState());
	}
	
}
