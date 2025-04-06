/* UpdateBook.java Class */

package MyLibrary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class UpdateBook implements ActionListener {

	JButton update, reset;
	JLabel b_id, b_name, b_author, b_price, heading;
	public static JTextField b_id_field, b_name_field, b_author_field, b_price_field;
	JPanel update_book_panel;
	JTable table;

	public JPanel updateBook() throws ClassNotFoundException, SQLException {

		// font settings
		Font font = new Font("arial", Font.BOLD, 16);

		// create panel
		update_book_panel = new JPanel();
		update_book_panel.setBounds(180, 65, 500, 400);
		update_book_panel.setLayout(null);
		update_book_panel.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));

		// heading
		heading = new JLabel("Update Book Details");
		heading.setBounds(120, 20, 300, 50);
		heading.setFont(new Font("arial", Font.BOLD, 24));

		// buttons
		update = new JButton("Update");
		update.setBounds(70, 320, 100, 30);
		update.setFont(font);
		reset = new JButton("Reset");
		reset.setBounds(200, 320, 100, 30);
		reset.setFont(font);

		// labels
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

		// text fields
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

		update_book_panel.add(heading);

		update_book_panel.add(b_id);
		update_book_panel.add(b_name);
		update_book_panel.add(b_author);
		update_book_panel.add(b_price);

		update_book_panel.add(b_id_field);
		update_book_panel.add(b_name_field);
		update_book_panel.add(b_author_field);
		update_book_panel.add(b_price_field);

		update_book_panel.add(update);
		update_book_panel.add(reset);

		update.addActionListener(this);
		reset.addActionListener(this);

		return update_book_panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// press update button
		if (e.getSource().equals(update)) {

			// if any field is empty
			if (b_id_field.getText().toString().isEmpty() || b_name_field.getText().toString().isEmpty()
					|| b_author_field.getText().toString().isEmpty() || b_price_field.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please Fill All The Fields", "Error!!", JOptionPane.ERROR_MESSAGE);
			} else {

				try {
					// database connectivity
					Connection con = Login.getConnection();
					// database queries
					String select_query = "select Book_Status from library where Book_ID=?";
					String insert_query = "insert into library(Book_ID,Book_Name,Book_Author,Book_Price,Book_Status) values(?,?,?,?,'Available')";
					PreparedStatement pstmt = con.prepareStatement(insert_query);
					PreparedStatement pstmt2 = con.prepareStatement(select_query);
					pstmt2.setString(1, b_id_field.getText().toString());
					ResultSet set = pstmt2.executeQuery();
					String str = null;
					while (set.next()) {
						str = set.getString(1);
					}

					if (str == null) {
						JOptionPane.showMessageDialog(null, "Book Not Found.....", "Error!!!",
								JOptionPane.ERROR_MESSAGE);

					}

					else if (str.equals("Issued")) {
						JOptionPane.showMessageDialog(null, "First Return the Book.....", "Error!!!",
								JOptionPane.ERROR_MESSAGE);
					}

					else if (str.equals("Available")) {
						new DeleteBook().deleteBook(b_id_field.getText().toString());

						pstmt.setString(1, b_id_field.getText().toString());
						pstmt.setString(2, b_name_field.getText().toString());
						pstmt.setString(3, b_author_field.getText().toString());
						pstmt.setString(4, b_price_field.getText().toString());
						pstmt.executeUpdate();

						JOptionPane.showMessageDialog(null, "Book Updated Successfully...");

						b_id_field.setText(null);
						b_name_field.setText(null);
						b_author_field.setText(null);
						b_price_field.setText(null);
					}

					pstmt2.close();
					pstmt.close();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
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

}
