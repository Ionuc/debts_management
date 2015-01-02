package main.com.debts.api;

public abstract class ManagementObject {

	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public abstract String getModelObjectDescription();
}
