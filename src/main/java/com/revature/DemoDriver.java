package com.revature;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DemoDriver {

	public DemoDriver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionUtil.getConnection()){
			try(Statement stmt = conn.createStatement()){
				try(ResultSet rs = stmt.executeQuery("SELECT * FROM \"project-0\".security_table")){
					System.out.println(rs.toString());
					while(rs.next()) {
						System.out.println(rs.getString(1));
						System.out.println(rs.getString(2));
						System.out.println(rs.getString(3));
						System.out.println(rs.getString(4));
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
