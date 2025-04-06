/* ReturnBook.java Class*/

package MyLibrary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class ReturnBook implements ActionListener {

	SimpleDateFormat sdf;
	Date rdate, isdate;
	JPanel return_panel;
	JLabel b_id_label, issued_date_label, return_date_label, heading;
	JButton fine, retrn, reset;
	JTextField b_id, issued_date, return_date, show_fine;
	Connection con;
	String create_table, insert_query, select_query3, select_query1, delete_query, update_query, select_query2;

	public JPanel returnBook() throws Exception {
		// database connectivity
		con = Login.getConnection();

		// create return book table
		create_table = "create table if not exists returned_books(Book_ID int(10),Book_Name varchar(80) not null,"
				+ "Student_ID int(10), Issue_Date date,Returned_date date,Fine int(20))";

		Statement stmt = con.createStatement();

		stmt.executeUpdate(create_table);

		// create a date type variable and format it
		rdate = new Date();
		sdf = new SimpleDateFormat("yyyy-MM-dd");

		// font settings
		Font font = new Font("arial", Font.BOLD, 14);

		// create a panel
		return_panel = new JPanel();
		return_panel.setBounds(180, 65, 500, 400);
		return_panel.setLayout(null);
		return_panel.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));

		// heading of panel
		heading = new JLabel("Return a Book");
		heading.setBounds(120, 20, 250, 30);
		heading.setFont(new Font("arial", Font.BOLD, 24));

		// label used
		b_id_label = new JLabel("Book ID");
		b_id_label.setBounds(30, 80, 150, 30);
		b_id_label.setFont(font);
		issued_date_label = new JLabel("Issued Date");
		issued_date_label.setBounds(30, 140, 150, 30);
		issued_date_label.setFont(font);
		return_date_label = new JLabel("Return Date");
		return_date_label.setBounds(30, 200, 150, 30);
		return_date_label.setFont(font);

		// text fields used
		b_id = new JTextField();
		b_id.setBounds(210, 80, 150, 30);
		b_id.setFont(font);
		issued_date = new JTextField();
		issued_date.setBounds(210, 140, 150, 30);
		issued_date.setFont(font);
		return_date = new JTextField(sdf.format(rdate));
		return_date.setBounds(210, 200, 150, 30);
		return_date.setFont(font);
		show_fine = new JTextField();
		show_fine.setBounds(35, 260, 80, 30);
		show_fine.setFont(font);

		// buttons used
		fine = new JButton("Calculate Fine");
		fine.setBounds(210, 260, 150, 30);
		fine.setFont(font);
		retrn = new JButton("Return");
		retrn.setBounds(90, 320, 80, 30);
		retrn.setFont(font);
		reset = new JButton("Reset");
		reset.setBounds(210, 320, 80, 30);
		reset.setFont(font);

		return_panel.add(b_id_label);
		return_panel.add(b_id);
		return_panel.add(issued_date_label);
		return_panel.add(issued_date);
		return_panel.add(return_date_label);
		return_panel.add(return_date);
		return_panel.add(show_fine);
		return_panel.add(fine);
		return_panel.add(retrn);
		return_panel.add(reset);
		return_panel.add(heading);

		fine.addActionListener(this);
		retrn.addActionListener(this);
		reset.addActionListener(this);

		// perform action on issue date field
		issued_date.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {

				// if field is empty
				if (b_id.getText().isEmpty()) {
					issued_date.transferFocus();
					JOptionPane.showMessageDialog(null, "Please Enter Book ID !!!", "Error!!",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						// database queries to operate data
						select_query2 = "select Issue_Date from issued_books where Book_ID=?";
						select_query1 = "select Book_ID from issued_books";
						Statement stmt = con.createStatement();
						ResultSet set1 = stmt.executeQuery(select_query1);
						while (set1.next()) {
							String id = set1.getString(1);
							if (id.equals( b_id.getText().toString())){

								PreparedStatement pstmt = con.prepareStatement(select_query2);
								pstmt.setString(1, id);
								ResultSet set2 = pstmt.executeQuery();
								while (set2.next()) {
									isdate = set2.getDate(1);
								}
								issued_date.setText(sdf.format(isdate));
								pstmt.close();
								
								issued_date.transferFocus();
							}
						}
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());					}
				}
				issued_date.setEditable(false);
			}
		});

		return_date.setEditable(false);

		return return_panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			// if fine button is clicked
			if (e.getSource().equals(fine)) {
				if (issued_date.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Fill All Fields !!!", "Error!!",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// call function
					show_fine.setText(String.valueOf(calculateFine(rdate, isdate)));
				}
			}

			// if return button is clicked
			if (e.getSource().equals(retrn)) {
				if (show_fine.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Calculate the Fine!!", "Error!!!",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// database queries to operate data
					select_query3 = "select Book_Name,Student_ID from issued_books where Book_ID=?";
					PreparedStatement pstmt3 = con.prepareStatement(select_query3);
					pstmt3.setString(1, b_id.getText().toString());
					ResultSet set4 = pstmt3.executeQuery();

					insert_query = "insert into returned_books(Book_ID,Book_Name,Student_ID,Issue_Date,Returned_date,Fine) values(?,?,?,?,?,?)";
					PreparedStatement pstmt = con.prepareStatement(insert_query);
					pstmt.setString(1, b_id.getText().toString());

					while (set4.next()) {
						pstmt.setString(2, set4.getString(1));
						pstmt.setString(3, set4.getString(2));
					}

					String str = sdf.format(isdate);
					isdate = sdf.parse(str);
					java.sql.Date tempdate2 = java.sql.Date.valueOf(sdf.format(isdate));
					pstmt.setDate(4, tempdate2);
					rdate = sdf.parse(return_date.getText().toString());
					java.sql.Date tempdate1 = java.sql.Date.valueOf(sdf.format(rdate));
					pstmt.setDate(5, tempdate1);
					pstmt.setInt(6, calculateFine(rdate, isdate));
					pstmt.executeUpdate();

					//update command 
					update_query = "update library set Book_Status='Available',Student_ID=NULL where Book_ID=?";
					PreparedStatement pstmt2 = con.prepareStatement(update_query);
					pstmt2.setString(1, b_id.getText().toString());
					pstmt2.executeUpdate();

					// delete query
					delete_query = "delete from issued_books where Book_ID=?";
					PreparedStatement pstmt1 = con.prepareStatement(delete_query);
					pstmt1.setString(1, b_id.getText().toString());
					pstmt1.executeUpdate();

					// clear all fields
					b_id.setText(null);
					issued_date.setEditable(true);
					issued_date.setText(null);
					show_fine.setText(null);
					JOptionPane.showMessageDialog(null, "Book Retrned Successfully...");

				}
			}

			if (e.getSource().equals(reset)) {
				b_id.setText(null);
				issued_date.setEditable(true);
				issued_date.setText(null);
				show_fine.setText(null);
			}

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());	
			}

	}

	// function to calculate fine
	public int calculateFine(Date rdate, Date isdate) throws Exception {
		int fine;
		fine = (int) ((rdate.getTime() - isdate.getTime()) / 86400000) - 7; // 86400000 are milliseconds in a day
		if (fine <= 0)
			fine = 0;
		return fine;
	}

}
