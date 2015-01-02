package main.com.debts.api.enums;

public enum UserColumns {

	ID("Id", 0),
	USERNAME("Username", 1),
	FULLNAME("Full name", 2),
	NAME("Name", 3),
	SURNAME("Surname", 4),
	AGE("Age", 5),
	DELETE_BUTTON("Delete", 6);

	private String value;
	private int columnPosition;

	private UserColumns(String value, int columnPosition) {
		this.value = value;
		this.columnPosition = columnPosition;
	}

	public static String[] getAllValues() {
		String[] all = new String[UserColumns.values().length];
		int i = 0;
		for (UserColumns enumvalue : UserColumns.values()) {
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
}
