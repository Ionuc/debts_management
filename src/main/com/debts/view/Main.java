package main.com.debts.view;

import main.com.debts.management.dao.UserDataService;

public class Main {

	public static void main(String[] args) {
		UserDataService userDataService = new UserDataService();
		
		View view = new View(userDataService);
	}	

}
