package main.com.debts.management.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.com.debts.api.User;
import main.com.debts.management.connection.DataBaseConnection;

public class UserDataService extends AbstractDataService<User> {

	private static Connection conn = DataBaseConnection.getCon();
	private static final String TABLE_NAME = "user";

	public UserDataService() {
		super(UserDataService.class.getSimpleName(),TABLE_NAME, conn);
	}

	public User login(String username, String password) {
		User user = getUser(username);
		if (user != null && user.getPassword().equals(password))
		{
			System.out.println("true");
			return user;
		}
		System.out.println("false");
		return null;
	}

	public User getUser(String username) {
		ResultSet rs = get(conn, tableName,
				Arrays.asList("username"),
				Arrays.asList(username));
		User user = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					user = retrieveFrom(rs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	@Override
	protected User retrieveFrom(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(Long.parseLong(rs.getString("id")));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setName(rs.getString("name"));
		user.setSurname(rs.getString("surname"));
		user.setAge(Short.parseShort(rs.getString("age")));
		user.setFullname(user.getName() + " " + user.getSurname());
		user.setAdministrator(rs.getBoolean("administrator"));

		return user;
	}

	@Override
	public void create(User user, User loggedUser) {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("username");
		columnNames.add("password");
		columnNames.add("name");
		columnNames.add("surname");
		columnNames.add("age");
		columnNames.add("administrator");

		List<String> columnValues = new ArrayList<String>();
		columnValues.add(user.getUsername());
		columnValues.add(user.getPassword());
		columnValues.add(user.getName());
		columnValues.add(user.getSurname());
		columnValues.add(String.valueOf(user.getAge()));
		columnValues.add(user.isAdministrator() ? "1" : "0");

		create(user, conn, columnNames, columnValues, loggedUser);
	}

	@Override
	public void update(User oldObject, User newObject, User loggedUser) {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("username");
		columnNames.add("password");
		columnNames.add("name");
		columnNames.add("surname");
		columnNames.add("age");
		columnNames.add("administrator");

		List<String> columnValues = new ArrayList<String>();
		columnValues.add(newObject.getUsername());
		columnValues.add(newObject.getPassword());
		columnValues.add(newObject.getName());
		columnValues.add(newObject.getSurname());
		columnValues.add(String.valueOf(newObject.getAge()));
		columnValues.add(newObject.isAdministrator() ? "1" : "0");
		
		update(oldObject, newObject, conn, columnNames, columnValues, loggedUser);
		
	}

}
