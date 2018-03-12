package com.revature.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.exception.InsufficientBalanceException;
import com.revature.exception.InvalidCredentialsException;
import com.revature.model.User;
import com.revature.service.ServiceUtil;

public class Tests {
	
	private ServiceUtil serviceUtil = ServiceUtil.getInstance();
	private String username = "ZeldaZebra";
	private String password = "password26";
	
	@Before
	public void registerNewUser() throws InvalidCredentialsException {
		serviceUtil.register(username, password);
		serviceUtil.login(username, password);
	}
	
	@After
	public void  deleteUser() {
		serviceUtil.logout();
		serviceUtil.deleteUser(username);
	}
	
	@Test
	public void testValidLogin() throws InvalidCredentialsException {
		assertEquals(new User("ZeldaZebra", "password26", 0), serviceUtil.login(username, password));
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void testInalidLogin() throws InvalidCredentialsException {
		serviceUtil.logout();
		serviceUtil.login("Invalid", "Credentials");
	}
	
	@Test
	public void testValidDeposit() throws InsufficientBalanceException {
		assertEquals(10d, serviceUtil.deposit(10), 0);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testNegativeDeposit() throws InsufficientBalanceException {
		serviceUtil.deposit(-1);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testZeroDeposit() throws InsufficientBalanceException {
		serviceUtil.deposit(0);
	}
	
	@Test
	public void testValidWithdrawal() throws InsufficientBalanceException {
		serviceUtil.deposit(100);
		assertEquals(90d, serviceUtil.withdraw(10), 0);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testNegativeWithdrawal() throws InsufficientBalanceException {
		serviceUtil.withdraw(-1);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testZeroWithdrawal() throws InsufficientBalanceException {
		serviceUtil.withdraw(0);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testSumTooLargeWithdrawal() throws InsufficientBalanceException {
		serviceUtil.withdraw(99999);
	}
	
	@Test
	public void testLogout() {
		assertEquals(null, serviceUtil.logout());
	}
}
