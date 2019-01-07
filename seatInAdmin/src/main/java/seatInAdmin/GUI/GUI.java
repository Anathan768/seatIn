package seatInAdmin.GUI;

import javax.swing.JFrame;

public class GUI  {

	public void initalizeGUI() {
		
		JFrame frame = new JFrame();
		frame.setTitle("SeatInAdmin");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new PanelLogin());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		

	}

}
