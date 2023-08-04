package org.ng.GenericFunctionsPack;


import java.sql.*;


public class DBFunctions {
	
	public String sqlConnect(String query, String queryType){
		String connectionString="";
		if(testing.Ecosystem.TestData.baseDomain.contains("testng1"))
			  connectionString = "jdbc:mysql://172.16.3.231:3306";
		  else if(testing.Ecosystem.TestData.baseDomain.contains("testng2"))
			  connectionString = "jdbc:mysql://172.16.3.196:3306";
		  else if(testing.Ecosystem.TestData.baseDomain.contains("testng3"))
			  connectionString = "jdbc:mysql://172.16.2.196:3306";
		  else if(testing.Ecosystem.TestData.baseDomain.contains("testng4"))
			  connectionString = "jdbc:mysql://172.16.2.234:3306";
		  else return "LIVEDB";
		

		String dbtime;
		String dbURL = connectionString;
		String username = "autoscript";
		String password = "autopass";
		String dbClass = "com.mysql.jdbc.Driver";
		String result = "";
		try{
			Class.forName(dbClass);
			Connection con = DriverManager.getConnection(dbURL, username, password);
			Statement stmt = con.createStatement();
			if (queryType.equals("select")){
				ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				dbtime = rs.getString(1);
				result =  dbtime;
			}
		}else
			stmt.executeUpdate(query);
			con.close();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = "";
		}catch(SQLException e){
			e.printStackTrace();
			result= "";
		}
	return result;
	}
	
}
