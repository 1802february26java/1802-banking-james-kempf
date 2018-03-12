package com.revature.repository;

import java.util.Set;

import com.revature.model.User;

public interface UserRepository {

	/**
	 * Insert new user into in the database
	 */
	public User insertUser(String username, String password);
	
	/**
	 * Delete user (for testing)
	 */
	public void deleteUser(String username);
	
	/**
	 * Select all users form the database
	 */
	public Set<User> selectAllUsers();
	
	/**
	 * Select one user based on username
	 */
	public User getUserByUsername(String username);
	
	/**
	 * Set balance of user to specified balance
	 */
	public boolean updateBalance(User user, double balance);
	
	/**
	 * Get the current balance for user
	 */
	public double getBalance(User user);
}
