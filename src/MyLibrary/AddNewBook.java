/* AddNewBook.java Class*/

package MyLibrary;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class AddNewBook implements ActionListener {

	JButton confirm, reset;
	JLabel b_id, b_name, b_author, b_price, heading;
	JTextField b_id_field, b_name_field, b_author_field, b_price_field;
	JPanel add_book_panel;

	public JPanel AddNewBook() {

		// font setting of text
		Font font = new Font("arial", Font.BOLD, 16);

		// create panel to add things
		add_book_panel = new JPanel();
		add_book_panel.setBounds(200, 65, 500, 400);
		add_book_panel.setLayout(null);
		add_book_panel.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));

		heading = new JLabel("Fill Book Details");
		heading.setBounds(150, 30, 300, 40);
		heading.setFont(new Font("arial", Font.BOLD, 24));

		// settings of buttons
		confirm = new JButton("Confirm");
		confirm.setBounds(80, 330, 100, 30);
		confirm.setFont(font);
		reset = new JButton("Reset");
		reset.setBounds(220, 330, 100, 30);
		reset.setFont(font);

		// settings of labels
		b_id = new JLabel("Book ID : ");
		b_id.setBounds(20, 90, 150, 30);
		b_id.setFont(font);
		b_name = new JLabel("Book Name : ");
		b_name.setBounds(20, 150, 150, 30);
		b_name.setFont(font);
		b_author = new JLabel("Book Author Name : ");
		b_author.setBounds(20, 210, 160, 30);
		b_author.setFont(font);
		b_price = new JLabel("Book Price : ");
		b_price.setBounds(20, 270, 150, 30);
		b_price.setFont(font);

		// settings of fields
		b_id_field = new JTextField();
		b_name_field = new JTextField();
		b_author_field = new JTextField();
		b_price_field = new JTextField();

		b_id_field.setBounds(200, 90, 250, 30);
		b_id_field.setFont(font);
		b_name_field.setBounds(200, 150, 250, 30);
		b_name_field.setFont(font);
		b_author_field.setBounds(200, 210, 250, 30);
		b_author_field.setFont(font);
		b_price_field.setBounds(200, 270, 250, 30);
		b_price_field.setFont(font);

		add_book_panel.add(heading);

		add_book_panel.add(b_id);
		add_book_panel.add(b_name);
		add_book_panel.add(b_author);
		add_book_panel.add(b_price);

		add_book_panel.add(b_id_field);
		add_book_panel.add(b_name_field);
		add_book_panel.add(b_author_field);
		add_book_panel.add(b_price_field);

		add_book_panel.add(confirm);
		add_book_panel.add(reset);

		confirm.addActionListener(this);
		reset.addActionListener(this);

		return add_book_panel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Event Handles if press confirm
		if (e.getSource() == confirm) {
			if (b_id_field.getText().isEmpty() || b_name_field.getText().isEmpty() || b_author_field.getText().isEmpty()
					|| b_price_field.getText().isEmpty()) {
				JOptionPane.showMessageDialog(add_book_panel, "Something Wrong Please Check", "Error!!",
						JOptionPane.ERROR_MESSAGE);
			}

			else {
				try {
					// database connectivity
					Connection con = Login.getConnection();
					
					String create_table = "create table if not exists library(Book_ID varchar(10) primary key,Book_Name varchar(80) not null,"
							+ "Book_Author varchar(80), Book_Price varchar(10),Book_Status varchar(45),Student_ID varchar(20))";

					Statement stmt = con.createStatement();
					stmt.executeUpdate(create_table);

					// query to insert data into table
					String query = "insert into library(Book_ID,Book_Name,Book_Author,Book_Price,Book_Status) values(?,?,?,?,?)";
					PreparedStatement pstmt = con.prepareStatement(query);
					
					
					pstmt.setString(1, b_id_field.getText().toString());
					
					pstmt.setString(2, b_name_field.getText().toString());
					pstmt.setString(3, b_author_field.getText().toString());
					pstmt.setString(4, b_price_field.getText().toString());
					pstmt.setString(5, "Available");
					pstmt.executeUpdate();

					JOptionPane.showMessageDialog(add_book_panel, "Book Added Successfully....");

					b_id_field.setText(null);
					b_name_field.setText(null);
					b_author_field.setText(null);
					b_price_field.setText(null);

					pstmt.close();
					// con.close();
				}catch(SQLIntegrityConstraintViolationException e5){
					 
					JOptionPane.showMessageDialog(null, e5.getMessage());
				}
				 catch (Exception e1) {
					 JOptionPane.showMessageDialog(null, e1.getMessage());				}
			}
		}

		if (e.getSource() == reset) {
			b_id_field.setText(null);
			b_name_field.setText(null);
			b_author_field.setText(null);
			b_price_field.setText(null);
		}

	}
}
