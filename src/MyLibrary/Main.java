/* Main class contains main() function*/

package MyLibrary;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public static void main(String[] args) throws Exception {
		try {
		// set Look and Feel of Environment
		String look_and_feel = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";
		UIManager.setLookAndFeel(look_and_feel);
		// start execution from Login class
		Login login = new Login();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());

		}
	}

}
