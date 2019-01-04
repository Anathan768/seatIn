package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PanelLogin extends JPanel implements ActionListener {

	Component c = this;

	// PANELS
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel insertionsPanel = new JPanel(new GridBagLayout());
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// TEXT FIELDS
	JTextField user = new JTextField(20);
	JPasswordField password = new JPasswordField(20);

	// LABELS
	JLabel eMailLabel = new JLabel("e-mail:");
	JLabel passwordLabel = new JLabel("Password:");

	// BUTTONS
	JButton loginButton = new JButton("Login");
	JButton renewButton = new JButton("Reset Password");
	JButton backButton = new JButton("Back");

	protected PanelLogin() {

		this.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "E-Learning"));

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		insertionsPanel.add(eMailLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		insertionsPanel.add(user, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		insertionsPanel.add(passwordLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		insertionsPanel.add(password, gbc);

		buttonPanel.add(loginButton);
		buttonPanel.add(renewButton);
		backPanel.add(backButton);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getText().equals("STUDENT")) {
					GUI.s = "STUDENT";
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelMenuStd());
					frame.pack();
					frame.getContentPane().validate();

				}
				if (user.getText().equals("TEACHER")) {
					GUI.s = "TEACHER";
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelMenuTeach());
					frame.pack();
					frame.getContentPane().validate();

				}

			}
		});
		renewButton.addActionListener(this);
		backButton.addActionListener(new ToHomeAction(this));

		this.add(insertionsPanel, BorderLayout.PAGE_START);
		this.add(buttonPanel, BorderLayout.CENTER);
		this.add(backPanel, BorderLayout.PAGE_END);

	}

	public void actionPerformed(ActionEvent e) {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelRedeem());
		frame.pack();
		frame.getContentPane().validate();

	}

}
