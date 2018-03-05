package com.revature;

import com.revature.controller.Controller;

/** 
 * Create an instance of your controller and launch your application.
 * 
 * Try not to have any logic at all on this class.
 */
public class Main {

	public static void main(String[] args) {
		
		Controller controller = Controller.getController();
		controller.start();
		
		controller.register("James Kempf", "jamesk4321@gmail.com", "123qwe!@#");
		controller.login("jamesk4321@gmail.com", "123qwe!@!");
		controller.login("jamesk4321@gmail.com", "123qwe!@#");
		controller.deposit(100);
		controller.withdraw(30);
		controller.withdraw(1000);
		controller.viewBalance();
		controller.printTransactions();
		controller.logout();
	}
}
