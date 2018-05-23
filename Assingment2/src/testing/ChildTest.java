package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Adult;
import main.Child;

public class ChildTest {

	Child child1;
	Child child2;
	
	@Before
	public void setUp() {
		child1= new Child("James Bond", "007.jpg", "spy stuff", "M", 16, "SA");
		child2= new Child("Hannah White", "Hannah.png", "student at PLC", "F", 14, "VIC");
	}
	
	@Test
	public void testGetName() {
		assertEquals("James Bond",child1.getName());
	}
	@Test
	public void testGetName1() {
		assertEquals("Hannah White",child2.getName());
	}
	
	@Test
	public void testGetImage() {
		assertEquals("007.jpg",child1.getImage());
	}
	
	@Test
	public void testGetImage1() {
		assertEquals("Hannah.png",child2.getImage());
	}

	@Test
	public void testGetStatus() {
		assertEquals("spy stuff",child1.getStatus());
	}
	@Test
	public void testGetStatus1() {
		assertEquals("student at PLC",child2.getStatus());
	}
	@Test
	public void testGetGender() {
		assertEquals("M",child1.getGender());
	}
	@Test
	public void testGetGender1() {
		assertEquals("F",child2.getGender());
	}
	@Test
	public void testGetAge() {
		assertEquals(16,child1.getAge());
	}
	@Test
	public void testGetAge1() {
		assertEquals(14,child2.getAge());
	}
	@Test
	public void testGetState() {
		assertEquals("SA",child1.getState());
	}
	@Test
	public void testGetState1() {
		assertEquals("VIC",child2.getState());
	}
}
	
