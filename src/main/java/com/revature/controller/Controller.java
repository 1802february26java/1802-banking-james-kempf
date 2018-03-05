package com.revature.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.revature.exception.InsufficientBalanceException;
import com.revature.exception.InvalidCredentialsException;
import com.revature.model.User;

public class Controller {

	private static Controller controller = new Controller();
	HashMap<String, User> users = new HashMap<>();
	User currentUser;
	
	public static Controller getController() {
		return controller;
	}
	
	public void logIn(String username, String password) {
		User u = users.get(username);
		try {
			if(u.authenticate(username, password)) {
				currentUser = u;
				System.out.println("Login Successful");
			}
		} catch(InvalidCredentialsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void logout() {
		currentUser = null;
	}
	
	public void register(String name, String username, String password) {
		User newUser = new User(name, username, password);
		users.put(username, newUser);
		System.out.println(newUser.toString());
	}
	
	public void viewBalance() {
		System.out.println("Current Balance: " + currentUser.getBalance());
	}
	
	public void deposit(double sum) {
		currentUser.deposit(sum);
	}
	
	public void withdraw(double sum) {
		try {
			currentUser.withdraw(sum);
		} catch(InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void printTransactions() {
		ArrayList<String> transactions = currentUser.getTransactions();
		for(String i: transactions) {
			System.out.println(i);
		}
	}

}
