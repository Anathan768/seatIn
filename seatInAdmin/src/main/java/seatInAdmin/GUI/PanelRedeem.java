package seatInAdmin.GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import seatInAdmin.AdminCommands;

@SuppressWarnings("serial")
public class PanelRedeem extends JPanel {
	
	Component c = this;
	
	AdminCommands commands;

	// PANELS
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel(new BorderLayout());

	// LABELS
	JLabel email = new JLabel("E Mail: ");

	// FILEDS
	JTextField emailField = new JTextField(20);

	// JBUTTON
	JButton send = new JButton("Send");
	JButton backButton = new JButton("Back");

	protected PanelRedeem() {

		commands = AdminCommands.getInstance();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder((new EmptyBorder(5, 5, 10, 5)));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Reset Password"));
		
		panel2.setBorder((new EmptyBorder(5, 10, 5, 10)));
		

		backButton.addActionListener(new ToLoginAction(this));
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (emailField.getText().equals("")) {

					return;

				}
				
				commands.resetPassword(emailField.getText());
				
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelLogin());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		panel1.add(email);
		panel1.add(emailField);
		panel2.add(send, BorderLayout.WEST);
		panel2.add(backButton, BorderLayout.EAST);



		this.add(panel1);
		this.add(panel2);

	}

}
