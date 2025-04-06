/* Class For Access Specific Details About Issued Books and 
    collected Fine
 */

package MyLibrary;

import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.*;

public class IssuedBooksOnly implements ActionListener {

	// used components
	JPanel big_panel, top, bottom;
	JButton issued_book_table, total_fine, id_details, del_fine_collection;
	JTextField fine_field, id_field;
	Connection con;

	public JPanel issuedBooksOnly() {

		Font font = new Font("arial", Font.BOLD, 14);

		// (total panel) big=top + bottom
		big_panel = new JPanel();
		big_panel.setBounds(0, 0, 848, 548);
		big_panel.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));
		big_panel.setLayout(null);
		// top panel
		top = new JPanel();
		top.setBounds(0, 0, 850, 90);
		top.setLayout(null);
		top.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));
		// bottom panel
		bottom = new JPanel();
		bottom.setBounds(0, 90, 850, 500);
		bottom.setLayout(null);
		bottom.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));

		// used buttons
		del_fine_collection = new JButton("Delete Collected Fine");
		del_fine_collection.setBounds(550, 50, 200, 30);
		del_fine_collection.setFont(font);
		issued_book_table = new JButton("Issued Books Table");
		issued_book_table.setBounds(550, 10, 200, 30);
		issued_book_table.setFont(font);
		total_fine = new JButton("Total Fine Collected");
		total_fine.setBounds(50, 10, 200, 30);
		total_fine.setFont(font);
		id_details = new JButton("Student ID Details");
		id_details.setBounds(300, 10, 200, 30);
		id_details.setFont(font);

		// used text fields
		fine_field = new JTextField();
		fine_field.setBounds(50, 50, 200, 30);
		fine_field.setFont(font);

		id_field = new JTextField();
		id_field.setBounds(300, 50, 200, 30);
		id_field.setFont(font);

		big_panel.add(bottom);
		big_panel.add(top);
		
		// bottom.add(new About().about());
		top.add(del_fine_collection);
		top.add(issued_book_table);
		top.add(total_fine);
		top.add(id_details);
		top.add(fine_field);
		top.add(id_field);

		del_fine_collection.addActionListener(this);
		issued_book_table.addActionListener(this);
		id_details.addActionListener(this);
		total_fine.addActionListener(this);

		return big_panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			// action performed if issued book button is pressed
			if (e.getSource().equals(issued_book_table)) {

				// remove all components from bottom panel
				bottom.removeAll();
				// database connection
				con = Login.getConnection();

				// create table command for issued table
				String create_table = "create table if not exists issued_books(Book_ID varchar(10) primary key,Book_Name varchar(80) not null,"
						+ "Student_ID varchar(10), Issue_Date date)";
				Statement stmt1 = con.createStatement();
				stmt1.executeUpdate(create_table);

				// creating a table
				Object data[][] = {};
				String[] columns = { "Book ID", "Book Name", "Student ID", "Issued Date" };

				DefaultTableModel model = new DefaultTableModel(data, columns);
				JTable table = new JTable(model);

				// select data command
				String select_query = "select * from issued_books";
				Statement stmt = con.createStatement();
				ResultSet set = stmt.executeQuery(select_query);
				while (set.next()) {
					// set data in a row
					Object[] new_row = { set.getString(1), set.getString(2), set.getString(3), set.getDate(4) };
					model.addRow(new_row);
				}
				table.setModel(model);

				bottom.setLayout(new GridLayout(1, 1));

				// set table in panel
				bottom.add(new JScrollPane(table));

				big_panel.setVisible(false);

				big_panel.setVisible(true);
			}

			// action performed when id details button is pressed
			if (e.getSource().equals(id_details)) {
				if (id_field.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Student ID in Bellow Field!!");
				} else {

					bottom.removeAll();

					// database connection
					con = Login.getConnection();

					// create table command for issued table
					String create_table = "create table if not exists issued_books(Book_ID varchar(10) primary key,Book_Name varchar(80) not null,"
							+ "Student_ID varchar(10), Issue_Date date)";
					Statement stmt1 = con.createStatement();
					stmt1.executeUpdate(create_table);

					// creating a table
					Object data[][] = {};
					String[] columns = { "Book ID", "Book Name", "Student ID", "Issued Date", "Fine" };

					DefaultTableModel model = new DefaultTableModel(data, columns);
					JTable table = new JTable(model);

					// select command from issued books data
					String query1 = "select * from issued_books where Student_ID=?";
					PreparedStatement pstmt = con.prepareStatement(query1);
					pstmt.setString(1, id_field.getText().toString());
					ResultSet set = pstmt.executeQuery();
					int sum = 0, tempfine;
					String str = "";
					while (set.next()) {

						// access issued date from database
						java.util.Date tempdate = new java.util.Date();
						java.util.Date date = set.getDate(4);

						// calculate fine on student
						tempfine = 0;
						tempfine += new ReturnBook().calculateFine(tempdate, date);

						// set data in a row
						Object[] new_row = { set.getString(1), set.getString(2), set.getString(3), set.getDate(4), tempfine };
						model.addRow(new_row);

						sum += tempfine;

					}

					table.setModel(model);

					bottom.setLayout(new GridLayout(1, 1));

					// set table in panel
					bottom.add(new JScrollPane(table));

					big_panel.setVisible(false);

					big_panel.setVisible(true);

					JOptionPane.showMessageDialog(null, "Total Fine on Stident is :\n" + String.valueOf(sum));

				}
			}

			// action performed when total fine button is pressed
			if (e.getSource().equals(total_fine)) {

				// remove all components from panel
				bottom.removeAll();

				// database connection
				con = Login.getConnection();

				// create query for table returned book
				String create_table = "create table if not exists returned_books(Book_ID varchar(10),Book_Name varchar(80) not null,"
						+ "Student_ID varchar(10), Issue_Date date,Returned_date date,Fine int(20))";
				Statement stmt1 = con.createStatement();
				stmt1.executeUpdate(create_table);

				// creating a jtable
				Object data[][] = {};
				String[] columns = { "Book ID", "Book Name", "Student ID", "Issued Date", "Returned Date", "Fine" };

				DefaultTableModel model = new DefaultTableModel(data, columns);
				JTable table = new JTable(model);

				// select data query from returned book table
				String query = "select * from returned_books";
				Statement stmt = con.createStatement();
				ResultSet set = stmt.executeQuery(query);

				int sum = 0;
				while (set.next()) {

					Object[] new_row = { set.getString(1), set.getString(2), set.getString(3), set.getDate(4), set.getDate(5),
							set.getInt(6) };
					model.addRow(new_row);
					sum += set.getInt(6);
				}
				fine_field.setText(String.valueOf(sum));

				table.setModel(model);

				bottom.setLayout(new GridLayout(1, 1));

				bottom.add(new JScrollPane(table));

				big_panel.setVisible(false);

				big_panel.setVisible(true);

			}

			if (e.getSource().equals(del_fine_collection)) {

				// database connection
				con = Login.getConnection();

				int sel = JOptionPane.showConfirmDialog(bottom, "Confirm to Delete Full Fine Collection !!",
						"Confirmation Box", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (sel == JOptionPane.YES_OPTION) {
					
					// create query for table returned books
					String create_table = "create table if not exists returned_books(Book_ID varchar(10),Book_Name varchar(80) not null,"
							+ "Student_ID varchar(10), Issue_Date date,Returned_date date,Fine int(20))";					

					// query to delete a table
					String delete_query = "drop table returned_books";

					Statement stmt = con.createStatement();

					stmt.executeUpdate(create_table);
					
					stmt.executeUpdate(delete_query);
					
					// remove all components from panel
					bottom.removeAll();

					JOptionPane.showMessageDialog(bottom, "Full Fine Collection Deleted Successfuly!! ");

				}

			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
