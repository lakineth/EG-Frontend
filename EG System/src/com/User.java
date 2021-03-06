package com;

import java.sql.*;


public class User {
	
private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");     

			// Provide the correct details: DBServer/DBName, user_name, password
			
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid","root", "root"); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
		public String insertUser(String username, String address, String phonenum, String email, String password) 
		 { 
		    String output = ""; 
		 try
		 { 
		      Connection con = connect(); 
		      
		      if (con == null) 
		      {
		    	  return "Error while connecting to the database for inserting the user."; 
		      
		      } 
		      
		      // create a prepared statement
		      String query = " insert into user (`userid`,`username`,`address`,`phonenum`,`email`, `password`)"
		       + " values (?, ?, ?, ?, ?, ?)"; 
		      
	      	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	      	 
		     // binding values
	      	 
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, username); 
			 preparedStmt.setString(3, address); 
			 preparedStmt.setString(4, phonenum); 
			 preparedStmt.setString(5, email); 
			 preparedStmt.setString(6, password); 
			 
		 // execute the statement

			 preparedStmt.execute(); 
			 con.close(); 
			 String users = readUser();
			 output = "{\"status\":\"success\", \"data\": \"" + users + "\"}";
			 
		 } 
		   catch (Exception e) 
		   { 
			   output = "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}";
			 System.err.println(e.getMessage()); 
		   } 
		   return output; 
	   } 
		
		public String readUser() {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading the user.";
				}
				
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>UserID</th><th>UserName</th><th>Address</th>" + 
				          "<th>Phone Number</th>" +
						 "<th>Email</th>" + 
				          "<th>Password</th>"+
						 "<th>Update</th><th>Remove</th></tr>";

				String query = "select * from user";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				
				// iterate through the rows in the result set
				while (rs.next()) {
					String userID = Integer.toString(rs.getInt("userID"));
					String username = rs.getString("username");
					String address = rs.getString("address");
					String phonenum = rs.getString("phonenum");
					String email = rs.getString("email");
					String password =rs.getString("password");
					
					// Add into the html table
					output += "<tr><td>" + userID + "</td>";
					output += "<td>" + username + "</td>";
					output += "<td>" + address + "</td>";
					output += "<td>" + phonenum + "</td>";
					output += "<td>" + email + "</td>";
					output += "<td>" + password + "</td>";

					//buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-userid='" + userID + "'></td>"
							 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-userid='" + userID + "'></td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading the users.";
				System.err.println(e.getMessage());
			}
			return output;	
		}
		
		public String updateUser(int userID, String username, String address, String phonenum, String email, String password)

		{
			String output = "";
			
			
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for updating the user.";
				}
				// create a prepared statement
				String query = "UPDATE user SET username=?,address=?,phonenum=?, email=?, password=? WHERE userID=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setString(1, username);
				preparedStmt.setString(2, address);
				preparedStmt.setString(3, phonenum);
				preparedStmt.setString(4, email);
				preparedStmt.setString(5, password);
				preparedStmt.setInt(6, userID);
				// execute the statement
				 preparedStmt.execute();
				con.close();
				
				 String users = readUser();
				 output = "{\"status\":\"success\", \"data\": \"" + users + "\"}";
				
				
			} catch (Exception e) {
				
				output = "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}"; 
				System.err.println(e.getMessage());
				
			}
			return output;
		}
		
		public String deleteUser(String userID) 
		 { 
		        String output = ""; 
		 try
		 { 
			       Connection con = connect(); 
			       
			       if (con == null) 
			      {
			    	   return "Error while connecting to the database for deleting the user."; 
			      } 
			       
			     // create a prepared statement
			     String query = "delete from user where userID=?"; 
			     
			     PreparedStatement preparedStmt = con.prepareStatement(query); 
			     
			     // binding values
			     preparedStmt.setInt(1, Integer.parseInt(userID)); 
			     
			    // execute the statement
			    preparedStmt.execute(); 
			    con.close(); 
			    
			    String users = readUser();
			     output = "{\"status\":\"success\", \"data\": \"" + users + "\"}"; 
		  } 
		   catch (Exception e) 
		  { 
			   output = "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}"; 
		       System.err.println(e.getMessage()); 
		  } 
		 
	    	 return output; 
		 }
		
		public String readAUser()
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
			 return "Error while connecting to the database for reading.";
		 }
		 
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>User Name</th><th>Address</th>" +
		 "<th>Phone Number</th><th>Email</th>" +
		 "<th>Password</th>" +
		 "<th>Update</th><th>Remove</th></tr>";

		 String query = "select * from user where userID=?";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String userID = Integer.toString(rs.getInt("userID"));
		 String username = rs.getString("username");
		 String address = rs.getString("address");
		 String phonenum = rs.getString("phonenum");
		 String email = rs.getString("email");
		 String password = rs.getString("password");
		
		 // Add into the html table
		 output += "<tr><td>" + userID + "</td>";
		 output += "<td>" + username + "</td>";
		 output += "<td>" + address + "</td>";
		 output += "<td>" + phonenum + "</td>";
		 output += "<td>" + email + "</td>";
		 output += "<td>" + password + "</td>";
		
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
		 + "<td><form method='post' action='payment.jsp'>"
		 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
		 + "<input name='itemID' type='hidden' value='" + userID
		 + "'>" + "</form></td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
		 }
		 catch (Exception e)
		 {
		 output = "Error while reading the data.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 } 
		
	
		
				
}
