package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentTest{
	
	private Argument a;
	

	@Test
	public void testThatNameIsCreated(){
		a = new Argument("length");
		assertEquals("length", a.getName());
	}
	
	@Test
	public void testThatValueTypeIsCreated(){
		a = new Argument("length", "float");
		assertEquals("float", a.getValueType());
	}
	
	@Test
	public void testThatValueIsAdded(){
		a = new Argument("length", "float");
		a.setValue("5");
		assertEquals("5", a.getValue());
		}

}