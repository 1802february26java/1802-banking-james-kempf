package com.revature.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.revature.exception.InsufficientBalanceException;
import com.revature.exception.InvalidCredentialsException;
import com.revature.model.User;
import com.revature.service.ServiceUtil;

public class Controller {

	private static final Controller controller = new Controller();
	private ServiceUtil serviceUtil = ServiceUtil.getInstance();
	private HashMap<String, User> users;
	private User currentUser;
	
	public static Controller getInstance() {
		return controller;
	}
	
	// start input scanner and complete commands
	public void start() {
		Scanner input = new Scanner (System.in);
		while (input.hasNext()) {
			String[] command = input.nextLine().split(" ");
			switch(command[0]) {
			case "register":
				if (command.length == 3) {
					register(command[1], command[2]);
				} else {
					System.out.println("Invalid Parameters");
				}
				break;
			case "login":
				if (command.length == 3) {
					currentUser = serviceUtil.login(command[1], command[2]);
				} else {
					System.out.println("Invalid Parameters");
				}
				break;
			case "logout":
				serviceUtil.logout();
				break;
			case "balance":
				viewBalance();
				break;
			case "deposit":
				System.out.println(serviceUtil.getUser());
				if (command.length == 2) {
					serviceUtil.deposit(Double.parseDouble(command[1]));
				} else {
					System.out.println("Invalid Parameters");
				}
				break;
			case "withdraw":
				if (command.length == 2) {
					withdraw(Double.parseDouble(command[1]));
				} else {
					System.out.println("Invalid Parameters");
				}
				break;
			case "transactions":
				printTransactions();
				break;
			case "exit":
				if (currentUser != null) {
					serviceUtil.logout();
				}
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Command");
				break;
			}
		}
		input.close();
	}
	
	// Add new user
	public boolean register(String username, String password) {
		if (users.containsKey(username)) {
			System.out.println("Username Is Already In User");
			return false;
		} else {
			User newUser = new User(username, password);
			users.put(username, newUser);
			System.out.println(newUser.toString());
			return true;
		}
	}
	
	// Prints balance of current user
	public void viewBalance() {
		if (currentUser == null) {
			System.out.println("Please login");
		} else {
			System.out.println("Current Balance: " + currentUser.getBalance());
		}
	}
	
	// Withdraw a sum from current user
	public boolean withdraw(double sum) {
		if (currentUser == null) {
			System.out.println("Please login");
		} else if (sum <= 0) {
			System.out.println("Insufficient Sum");
		} else {
			currentUser.setBalance(sum);
			System.out.println("Withdraw Successful");
			return true;
		}
		return false;
	}
	
	// Prints transactions of current user
	public void printTransactions() {
		if (currentUser == null) {
			System.out.println("Please Login");
		} else {
			ArrayList<String> transactions = currentUser.getTransactions();
			if (transactions.isEmpty()) {
				System.out.println("No Transactions");
			} else {
				for (String i: transactions) {
					System.out.println(i);
				}
			}
		}
	}

}
