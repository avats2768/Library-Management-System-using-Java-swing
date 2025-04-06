/* Class to See full Database or Library of a user */

package MyLibrary;

import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowFullDatabase {

	public static DefaultTableModel model;
	JTable table;

	public JTable ShowDatabase() throws Exception {
		// database connectivity
		Connection con = Login.getConnection();
		// queries for operate data
		String create_table, select_data;
		
		create_table = "create table if not exists library(Book_ID varchar(10) primary key,Book_Name varchar(80) not null,"
				+ "Book_Author varchar(80), Book_Price varchar(10),Book_Status varchar(45),Student_ID varchar(20))";
		select_data = "select * from library";

		Statement stmt = con.createStatement();
		stmt.executeUpdate(create_table);
		ResultSet set = stmt.executeQuery(select_data);

		// creating table to show data 
		Object data[][] = {};
		String[] columns = { "Book ID", "Book Name", "Book Author", "Book Price", "Book Status", "Student ID" };

		model = new DefaultTableModel(data, columns);
		table = new JTable(model);

		// get data from database
		while (set.next()) {
			String id = set.getString(1);
			String name = set.getString(2);
			String author = set.getString(3);
			String price = set.getString(4);
			String status = set.getString(5);
			String s_id = set.getString(6);
			Object[] new_row = { id, name, author, price, status, s_id };
			model.addRow(new_row);
		}
		
		table.setModel(model);
		stmt.close();
		// con.close();
		return table;		
	}
}
