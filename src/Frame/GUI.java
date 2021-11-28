package Frame;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


import Calender.CalenderPanel;


public class GUI extends JFrame{
	
	JPanel panel;
	
	CalenderPanel cp;
	
	public GUI() {
		
		panel = new JPanel();
		
		this.setSize(1000, 700);
		this.setTitle("Ä¶¸°´õ");
		
		panel.setLayout(new BorderLayout());
		
		
		cp = new CalenderPanel();
		panel.add(cp, BorderLayout.CENTER);
		
		
		this.add(panel);
		
		setVisible(true);
		
	}

}