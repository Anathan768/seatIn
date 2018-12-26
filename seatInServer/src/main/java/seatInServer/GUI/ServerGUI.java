package seatInServer.GUI;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ServerGUI {

	public void initializationGUI() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(new ServerConfig());
		frame.pack();
		frame.setLocationRelativeTo(null);
		

	}

}
