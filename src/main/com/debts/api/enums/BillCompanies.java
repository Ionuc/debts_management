package main.com.debts.api.enums;

public enum BillCompanies {
	EON_GAS("eon-gas", "Eon Gas"),
	ELECTRICA("electrica", "Electrica"),
	ADMINISTRATIVE_PAYS("administrative-pay", "Administrative Pays");

	private String name;
	private String description;

	private BillCompanies(String name, String description) {
		this.description = description;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}