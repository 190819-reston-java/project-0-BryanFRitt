package com.revature;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

public class SQLCode {

	public SQLCode() {
		// TODO Auto-generated constructor stub
	}

	public static void createTransationTable(String userName) {	
		try (Connection conn = ConnectionUtil.getConnection()) {
		String query = "CREATE table IF NOT EXISTS ? (id serial PRIMARY KEY, ts TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, amount REAL)";
			try (PreparedStatement stmt = conn.prepareStatement(query))
			{
				stmt.setString(1, userName);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void addTransation(String userName, BigDecimal amount){
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String query = "INSERT INTO ? (ts, amount) VALUES (now(), ?);";
			try (PreparedStatement stmt = conn.prepareStatement(query)) 
			{
				stmt.setString(1, userName);
				stmt.setBigDecimal(2, amount);
				stmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void createSecurityTable() {
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String query = "CREATE TABLE IF NOT EXISTS security_table(\n" + 
					"	ts TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, \n" + 
					"	user_name varchar(63) primary KEY, -- UNIQUE NOT NULL, \n" + 
					"	user_password char(63) NOT NULL, \n" + 
					"	table_name varchar(63) NOT NULL \n" + 
					")";
			try (PreparedStatement stmt = conn.prepareStatement(query)) 
			{
				stmt.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addUserPassword(String UserName, String Password) {
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String query = "INSERT INTO security_table(user_name, user_password, table_name) values('?',crypt('?', gen_salt('bf',8)), '?');\n";
			try(PreparedStatement stmt = conn.prepareStatement(query))
			{
				stmt.setString(1, UserName);
				stmt.setString(2, Password);
				stmt.setString(3, UserName.toLowerCase() + "_transactions");
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	// ResultSet...
	// SELECT * FROM security_table WHERE user_name='I3' AND user_password = crypt('johnspassword2',(SELECT user_password FROM security_table WHERE user_name='I3'));
	public static boolean validPassword(String userName, String password) {
		ResultSet resultSet = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "SELECT COUNT(*) FROM security_table WHERE user_name='?' AND user_password = crypt('?',(SELECT user_password FROM security_table WHERE user_name='?'));";
			try(PreparedStatement stmt = conn.prepareStatement(query))
			{
				stmt.setString(1, userName);
				stmt.setString(2, password);
				stmt.setString(3, userName);
				resultSet = stmt.executeQuery();
				if(resultSet.next() && resultSet.getInt(1) == 1) {
					resultSet.close();
					return true;
				}
				resultSet.close();
				return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	}
	//SELECT * FROM security_table WHERE user_name='I3' AND user_password = crypt('johnspassword2',(SELECT user_password FROM security_table WHERE user_name='I3'));
	
	public static BigDecimal getBalance(String userName) {
		BigDecimal balance = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "SELECT sum(amount) FROM ?";
			try(PreparedStatement stmt = conn.prepareStatement(query))
			{
				stmt.setString(1, userName);
				ResultSet resultSet = stmt.executeQuery();
				if(resultSet.next()) {
					balance = resultSet.getBigDecimal(1);
				}
				balance = BigDecimal.valueOf(0);
				resultSet.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return balance;
	}
	
}