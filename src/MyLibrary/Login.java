/* class for Login Page and many more */

package MyLibrary;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.*;

public class Login extends JFrame implements ActionListener {

	private static Connection con;
	static String obj;
	static JTextField user;
	JPasswordField pass;
	JButton newuser, login;
	JLabel background;

	Login() throws Exception {
		super("Login Library");
		setSize(1100, 680);

		// To set the Background image
		ImageIcon icon = new ImageIcon("library_img2.png");
		background = new JLabel("", icon, JLabel.CENTER);
		background.setBounds(0, 0, 700, 500);
		this.add(background);

		// font of components
		Font font1 = new Font("Arial", Font.BOLD, 30);

		// Username Line
		JLabel username = new JLabel("Username");
		username.setFont(font1);
		username.setForeground(Color.white);
		username.setBounds(250, 250, 200, 50);
		background.add(username);

		user = new JTextField();
		user.setFont(font1);
		user.setBounds(475, 250, 300, 50);
		background.add(user);

		// password Line
		JLabel password = new JLabel("Password");
		password.setFont(font1);
		password.setForeground(Color.white);
		password.setBounds(250, 350, 200, 50);
		background.add(password);

		pass = new JPasswordField();
		pass.setFont(font1);
		pass.setBounds(475, 350, 300, 50);
		background.add(pass);

		// Login Button
		login = new JButton("Login");
		login.setFont(font1);
		login.setBackground(Color.black);
		login.setForeground(Color.white);
		login.setBounds(375, 450, 200, 50);
		background.add(login);

		// New User Button
		newuser = new JButton("New User");
		newuser.setFont(font1);
		newuser.setBackground(Color.black);
		newuser.setForeground(Color.white);
		newuser.setBounds(595, 450, 200, 50);
		background.add(newuser);

		login.addActionListener(this);
		newuser.addActionListener(this);

		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {

			Connection con = DbConnection.getConnection();

			// For new user creation

			if (e.getSource() == newuser) {

				int sel = JOptionPane.showConfirmDialog(background, "Confirm to create a new user !!",
						"Confirmation Box", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);

				// If user want to create a new user

				if (sel == JOptionPane.YES_OPTION) {

					String create = "create table if not exists authorization(username varchar(80) PRIMARY kEY, password varchar(50))";
					Statement stmt1 = con.createStatement();
					stmt1.executeUpdate(create);

					String insert = "insert into authorization(username,password) values(?,?)";
					PreparedStatement pstmt = con.prepareStatement(insert);

					pstmt.setString(1, user.getText().toString());
					pstmt.setString(2, pass.getText().toString());
					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(background, "User Created Successfully...");

				}

			}

			// User Login
			if (e.getSource() == login) {
				String select = "select password from authorization where username=?";
				// Connection con = DbConnection.getConnection();
				PreparedStatement pstmt2 = con.prepareStatement(select);
				pstmt2.setString(1, user.getText().toString());
				ResultSet set = pstmt2.executeQuery();
				String str = null;

				// check user exists or not
				while (set.next()) {
					str = set.getString(1);
				}

				if (str == null) {
					JOptionPane.showMessageDialog(null, "Either Username or Password is wrong!!", "Error!!!",
							JOptionPane.ERROR_MESSAGE);
				}
				// Authorized if exists
				else if (str.equals(pass.getText().toString())) {
					MyMenu menu = new MyMenu();
					this.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Either Username or Password is wrong!!", "Error!!!",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		} catch (SQLIntegrityConstraintViolationException e5) {

			JOptionPane.showMessageDialog(background, e5.getMessage());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(background, e1.getMessage()+"\n"+"First Create a User...");
		}
	}
	public static Connection getConnection() throws Exception {
		
		if (con == null) {
			// access driver class for mysql connection
			Class.forName("com.mysql.cj.jdbc.Driver");

			// create connection 1 for create database query without database name
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
			obj = user.getText().toString();
			// create database query
			String create_database = "create database if not exists " + obj;
			String use_database = "use " + obj;

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(create_database);
			stmt.executeUpdate(use_database);

			// create connection 2 for other queries with database name
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + obj, "root", "root");			
		}
		return con;
		}

	public int deleteInventory() throws Exception {
		
		// database connectivity with Library database
		Connection con1 = DbConnection.getConnection();

		// delete full database query
		String delete_database = "drop database " + obj;
		String query = "delete from authorization where username=?";
		PreparedStatement pstmt = con1.prepareStatement(query);

		pstmt.setString(1, obj);
		pstmt.executeUpdate();

		Statement stmt = con.createStatement();
		// query execution
		stmt.executeUpdate(delete_database);
		return 1;
	}
}
