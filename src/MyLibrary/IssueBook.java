/* IssueBook.java Class*/

package MyLibrary;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date.*;


import javax.swing.*;

public class IssueBook implements ActionListener {

	//variable used
	
	LocalDate d;
	JPanel issue_panel;
	JLabel s_id_label, b_id_label, date_label,heading;
	JTextField s_id, b_id, date_field;
	JButton issue, reset;
	String create_database, use_database, create_table, insert_data, select_query1,update_query, select_query2;
	
	public  JPanel issueBook() throws Exception {

		//text formatting
		Font font = new Font("arial", Font.BOLD, 14);

		//panel for the components
		issue_panel=new JPanel();
		issue_panel.setSize(400, 400);
		issue_panel.setLayout(null);
		issue_panel.setLocation(150,100);
		issue_panel.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));

		// heading of panel
		heading=new JLabel("Issue a Book");
		heading.setBounds(70,20,150,30);
		heading.setFont(new Font("arial",Font.BOLD,22));
		
		// label used
		b_id_label = new JLabel("Enter Book ID");
		b_id_label.setBounds(70, 70, 150, 30);
		b_id_label.setFont(font);
		s_id_label = new JLabel("Enter Student ID");
		s_id_label.setBounds(70, 140, 150, 30);
		s_id_label.setFont(font);
		date_label = new JLabel("Issued Date");
		date_label.setBounds(70, 220, 150, 30);
		date_label.setFont(font);

		// textfields used
		b_id = new JTextField();
		b_id.setBounds(70, 110, 150, 30);
		b_id.setFont(font);
		s_id = new JTextField();
		s_id.setBounds(70, 180, 150, 30);
		s_id.setFont(font);

		// initiallisation of date object
		d=LocalDate.now();
		DateTimeFormatter.ofPattern("yyyy-MM-dd").format(d);
		date_field = new JTextField(d.toString());
		date_field.setBounds(70, 260, 150, 30);
		date_field.setEditable(false);

		// buttons used
		issue = new JButton("Issue");
		issue.setBounds(70, 310, 80, 30);
		issue.setFont(font);
		reset = new JButton("Reset");
		reset.setBounds(180, 310, 80, 30);
		reset.setFont(font);

		issue_panel.add(heading);
		issue_panel.add(b_id_label);
		issue_panel.add(s_id_label);
		issue_panel.add(date_label);
		issue_panel.add(b_id);
		issue_panel.add(s_id);
		issue_panel.add(date_field);
		issue_panel.add(issue);
		issue_panel.add(reset);

		issue.addActionListener(this);
		reset.addActionListener(this);
		issue_panel.setVisible(true);
		return issue_panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Event handling for issue button
		if (e.getSource().equals(issue)) {
		
			if(b_id.getText().isEmpty() || s_id.getText().isEmpty()) {
				JOptionPane.showMessageDialog(b_id,"Please Fill All The Fields" );
			}
			
			else {
			Connection con;
			try {
				// database connection
				con = Login.getConnection();

				// database queries 
				
				create_table = "create table if not exists issued_books(Book_ID varchar(10) primary key,Book_Name varchar(80) not null,"
						+ "Student_ID varchar(10), Issue_Date date)";
				
				insert_data = "insert into issued_books(Book_ID,Book_Name,Student_ID,Issue_date) values(?,?,?,?)";
				
				update_query="update library set Book_Status=?,Student_ID=? where Book_ID=?";
				
				Statement stmt = con.createStatement();
				stmt.executeUpdate(create_table);

				select_query1 = "select Book_Name,Book_Status from library where Book_ID=?";
				
				PreparedStatement pstmt2 = con.prepareStatement(select_query1);
				PreparedStatement pstmt3 = con.prepareStatement(update_query);
				pstmt2.setString(1, b_id.getText().toString());
				ResultSet set1 = pstmt2.executeQuery();// getting info from database
				
				while(set1.next()) {
					if(set1.getString(2).equals("Available")) {
						PreparedStatement pstmt1 = con.prepareStatement(insert_data);
						pstmt1.setString(1, b_id.getText().toString());
						
						pstmt1.setString(2, set1.getString(1));

						pstmt1.setString(3, s_id.getText().toString());
						// changing date format to sql date
					    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
					    java.util.Date text_as_date=sdf.parse(date_field.getText().toString());
					    java.sql.Date date1=java.sql.Date.valueOf(sdf.format(text_as_date));
						// set date through query
					    pstmt1.setDate(4,date1);
						pstmt1.executeUpdate();
						
						pstmt3.setString(3, b_id.getText().toString());
						pstmt3.setString(1,"Issued");
						pstmt3.setString(2, s_id.getText().toString());
						pstmt3.executeUpdate();
						
						pstmt1.close();

						stmt.close();

						b_id.setText(null);
						s_id.setText(null);
						JOptionPane.showInternalMessageDialog(null, "Book Issued SccessFully..");
						return;
					}
					
					else {
						JOptionPane.showMessageDialog(null, "Book is Not Available");
						b_id.setText(null);
					}
					
				}

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());			}
			}
		}
		
		// Event handling for reset button
		if (e.getSource().equals(reset)) {
			try {
				b_id.setText(null);
				s_id.setText(null);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());			}
		}
	}

}
