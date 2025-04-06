/* Class For Database Conecction*/

package MyLibrary;

import java.sql.*;

import javax.swing.JOptionPane;

public class DbConnection  {

	private static Connection con;
	static String create_database;
	static String use_database;
	
	// function to create database connection
	public static Connection getConnection() throws ClassNotFoundException{
		
			if(con==null) {
			// if connection is null 
				
			// access driver class for mysql connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // create connection 1 for create database query without database name
            Connection connection;
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
			
            // create database query
            create_database="create database if not exists Library" ;
    	    use_database="use Library";
    	   
    	    Statement stmt=connection.createStatement();
    		stmt.executeUpdate(create_database);
    		stmt.executeUpdate(use_database);
    		
    		// create connection 2 for other queries with database name
    		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","root");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			}
			
		// Return connection 2 as con
		return con;
	}
	
}
