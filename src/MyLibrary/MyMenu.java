
/* MyMenu class contains all Option or Buttons */

package MyLibrary;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MyMenu extends JFrame implements ActionListener {

	JLabel heading;
	JTable table;
	JPanel left, top, right, right_bottom;
	public static JButton new_book, update_book, issue_book, issued_books_only, full_database, return_book,
			delete_inventory, about, exit, delete_book;

	MyMenu() throws Exception {

		super("My Library Management System");

		setLayout(null);
		Font font = new Font("arial", Font.BOLD, 16);

		// setting left side index of main frame
		left = new JPanel();
		left.setBounds(0, 50, 250, 626);
		GridLayout g = new GridLayout();
		g.setColumns(1);
		g.setRows(11);
		g.setVgap(5);
		g.setHgap(5);
		left.setLayout(g);

		new_book = new JButton("Add New Book");
		update_book = new JButton("Update Book");
		delete_book = new JButton("Delete Book");
		issue_book = new JButton("Issue Book");
		full_database = new JButton("Show Full Database");
		return_book = new JButton("Return Book");
		issued_books_only = new JButton("Access Issued Books Only");
		about = new JButton("About");
		delete_inventory = new JButton("Delete Inventory");
		exit = new JButton("Exit");

		new_book.setFont(font);
		update_book.setFont(font);
		delete_book.setFont(font);
		issue_book.setFont(font);
		issued_books_only.setFont(font);
		full_database.setFont(font);
		return_book.setFont(font);
		about.setFont(font);
		delete_inventory.setFont(font);
		exit.setFont(font);

		left.add(new_book);
		left.add(update_book);
		left.add(delete_book);
		left.add(issue_book);
		left.add(issued_books_only);
		left.add(full_database);
		left.add(return_book);
		left.add(delete_inventory);
		left.add(about);
		left.add(exit);

		left.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));
		add(left);

		// right or working area of main frame
		right = new JPanel();
		right.setLayout(null);
		right.setBounds(250, 50, 850, 625);
		right.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));
		GridLayout g3 = new GridLayout();
		g3.setColumns(1);
		g3.setRows(1);
		right_bottom = new JPanel();
		right_bottom.setBounds(0, 0, 850, 625);
		right_bottom.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));

		right_bottom.setLayout(g3);
		right_bottom.add(new JScrollPane(new ShowFullDatabase().ShowDatabase()));
		right.add(right_bottom);

		add(right);

		// Setting top part of the main frame
		top = new JPanel();
		top.setBounds(0, 0, 1100, 50);
		top.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.yellow));

		heading = new JLabel("Library Inventory");
		heading.setFont(new Font("Arial", Font.BOLD, 32));
		top.add(heading);

		// add Actions on buttons
		new_book.addActionListener(this);
		update_book.addActionListener(this);
		delete_book.addActionListener(this);
		issue_book.addActionListener(this);
		issued_books_only.addActionListener(this);
		return_book.addActionListener(this);
		full_database.addActionListener(this);
		about.addActionListener(this);
		delete_inventory.addActionListener(this);
		exit.addActionListener(this);

		add(top);
		setResizable(false);
		validate();
		pack();
		setSize(1100, 680);
		setLocationRelativeTo(null);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {

			// Event handling to add new book button
			if (e.getSource().equals(new_book)) {
				right.setVisible(false);
				right.removeAll();
				right.add(new AddNewBook().AddNewBook());
				right.setVisible(true);
				right.revalidate();

			}

			// Event Handling for update book button
			if (e.getSource().equals(update_book)) {
				try {
					right.setVisible(false);
					right.removeAll();
					right.add(new UpdateBook().updateBook());
					right.setVisible(true);
					right.revalidate();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}

			// Event Handling for delete a book button
			if (e.getSource().equals(delete_book)) {
				try {
					right.removeAll();
					right_bottom.removeAll();
					right.add(right_bottom);
					right_bottom.add(new JScrollPane(new ShowFullDatabase().ShowDatabase()));
					right_bottom.revalidate();
					right_bottom.setVisible(true);

					// Input Dialog for entering Book Id
					String str = JOptionPane.showInputDialog(right_bottom, "Eneter Book ID to Delete!!");

					if (str == null) {
					}

					else if (str.length() > 0) {
						int sel = JOptionPane.showConfirmDialog(right_bottom, "Confirm to Delete Book !!",
								"Confirmation Box", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (sel == JOptionPane.YES_OPTION) {
							new DeleteBook().deleteBook(str);

							right.removeAll();
							right_bottom.removeAll();
							right.add(right_bottom);
							right_bottom.add(new JScrollPane(new ShowFullDatabase().ShowDatabase()));
							right_bottom.revalidate();
						}
					}

					// if user not enter any id
					else if (str != null) {
						JOptionPane.showMessageDialog(null, "Please Enter The Book ID !!!", "Error!!",
								JOptionPane.ERROR_MESSAGE);
					}
					

				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}catch(Exception e5) {
					JOptionPane.showMessageDialog(null, e5.getMessage());
				}

			}

			// Event Handling for issue a book button
			if (e.getSource().equals(issue_book)) {

				try {

					right.setVisible(false);
					right.removeAll();
					right.add(new IssueBook().issueBook());
					right.setVisible(true);
					right.revalidate();

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());				}

			}

			// Event Handling to see data of issue book table button
			if (e.getSource().equals(issued_books_only)) {

				right.setVisible(false);
				right.removeAll();
				right.add(new IssuedBooksOnly().issuedBooksOnly());
				right.setVisible(true);
				right.revalidate();
			}

			// Event Handling for Return Book button
			if (e.getSource().equals(return_book)) {
				right.setVisible(false);
				right.removeAll();
				right.add(new ReturnBook().returnBook());
				right.setVisible(true);
				right.revalidate();
			}

			// Event Handling to see full Database button
			if (e.getSource().equals(full_database)) {

				try {
					right.removeAll();
					right_bottom.removeAll();
					right_bottom.add(new JScrollPane(new ShowFullDatabase().ShowDatabase()));
					right.add(right_bottom);
					right_bottom.revalidate();
					right_bottom.setVisible(true);

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());				}
			}

			// Event Handling for About button
			if (e.getSource().equals(about)) {

				right.setVisible(false);
				right.removeAll();
				right.add(new About().about());
				right.setVisible(true);
				right.revalidate();

			}

			// Event Handling for delete inventory button
			if (e.getSource().equals(delete_inventory)) {

				int sel = JOptionPane.showConfirmDialog(right_bottom, "Confirm to Delete Full Inventory !!",
						"Confirmation Box", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (sel == JOptionPane.YES_OPTION) {
					new Login().deleteInventory();
										
					JOptionPane.showMessageDialog(null, "Delete Inventory Successfully!!");
					
					// exit from application
					System.exit(0);

				}
			}

			// Event Handling for exit button
			if (e.getSource().equals(exit)) {

				// exit from application
				System.exit(0);
			}
		} catch (Exception e5) {
			JOptionPane.showMessageDialog(null, e5.getMessage());		}
	}
}
