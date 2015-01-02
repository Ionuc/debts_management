package main.com.debts.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.com.debts.api.ManagementObject;
import main.com.debts.api.User;

public abstract class AbstractDataService<OBJECT extends ManagementObject> {

	protected final Connection conn;
	protected final String tableName;
	protected final FileDataService fileDataService;

	public AbstractDataService(String classCaller, String tableName, Connection conn) {
		this.tableName = tableName;
		this.conn = conn;
		fileDataService = new FileDataService(classCaller);
	}

	public Connection getConnection() {
		return conn;
	}

	protected String create(OBJECT object, Connection conn,
			List<String> columnNames, List<String> columnValues, User loggedUser) {
		StringBuilder fieldBuilder = new StringBuilder("Insert into `"
				+ tableName + "`");

		processInsertStringBuilder(fieldBuilder, columnNames, "`");
		fieldBuilder.append(" values ");
		processInsertStringBuilder(fieldBuilder, columnValues, "'");

		PreparedStatement stmt;
		try {
			String stmtString = fieldBuilder.toString();
			stmt = conn.prepareStatement(stmtString);
			System.out.println(stmtString);
			stmt.executeUpdate();
			fileDataService.writeCreateInfo(object, tableName, loggedUser);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fieldBuilder.toString();
	}

	protected void update(OBJECT oldObject, OBJECT newObject, Connection conn,
			List<String> columnNames, List<String> columnValues, User loggedUser) {
		StringBuilder strBuilder = new StringBuilder("Update `"
				+ tableName + "` set ");

		for (int i = 0 ; i < columnNames.size() ; i++)
		{
			String columnName = columnNames.get(i);
			String columnValue = columnValues.get(i);
			strBuilder.append("`").append(columnName).append("`='").append(columnValue).append("' ");
			if (i != columnNames.size()-1)
			{
				strBuilder.append(", ");
			}
		}
		strBuilder.append("where `id`=").append(newObject.getId()).append(";");
		

		PreparedStatement stmt;
		try {
			String stmtString = strBuilder.toString();
			stmt = conn.prepareStatement(stmtString);
			System.out.println(stmtString);
			stmt.executeUpdate();
			fileDataService.writeUpdateInfo(oldObject, newObject, tableName, loggedUser);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void delete(Connection conn, String tableName, long id) {
		PreparedStatement stmt;
		try {

			StringBuilder str = new StringBuilder("Delete from ").append(tableName)
					.append(" where id='").append(id).append("'");
			stmt = conn.prepareStatement(str.toString());
			stmt.executeUpdate();
			System.out.println(str.toString());

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	protected ResultSet get(Connection conn, String tableName,
			String columnName, String columnValue) {
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Select * from " + tableName + " where `"
					+ columnName + "` = \"" + columnValue + "\"");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;

	}

	private StringBuilder processInsertStringBuilder(StringBuilder stringBuilder,
			List<String> values, String separator) {
		stringBuilder.append("(");
		for (String value : values) {
			stringBuilder.append(separator).append(value).append(separator)
					.append(" ,");
		}
		stringBuilder.replace(stringBuilder.length() - 2,
				stringBuilder.length(), ")");
		return stringBuilder;
	}

	protected ResultSet get(Connection conn, String tableName,
			List<String> keys, List<String> values) {
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			StringBuilder strBuilder = new StringBuilder("Select * from "
					+ tableName);
			strBuilder.append(" where ");
			for (int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				String value = values.get(i);
				strBuilder.append("`" + key + "` = \"" + value + "\" and ");
			}
			strBuilder.delete(strBuilder.length() - 4, strBuilder.length());
			System.out.println(strBuilder.toString());
			rs = stmt.executeQuery(strBuilder.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public OBJECT get(long id) {
		ResultSet rs = get(conn, tableName, "id", String.valueOf(id));
		OBJECT user = null;
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

	public List<OBJECT> getAll() {

		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			StringBuilder strBuilder = new StringBuilder("Select * from "
					+ tableName);
			System.out.println(strBuilder.toString());
			rs = stmt.executeQuery(strBuilder.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<OBJECT> objects = new ArrayList<OBJECT>();
		if (rs != null) {
			try {
				while (rs.next()) {
					objects.add(retrieveFrom(rs));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objects;
	}

	public OBJECT delete(long id, User loggedUser) {
		OBJECT object = get(id);
		if (object != null) {
			delete(conn, tableName, id);
			fileDataService.writeDeleteInfo(object, tableName, loggedUser);
		}
		return object;
	}
	
	public abstract void create(OBJECT object, User loggedUser);
	
	public abstract void update(OBJECT oldObject, OBJECT newObject, User loggedUser);

	protected abstract OBJECT retrieveFrom(ResultSet rs) throws SQLException;
}
