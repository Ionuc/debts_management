package main.com.debts.api;

import java.math.BigDecimal;
import java.util.Date;

public class Debt  extends ManagementObject{

	private String toUsername;
	private String fromUsername;
	private BigDecimal value;
	private String description;
	private Date date;

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getModelObjectDescription()
	{
		StringBuilder str = new StringBuilder(" Debt[id=")
			.append(id).append("]").append(" with values {")
			.append(" fromUsername: ").append(fromUsername)
			.append(", toUsername: ").append(toUsername)
			.append(", value: ").append(value)
			.append(", description: ").append(description)
			.append(" }");
		return str.toString();
	}
	
	public Debt clone()
	{
		Debt newDebt = new Debt();
		newDebt.setDescription(description);
		newDebt.setFromUsername(fromUsername);
		newDebt.setId(id);
		newDebt.setToUsername(toUsername);
		newDebt.setValue(value);
		return newDebt;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
