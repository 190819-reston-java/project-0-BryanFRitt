package com.revature;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import javax.swing.Spring;
//import java.sql.Statement;

// NOTE: currently assuming security_table exists..., // TODO: don't assume this.

public class SQLCode {

	public SQLCode() {
		// TODO Auto-generated constructor stub
	}
	
	// TODO: maybe combine with password checker somehow?
	public static boolean usersExists(String userName) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "SELECT COUNT(*) FROM security_table WHERE user_name = ?";
			try (PreparedStatement stmt = conn.prepareStatement(query)){
				stmt.setString(1,userName);
				System.out.println("stmt.toString is " + stmt.toString());
				try(ResultSet resultSet = stmt.executeQuery()){ ;
					if(resultSet.next()) {
						int count = resultSet.getInt(1);
						resultSet.close();
						if(count == 1) {
							return true;
						}
						if(count >1 ) {
							// TODO: turn into error statement
							return true;
						} // count <= 0;
						return false;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;// error on the side of saying user exists // Don't want to create a user that exists
	}

	public static void createTransationTable(String userName) {	
		try (Connection conn = ConnectionUtil.getConnection()) {
		String query = "CREATE table IF NOT EXISTS ? (id serial PRIMARY KEY, ts TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, amount REAL)";
			try (PreparedStatement stmt = conn.prepareStatement(query))
			{
				stmt.setString(0, userName);
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
			String query = "INSERT INTO ? (ts, amount) VALUES ( now(), ? );";
			try (PreparedStatement stmt = conn.prepareStatement(query)) 
			{
				stmt.setString(0, userName);
				stmt.setBigDecimal(1, amount);
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
	
	public static boolean addUserPassword(String UserName, String Password) {
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String query = "INSERT INTO security_table(user_name, user_password, table_name) values( ? ,crypt( ? , gen_salt( 'bf',8)), ? );\n";
			try(PreparedStatement stmt = conn.prepareStatement(query))
			{
				stmt.setString(1, UserName);
				stmt.setString(2, Password);
				stmt.setString(3, UserName.toLowerCase() + "_transactions");
				stmt.execute();
				stmt.close();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	}
	// ResultSet...
	// SELECT * FROM security_table WHERE user_name='I3' AND user_password = crypt('johnspassword2',(SELECT user_password FROM security_table WHERE user_name='I3'));
	public static boolean validatePassword(String userName, String password) {
		System.out.println("Testing Password");
		ResultSet resultSet = null;
		int count;
		try(Connection conn = ConnectionUtil.getConnection()){
			String query2 = "SET search_path TO \"project-0\""; 
			String query = "SELECT COUNT(*) FROM \"project-0\".security_table WHERE user_name= ? AND user_password = crypt( ? ,(SELECT user_password FROM security_table WHERE user_name= ? ));";
			// Lest make a simple query just for testing
			//String query = "SET search_path TO \"project-0\"; SELECT * FROM \"project-0\".security_table;";
			//String query = "SELECT * FROM \"project-0\".security_table;";
			try(PreparedStatement stmt = conn.prepareStatement(query); PreparedStatement stmt2 = conn.prepareStatement(query2))
			{
				System.out.println("stmt.toString is " + stmt.toString());
				//stmt.executeQuery ( "SET CURRENT_SCHEMA=project-0" ); // Line found on the Internet
				stmt2.execute();
				stmt.setString(1, userName);
				stmt.setString(2, password);
				stmt.setString(3, userName);
				
				
				System.out.println("stmt.toString is " + stmt.toString());
				resultSet = stmt.executeQuery() ;
				if(resultSet.next()) {
					count = resultSet.getInt(1);
					resultSet.close();
					if(count == 1) {
						return true;
					}
					if(count >1 ) {
						// TODO: turn into error statement
						return false;
					} // count <= 0;
					return false;
				}
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				try {
					if(resultSet != null) {
						resultSet.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return false;
				}
				e.printStackTrace();
				return false;
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return false;
	}
	//SELECT * FROM security_table WHERE user_name='I3' AND user_password = crypt('johnspassword2',(SELECT user_password FROM security_table WHERE user_name='I3'));
	 
	public static BigDecimal getBalance(String userName) {
		// TODO: What if user doesn't currently have table?
		BigDecimal balance = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "SELECT sum(amount) FROM ? ";
			try(PreparedStatement stmt = conn.prepareStatement(query))
			{
				stmt.setString(0, "transation_table_" + userName); // text before or after name?
				try(ResultSet resultSet = stmt.executeQuery()){
					if(resultSet.next()) {
						balance = resultSet.getBigDecimal(1);
					}
					balance = BigDecimal.valueOf(0);
					resultSet.close();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return balance;
	}
	
}