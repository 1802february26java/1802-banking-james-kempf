package com.revature.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.revature.exception.InsufficientBalanceException;
import com.revature.exception.InvalidCredentialsException;

public class User {

	private String username;
	private String password;
	private double balance = 0;
	private ArrayList<String> transactions = new ArrayList<>();
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(String username, String password, double balance) {
		this.username = username;
		this.password = password;
		this.balance = balance;
	}
	
	public double getBalance() {
		return balance;
	}

	public void deposit(double sum) {
		if (sum > 0) {
			balance += sum;
			transactions.add("Deposit: " + sum + " - " + LocalDateTime.now());
		}
	}
	
	public void withdraw(double sum) throws InsufficientBalanceException {
		if (sum > 0) {
 			if (balance >= sum) {
				balance -= sum;
				transactions.add("Withdraw: " + sum + " - " + LocalDateTime.now());
			} else {
				throw new InsufficientBalanceException("Insufficient Balance: " + balance);
			}
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public ArrayList<String> getTransactions() {
		return transactions;
	}
	
	// Authenticates a user when logging in
	public boolean authenticate(String username, String password) throws InvalidCredentialsException {
		if (this.username.equals(username) && this.password.equals(password)) {
			return true;
		} else {
			throw new InvalidCredentialsException("Invalid Username Or Password");
		}
	}

	@Override
	public String toString() {
		return "User [Username=" + username + ", Balance=" + balance + "]";
	}
}
