package main.com.debts.api;

public class User extends ManagementObject{

	private String username;
	private String password;
	private String name;
	private String surname;
	private String fullname;
	private int age;
	private boolean administrator;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString()
	{
		return fullname;
	}
	
	@Override
	public String getModelObjectDescription()
	{
		StringBuilder str = new StringBuilder(" User[id=")
			.append(id).append("]").append(" with values { ")
			.append(" username: ").append(username)
			.append(", name: ").append(name)
			.append(", surname: ").append(surname)
			.append(", fullname: ").append(fullname)
			.append(", age: ").append(age)
			.append(" }");
		return str.toString();
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}
	
	public User clone()
	{
		User newUser = new User();
		newUser.setAdministrator(administrator);
		newUser.setAge(age);
		newUser.setFullname(fullname);
		newUser.setId(id);
		newUser.setName(name);
		newUser.setPassword(password);
		newUser.setSurname(surname);
		newUser.setUsername(username);
		return newUser;
	}
}
