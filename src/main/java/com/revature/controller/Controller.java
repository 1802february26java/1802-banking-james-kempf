package com.revature.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.revature.exception.InsufficientBalanceException;
import com.revature.exception.InvalidCredentialsException;
import com.revature.model.User;

public class Controller {

	private static Controller controller = new Controller();
	private HashMap<String, User> users = new HashMap<>();
	private User currentUser;
	
	public static Controller getController() {
		return controller;
	}
	
	// start input scanner and complete commands
	public void start() {
		Scanner input = new Scanner (System.in);
		
		while (input.hasNext()) {
			String[] command = input.nextLine().split(" ");
			switch(command[0]) {
			case "register":
				if(command.length == 4) {
					register(command[1], command[2], command[3]);
				} else {
					System.out.println("Invalid Parameters");
				}
				break;
			case "login":
				if(command.length == 3) {
					login(command[1], command[2]);
				} else {
					System.out.println("Invalid Parameters");
				}
				break;
			case "logout":
				logout();
				break;
			case "balance":
				viewBalance();
				break;
			case "deposit":
				if(command.length == 2) {
					deposit(Double.parseDouble(command[1]));
				} else {
					System.out.println("Invalid Parameters");
				}
				break;
			case "withdraw":
				if(command.length == 2) {
					withdraw(Double.parseDouble(command[1]));
				} else {
					System.out.println("Invalid Parameters");
				}
				break;
			case "transactions":
				printTransactions();
				break;
			case "exit":
				System.exit(0);
				break;
			}
		}
		input.close();
	}
	
	// Add new user
	public void register(String name, String username, String password) {
		User newUser = new User(name, username, password);
		users.put(username, newUser);
		System.out.println(newUser.toString());
	}
	
	// Log in user. Checks username and password
	public void login(String username, String password) {
		try {
			User u = users.get(username);
			if(u.authenticate(username, password)) {
				currentUser = u;
				System.out.println("Login Successful");
			}
		} catch(InvalidCredentialsException e) {
			System.out.println(e.getMessage());
		} catch(NullPointerException e) {
			System.out.println("Invalid Credentials");
		}
	}
	
	// Set current user to null
	public void logout() {
		if(currentUser == null) {
			System.out.println("Already logged out");
		} else {
			currentUser = null;
			System.out.println("Logout Successful");
		}
	}
	
	// Prints balance of current user
	public void viewBalance() {
		if(currentUser == null) {
			System.out.println("Please login");
		} else {
			System.out.println("Current Balance: " + currentUser.getBalance());
		}
	}
	
	// Deposit sum for current user
	public void deposit(double sum) {
		if(currentUser == null) {
			System.out.println("Please login");
		} else if(sum <= 0) {
			System.out.println("Insufficient Sum");
		} else {
			currentUser.deposit(sum);
			System.out.println("Deposit Successful");
		}
	}
	
	// Withdraw a sum from current user
	public void withdraw(double sum) {
		if(currentUser == null) {
			System.out.println("Please login");
		} else if(sum <= 0) {
			System.out.println("Insufficient Sum");
		} else {
			try {
				currentUser.withdraw(sum);
				System.out.println("Withdraw Successful");
			} catch(InsufficientBalanceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	// Prints transactions of current user
	public void printTransactions() {
		if(currentUser == null) {
			System.out.println("Please login");
		} else {
			ArrayList<String> transactions = currentUser.getTransactions();
			for(String i: transactions) {
				System.out.println(i);
			}
		}
	}

}
