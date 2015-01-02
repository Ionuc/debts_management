package main.com.debts.management.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.com.debts.api.Debt;
import main.com.debts.api.User;
import main.com.debts.management.connection.DataBaseConnection;

public class DebtDataService extends AbstractDataService<Debt> {

	private static Connection conn = DataBaseConnection.getCon();
	private static final String TABLE_NAME = "debt";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

	public DebtDataService() {
		super(DebtDataService.class.getSimpleName(), TABLE_NAME, conn);
	}

	@Override
	public void create(Debt debt, User loggedUser) {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("fromUsername");
		columnNames.add("toUsername");
		columnNames.add("value");
		columnNames.add("description");
		columnNames.add("date");

		List<String> columnValues = new ArrayList<String>();
		columnValues.add(debt.getFromUsername());
		columnValues.add(debt.getToUsername());
		columnValues.add(debt.getValue().toString());
		columnValues.add(debt.getDescription());
		columnValues.add(DATE_FORMAT.format(debt.getDate()));

		create(debt, conn, columnNames, columnValues, loggedUser);
	}

	@Override
	protected Debt retrieveFrom(ResultSet rs) throws SQLException {
		Debt debt = new Debt();
		debt.setDescription(rs.getString("description"));
		debt.setFromUsername(rs.getString("fromUserName"));
		debt.setToUsername(rs.getString("toUserName"));
		debt.setValue(new BigDecimal(rs.getDouble("value")).setScale(2,
				RoundingMode.HALF_EVEN));
		debt.setId(Long.parseLong(rs.getString("id")));
		debt.setDate(rs.getDate("date"));
		return debt;
	}

	@Override
	public void update(Debt oldObject, Debt newObject, User loggedUser) {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("fromUsername");
		columnNames.add("toUsername");
		columnNames.add("value");
		columnNames.add("description");

		List<String> columnValues = new ArrayList<String>();
		columnValues.add(newObject.getFromUsername());
		columnValues.add(newObject.getToUsername());
		columnValues.add(newObject.getValue().toString());
		columnValues.add(newObject.getDescription());
		
		update(oldObject, newObject, conn, columnNames, columnValues, loggedUser);
	}
	
	public void optimizeDebts(User loggedUser)
	{
		Map<Long, Debt> debts = getDebtsMap(getAll());
		
		if (debts.size() < 3)
		{
			return;
		}
		
		List<Debt> optimezedDebts = optimezeDebts(debts);
		
		for(Debt newDebt : optimezedDebts)
		{
			if (newDebt.getValue().compareTo(BigDecimal.ZERO) == 0)
			{
				delete(newDebt.getId(), loggedUser);
			}
			else
			{
				Debt oldDebt = debts.get(newDebt.getId());
				if (oldDebt.getValue().compareTo(newDebt.getValue()) != 0)
				{
					update(oldDebt, newDebt, loggedUser);
				}
				
			}
		}
	}
	
	private Map<Long, Debt> getDebtsMap(List<Debt> debts)
	{
		Map<Long, Debt> debtsMap = new HashMap<Long, Debt>();
		for (Debt debt : debts)
		{
			debtsMap.put(debt.getId(), debt);
		}
		return debtsMap;
	}
	private List<Debt> optimezeDebts(Map<Long, Debt> debts)
	{
		List<Debt> clonedDebts = getClonedDebts(debts);
		for (int i = 0; i < clonedDebts.size() - 1 ; i++)
		{
			for (int j = i + 1 ; j < clonedDebts.size(); j++)
			{
				Debt debtI = clonedDebts.get(i);
				Debt debtJ = clonedDebts.get(j);
				if (debtI.getFromUsername().equals(debtJ.getToUsername()) && debtI.getToUsername().equals(debtJ.getFromUsername()))
				{
					if (debtI.getValue().compareTo(BigDecimal.ZERO) == 0 
							|| debtJ.getValue().compareTo(BigDecimal.ZERO) == 0)
					{
						continue;
					}
					
					if (debtI.getValue().compareTo(debtJ.getValue()) > 0)
					{
						debtI.setValue(debtI.getValue().subtract(debtJ.getValue()));
						debtJ.setValue(BigDecimal.ZERO);
					}
					else
					{
						debtJ.setValue(debtJ.getValue().subtract(debtI.getValue()));
						debtI.setValue(BigDecimal.ZERO);
					}
				}
				if (debtI.getFromUsername().equals(debtJ.getFromUsername()) && debtI.getToUsername().equals(debtJ.getToUsername()))
				{
					debtI.setValue(debtI.getValue().add(debtJ.getValue()));
					debtJ.setValue(BigDecimal.ZERO);
				}
			}
		}
		return clonedDebts;
	}
	private List<Debt> getClonedDebts(Map<Long, Debt> debts)
	{
		List<Debt> cloned = new ArrayList<Debt>();
		for (Debt debt : debts.values())
		{
			cloned.add(debt.clone());
		}
		return cloned;
	}
}
