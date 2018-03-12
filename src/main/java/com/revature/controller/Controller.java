package com.revature.controller;

import java.util.Scanner;

import com.revature.service.ServiceUtil;

public class Controller {

	private static final Controller controller = new Controller();
	private ServiceUtil serviceUtil = ServiceUtil.getInstance();
	
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
					serviceUtil.register(command[1], command[2]);
				} else {
					System.out.println("Invalid parameters");
				}
				break;
			case "login":
				if (command.length == 3) {
					try {
						serviceUtil.login(command[1], command[2]);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("Invalid parameters");
				}
				break;
			case "logout":
				serviceUtil.logout();
				break;
			case "balance":
				serviceUtil.getBalance();
				break;
			case "deposit":
				System.out.println(serviceUtil.getUser());
				if (command.length == 2) {
					try {
						serviceUtil.deposit(Double.parseDouble(command[1]));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("Invalid parameters");
				}
				break;
			case "withdraw":
				if (command.length == 2) {
					try {
						serviceUtil.withdraw(Double.parseDouble(command[1]));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("Invalid parameters");
				}
				break;
			case "exit":
				if (serviceUtil.getUser() != null) {
					serviceUtil.logout();
				}
				System.exit(0);
				break;
			default:
				System.out.println("Invalid command");
				break;
			}
		}
	}
}
