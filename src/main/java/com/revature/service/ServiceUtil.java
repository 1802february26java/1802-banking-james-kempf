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
	 * Register a new user
	 * Returns true if registered
	 */
	public User register(String username, String password) {
		User user = repository.insertUser(username, password);
		if (user != null) {
			System.out.println("Registry successful");
		} else {
			System.out.println("Error while registering user");
		}
		return user;
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
	public User logout() {
		currentUser = null;
		System.out.println("Logout successful");
		return currentUser;
	}
	
	/**
	 * Get current balance of user
	 */
	public double getBalance() {
		if (currentUser != null) {
			double balance = repository.getBalance(currentUser);
			System.out.println("Current Balance: " + balance);
			return balance;
		}
		System.out.println("Not logged in");
		return -1;
	}
	
	/**
	 * Deposit sum into users balance
	 * returns Updated Balance
	 */
	public double deposit(double sum) throws InsufficientBalanceException {
		System.out.println(currentUser);
		if (currentUser == null) {
			System.out.println("Not logged in");
			return -1;
		} else if (sum > 0) {
			double newBalance = currentUser.getBalance() + sum;
			repository.updateBalance(currentUser, newBalance);
			currentUser.setBalance(newBalance);
			System.out.println("Balance: " + newBalance);
			return newBalance;
		}
		throw new InsufficientBalanceException("Insufficient sum");
	}
	
	/**
	 * Withdraw sum from users balance
	 * returns Updated Balance
	 */
	public double withdraw(double sum) throws InsufficientBalanceException {
		if (currentUser == null) {
			System.out.println("Not logged in");
		} else {
			double balance = currentUser.getBalance();
			if (sum > 0 && sum <= balance) {
				double newBalance = balance - sum;
				repository.updateBalance(currentUser, newBalance);
				currentUser.setBalance(balance - sum);
				System.out.println("Balance: " + newBalance);
				return newBalance;
			} else {
				throw new InsufficientBalanceException("Insufficient sum");
			}
		}
		System.out.println("Insufficient sum");
		return -1;
	}
	
	/**
	 * Delete user for testing purposes
	 */
	public void deleteUser(String username) {
		repository.deleteUser(username);
	}
	
	public static void main(String[] args) {
		ServiceUtil service = ServiceUtil.getInstance();
		logger.trace(service.login("AnthonyAardvark", "password1").toString());
		logger.trace(service.getUser());
		try {
			service.deposit(100);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		service.getBalance();
		try {
			service.withdraw(100);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		service.logout();
	}
}
