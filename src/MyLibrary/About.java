/* About.java Class*/

package MyLibrary;

import java.awt.*;
import javax.swing.*;

public class About {

	// components used 
	JPanel about;
	JLabel label1,label2;
	public JPanel about() {
		
		// jpanel for showing about
		about =new JPanel();
		about.setBounds(0,0,848,600);
		about.setLayout(null);
		about.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.green));
		
		// label used
		label2=new JLabel("<html>This Library Managment System is Made By :<br>Aman Sharma</html>");
		label2.setFont(new Font("arail",Font.BOLD,32));
		label2.setForeground(Color.white);
		label2.setBounds(250,260,400,300);
			
		// add image to label
		ImageIcon icon=new ImageIcon("library_img4.jpg");
		label1 = new JLabel("", icon, JLabel.CENTER);

		label1.setBounds(0,0,848,599);
		
		label1.add(label2);
		about.add(label1);
		
		return about;		
	}
	
}
