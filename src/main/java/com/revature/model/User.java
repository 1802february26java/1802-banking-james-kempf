package com.revature.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.revature.exception.InsufficientBalanceException;
import com.revature.exception.InvalidCredentialsException;

public class User {

	private String name;
	private String username;
	private String password;
	private double balance = 0;
	private ArrayList<String> transactions = new ArrayList<>();
	
	public User(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double sum) {
		balance += sum;
		transactions.add("Deposit: " + sum + " - " + LocalDateTime.now());
	}
	
	public void withdraw(double sum) throws InsufficientBalanceException {
		if (balance >= sum) {
			balance -= sum;
			transactions.add("Withdraw: " + sum + " - " + LocalDateTime.now());
		} else {
			throw new InsufficientBalanceException("Insufficient Balance: " + balance);
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public ArrayList<String> getTransactions() {
		return transactions;
	}
	
	// Authenticates a user when logging in
	public boolean authenticate(String username, String password) throws InvalidCredentialsException {
		if(this.username == username && this.password == password) {
			return true;
		} else {
			throw new InvalidCredentialsException("Invalid username or password");
		}
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", username=" + username + ", balance=" + balance + "]";
	}
}
