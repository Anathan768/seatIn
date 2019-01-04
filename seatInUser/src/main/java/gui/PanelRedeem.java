package gui;

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

@SuppressWarnings("serial")
public class PanelRedeem extends JPanel {
	
	Component c = this;

	// PANELS
	JPanel containerPanel = new JPanel();
	JPanel buttonPanel = new JPanel(new BorderLayout());

	// LABELS
	JLabel email = new JLabel("E Mail: ");

	// FILEDS
	JTextField emailField = new JTextField(20);

	// JBUTTON
	JButton sendButton = new JButton("Send");
	JButton backButton = new JButton("Back");

	protected PanelRedeem() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder((new EmptyBorder(5, 5, 10, 5)));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Reset Password"));
		
		buttonPanel.setBorder((new EmptyBorder(5, 5, 5, 5)));

		backButton.addActionListener(new ToLoginAction(this));
		
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelLogin());
				frame.pack();
				frame.getContentPane().validate();
			}

		});
		

		containerPanel.add(email);
		containerPanel.add(emailField);

		buttonPanel.add(sendButton, BorderLayout.WEST);
		buttonPanel.add(backButton, BorderLayout.EAST);

		this.add(containerPanel);
		this.add(buttonPanel);

	}

}
