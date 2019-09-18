/**
 * 
 */
package com.revature;

//import static org.junit.Assert.*;

import java.math.BigDecimal;

//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author bryan
 *
 */
public class MainTest {

//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//	}

	/**
	 * Test method for {@link com.revature.Main#getBalance(java.lang.String)}.
	 */
	// IDE tells me I should put this as static, but when I run it, JUINT says ..."should not be static" error
	@SuppressWarnings("static-method")
	@Test
	public void testGetBalance() {
		assert(Main.getBalance("admin").compareTo(BigDecimal.valueOf(0)) != -1);
	}
	
}
