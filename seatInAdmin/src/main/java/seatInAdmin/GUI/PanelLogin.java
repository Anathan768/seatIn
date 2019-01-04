package seatInAdmin.GUI;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import seatInAdmin.AdminCommands;
import seatInAdmin.Items;
import seatInServer.JDBC.Beans.User;

@SuppressWarnings("serial")
public class PanelLogin extends JPanel implements ActionListener {

	Component c = this;
	AdminCommands commands;

	// PANELS
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel insertionsPanel = new JPanel(new GridBagLayout());

	// TEXT FIELDS
	JTextField user = new JTextField("admin2@domain.com",20);
	JPasswordField password = new JPasswordField("pass",20);

	// LABELS
	JLabel eMailLabel = new JLabel("e-mail:");
	JLabel passwordLabel = new JLabel("Password:");

	// BUTTONS
	JButton loginButton = new JButton("Login");
	JButton renewButton = new JButton("Reset Password");

	protected PanelLogin() {

		commands = AdminCommands.getInstance();

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Admin"));
		GridBagConstraints gbc = new GridBagConstraints();

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

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				char[] passwordArray = password.getPassword();
				String admin_password = String.copyValueOf(passwordArray);

				if (user.getText().equals("") || admin_password.equals("")) {
					JOptionPane.showOptionDialog(new JFrame(), "Wrong Input", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
					
					return;

				}
 
				Object result = commands.login(user.getText(), admin_password);
				if (result.equals("DENIED")) {
					JOptionPane.showOptionDialog(new JFrame(), "Wrong Input", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
					return;

				} else {			
					Items.setUserData((User)result);
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelMenu());
					frame.pack();
					frame.getContentPane().validate();
				}

			}
		});
		renewButton.addActionListener(this);

		this.add(insertionsPanel, BorderLayout.PAGE_START);
		this.add(buttonPanel, BorderLayout.CENTER);

	}

	public void actionPerformed(ActionEvent e) {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelRedeem());
		frame.pack();
		frame.getContentPane().validate();

	}

}
