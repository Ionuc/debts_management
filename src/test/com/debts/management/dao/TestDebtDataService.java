package test.com.debts.management.dao;

import java.math.BigDecimal;

import main.com.debts.api.Debt;
import main.com.debts.api.User;
import main.com.debts.management.dao.DebtDataService;

import org.junit.Test;

public class TestDebtDataService {

	private DebtDataService debtDataService = new DebtDataService();
	private User loggedUser = new User();

	@Test
	public void testCreate() {
		Debt debt = new Debt();
		debt.setDescription("description");
		debt.setFromUsername("fromUsername");
		debt.setToUsername("toUsername");
		debt.setValue(new BigDecimal("3.4"));
		debtDataService.create(debt, loggedUser);
	}
	
	@Test
	public void testOptimizedDebts()
	{
		createDebt("test1", "test2", "100");
		createDebt("test2", "test1", "34");
		createDebt("test1", "test3", "200");
		createDebt("test3", "test1", "350");
		debtDataService.optimizeDebts(loggedUser);
	}
	
	private Debt createDebt(String from, String to , String value)
	{
		Debt debt = new Debt();
		debt.setFromUsername(from);
		debt.setToUsername(to);
		debt.setValue(new BigDecimal(value));
		debtDataService.create(debt, loggedUser);
		return debt;
	}
}
