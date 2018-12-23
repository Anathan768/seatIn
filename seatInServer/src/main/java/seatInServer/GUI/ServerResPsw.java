package seatInServer.GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import seatInServer.Services.ResetPassword;

@SuppressWarnings("serial")
public class ServerResPsw extends JPanel implements ActionListener{
	// PANELS
		JPanel titlePanel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// LABELS
		JLabel title = new JLabel("RESET PASSWORD");
		JLabel email = new JLabel("E Mail: ");

		// FILEDS
		JTextField emailField = new JTextField(20);

		// JBUTTON
		JButton send = new JButton("Send");

		protected ServerResPsw() {

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			this.setBorder((new EmptyBorder(5, 5, 10, 5)));

			title.setFont(title.getFont().deriveFont(25.0f));

			send.addActionListener(this);

			panel1.add(email);
			panel1.add(emailField);

			panel2.add(send);

			titlePanel.add(title);

			this.add(titlePanel);
			this.add(panel1);
			this.add(panel2);

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String email = emailField.getText();
			ResetPassword reset = new ResetPassword();
			reset.execute(email);
			
			//Return to Menu
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new ServerMenu());
			frame.pack();
			frame.getContentPane().validate();

		}
}
