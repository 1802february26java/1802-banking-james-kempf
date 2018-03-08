package com.revature.service;

import org.apache.log4j.Logger;

import com.revature.exception.InsufficientBalanceException;
import com.revature.model.User;
import com.revature.repository.UserRepository;
import com.revature.repository.UserRepositoryJdbc;

public class ServiceUtil {

	private static ServiceUtil service = new ServiceUtil();
	private UserRepository repository = UserRepositoryJdbc.getInstance();
	private static Logger logger = Logger.getLogger(ServiceUtil.class);
	private User currentUser = null;
	
	private ServiceUtil() {};
	
	public static ServiceUtil getInstance() {
		return service;
	}
	
	public User getUser() {
		return currentUser;
	}
	
	/**
	 * Authenticate user with username and password
	 */
	public User login(String username, String password) {
		User user = repository.getUserByUsername(username);
		if (user.getPassword().equals(password)) {
			currentUser = user;
			System.out.println("Login successful");
			return user;
		}
		System.out.println("Invalid credentials");
		return null;
	}
	
	/**
	 * Logout user
	 */
	public void logout() {
		currentUser = null;
		System.out.println("Logout successful");
	}
	
	/**
	 * Get current balance of user
	 */
	public void getBalance() {
		if (currentUser != null) {
			double balance = repository.getBalance(currentUser);
			System.out.println("Current Balance: " + balance);
		}
		System.out.println("Not logged in");
	}
	
	/**
	 * Deposit sum into users balance
	 */
	public void deposit(double sum) throws InsufficientBalanceException {
		System.out.println(currentUser);
		if (currentUser == null) {
			System.out.println("Not logged in");
		} else if (sum > 0) {
			double newBalance = currentUser.getBalance() + sum;
			repository.updateBalance(currentUser, newBalance);
			currentUser.setBalance(newBalance);
			System.out.println("Balance: " + newBalance);
		}
		throw new InsufficientBalanceException("Insufficient sum");
	}
	
	/**
	 * Withdraw sum from users balance
	 */
	public void withdraw(double sum) throws InsufficientBalanceException {
		if (currentUser == null) {
			System.out.println("Not logged in");
		} else {
			double balance = currentUser.getBalance();
			if (sum > 0 && sum <= balance) {
				double newBalance = balance - sum;
				repository.updateBalance(currentUser, newBalance);
				currentUser.setBalance(balance - sum);
				System.out.println("Balance: " + newBalance);
			} else {
				throw new InsufficientBalanceException("Insufficient sum");
			}
		}
		System.out.println("Insufficient sum");
	}
	
	public static void main(String[] args) {
		ServiceUtil service = ServiceUtil.getInstance();
		logger.trace(service.login("AnthonyAardvark", "password1").toString());
		logger.trace(service.getUser());
		service.deposit(100);
		service.getBalance();
		service.withdraw(100);
		
		
		service.logout();
	}
}
