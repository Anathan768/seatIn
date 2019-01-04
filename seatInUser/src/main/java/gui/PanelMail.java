package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelMail extends JPanel {

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel infoPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// LABELS
	JLabel reciverLabel = new JLabel("To:");
	JLabel subjectLabel = new JLabel("Subject:");
	JLabel passwordLabel = new JLabel("EMail Password:");

	// BUTTONS
	JButton sendButton = new JButton("Send");
	JButton backButton = new JButton("Back");

	// FIELDS & AREAS
	JTextField reciverField = new JTextField(25);
	JTextField subjectField = new JTextField(25);
	JTextField passwordField = new JPasswordField(15);
	JTextArea mail = new JTextArea(10, 30);

	protected PanelMail() {
		

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder((new EmptyBorder(5, 5, 5, 5)));

		backButton.addActionListener(new ToMenuAction(this));

		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				sendAction();
				
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		infoPanel.add(reciverLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		infoPanel.add(reciverField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		infoPanel.add(subjectLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		infoPanel.add(subjectField, gbc);

		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);

		buttonPanel.add(sendButton);
		buttonPanel.add(Box.createHorizontalStrut(250));
		buttonPanel.add(backButton);

		this.add(infoPanel);
		this.add(mail);
		this.add(passwordPanel);
		this.add(buttonPanel);

	}

	protected void sendAction() {
		
		System.out.println("standard");

	}

}
