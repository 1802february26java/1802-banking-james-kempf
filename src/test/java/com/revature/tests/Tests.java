package com.revature.tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.controller.Controller;

public class Tests {
	
	static Controller controller = Controller.getController();
	static String username = "example@gmail.com";
	static String password = "123QWE!@#";
	
	@BeforeClass
	public static void initialize() {
		controller.register(username, password);
		controller.login(username, password);
		controller.deposit(1000);
		controller.logout();
	}
	
	@Test
	public void testInvalidLogin() {
		assertEquals(false, controller.login("invalid", "credentials"));
	}
	
	@Test
	public void testValidLogin() {
		assertEquals(true, controller.login(username, password));
	}
	
	@Test
	public void testValidDeposit() {
		assertEquals(true, controller.deposit(100));
	}
	
	@Test
	public void testInvalidDeposit() {
		assertEquals(false, controller.deposit(-1));
	}
	
	@Test
	public void testValidWithdrawal() {
		assertEquals(true, controller.withdraw(10));
	}
	
	@Test
	public void testNegativeWithdrawal() {
		assertEquals(false, controller.withdraw(-1));
	}
	
	@Test
	public void testSumTooLargeWithdrawal() {
		assertEquals(false, controller.withdraw(99999));
	}
}
