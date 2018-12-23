package seatInServer.GUI;

import javax.swing.JFrame;

public class GUI {

	public void initializationGUI() {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.add(new ServerMenu());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
