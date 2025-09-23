package MyLibrary;

import java.sql.*;
import javax.swing.JOptionPane;

public class DbConnection {

    private static Connection con;

    // function to create database connection
    public static Connection getConnection() throws ClassNotFoundException {

        if (con == null) {
            try {
                // Load MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Use environment variables for database connection
                String host = System.getenv("DATABASE_HOST");       // e.g., render's host
                String port = System.getenv("DATABASE_PORT");       // e.g., 5432 or 3306
                String dbName = System.getenv("DATABASE_NAME");     // the database name
                String user = System.getenv("DATABASE_USER");       // db user
                String password = System.getenv("DATABASE_PASSWORD"); // db password

                // Build JDBC URL
                String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

                // Connect to the database
                con = DriverManager.getConnection(url, user, password);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                e.printStackTrace();
            }
        }

        return con;
    }
}
