package main.com.debts.api.enums;

public enum DebtsColumns {

	ID("Id", 0, 15),
	FROM_USERNAME("From", 1, 50),
	TO_USERNAME("To", 2, 50),
	VALUE("Value", 3, 50),
	DATE("Date", 4, 70),
	DESCRIPTION("Description", 5, 240),
	PAY("Pay", 6, 50);

	private String value;
	private int columnPosition;
	private int columnWidth;

	private DebtsColumns(String value, int columnPosition, int columnWidth) {
		this.value = value;
		this.columnPosition = columnPosition;
		this.columnWidth = columnWidth;
	}

	public static String[] getAllValues() {
		String[] all = new String[DebtsColumns.values().length];
		int i = 0;
		for (DebtsColumns enumvalue : DebtsColumns.values()) {
			all[i++] = enumvalue.value;
		}
		return all;
	}

	public String getValue() {
		return value;
	}

	public int getColumnPosition() {
		return columnPosition;
	}
	
	public int getColumnWidth()
	{
		return columnWidth;
	}
}
