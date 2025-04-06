/* DeleteBook.java Class*/

package MyLibrary;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class DeleteBook {

	public void deleteBook(String id) throws Exception {

		JTable table;
		Connection con = Login.getConnection();
		String query = "delete from library where Book_ID=?";
		PreparedStatement pstmt = con.prepareStatement(query);

				pstmt.setString(1, id);
				pstmt.executeUpdate();
			
	}

}
