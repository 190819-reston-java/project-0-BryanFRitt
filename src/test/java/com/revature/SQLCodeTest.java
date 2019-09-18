package com.revature;

import static org.junit.Assert.*;

//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

public class SQLCodeTest {

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}

	// IDE says I should put this as static, but JUINT errors if I do so
	@SuppressWarnings("static-method")
	@Test
	public void testShowTransations() {
		assertTrue(SQLCode.showTransations("admin"));
	}

}
