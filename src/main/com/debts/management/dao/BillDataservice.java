package main.com.debts.management.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import main.com.debts.api.Bill;
import main.com.debts.api.Debt;
import main.com.debts.api.User;

public class BillDataservice {

	private DebtDataService debtDataService;

	public BillDataservice(DebtDataService debtDataService) {
		this.debtDataService = debtDataService;
	}

	public void createBill(Bill bill, User loggedUser) {

		List<String> newUsers = filterUsers(bill);
		BigDecimal valuePerUser = bill.getValue().divide(
				new BigDecimal(bill.getParticipants().size()), 2, RoundingMode.DOWN);
		for (String user : newUsers) {
			Debt debt = new Debt();
			debt.setDescription(bill.getCompanyDescription());
			debt.setFromUsername(user);
			debt.setToUsername(bill.getOwner());
			debt.setValue(valuePerUser);
			debtDataService.create(debt, loggedUser);
		}
	}

	private List<String> filterUsers(Bill bill) {
		List<String> newUsers = new ArrayList<String>();
		for (String user : bill.getParticipants()) {
			if (!user.equals(bill.getOwner())) {
				newUsers.add(user);
			}
		}
		return newUsers;
	}
}
