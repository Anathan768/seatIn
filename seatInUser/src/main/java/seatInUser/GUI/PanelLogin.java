package seatInUser.GUI;

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

import seatInUser.Items;
import seatInServer.JDBC.Beans.Lecture;
import seatInServer.JDBC.Beans.Student;
import seatInServer.JDBC.Beans.User;
import seatInUser.LectureCommands;
import seatInUser.StudentCommands;

@SuppressWarnings("serial")
public class PanelLogin extends JPanel implements ActionListener {

	Component c = this;
	StudentCommands stdCommands;
	LectureCommands teachCommands;

	// PANELS
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel insertionsPanel = new JPanel(new GridBagLayout());
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// TEXT FIELDS
	JTextField userField = new JTextField("student1@domain.com",20);
	JPasswordField passwordField = new JPasswordField("pass",20);

	// LABELS
	JLabel eMailLabel = new JLabel("e-mail:");
	JLabel passwordLabel = new JLabel("Password:");

	// BUTTONS
	JButton loginButton = new JButton("Login");
	JButton renewButton = new JButton("Reset Password");
	JButton backButton = new JButton("Back");

	protected PanelLogin() {
		
		teachCommands = LectureCommands.getInstance();
		stdCommands = StudentCommands.getInstance();

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
		insertionsPanel.add(userField, gbc);

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
		insertionsPanel.add(passwordField, gbc);

		buttonPanel.add(loginButton);
		buttonPanel.add(renewButton);
		backPanel.add(backButton);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				char[] passwordArray = passwordField.getPassword();
				String password = String.copyValueOf(passwordArray);

				if (userField.getText().equals("") || password.equals("")) {
					JOptionPane.showOptionDialog(new JFrame(), "Wrong Input", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);

					return;

				}

				Object result = teachCommands.login(userField.getText(), password);
				User user = null;
				if(result.equals("DENIED")) {
					JOptionPane.showOptionDialog(new JFrame(), "Wrong Input", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
					return;
				}else {
					user = (User) result;
					//TODO eseguire il controllo sullo stato dell'account (è attivo? Se no, eseguire l'attivazione account)
					if(user.getUserType().equals("lecturer")) {
						teachCommands.viewUserProfileData(user.getId());
						Items.setUserData((User)result);
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
						frame.getContentPane().removeAll();
						frame.getContentPane().add(new PanelMenuTeach());
						frame.pack();
						frame.setLocationRelativeTo(null);
						frame.getContentPane().validate();
					}else {
						if(user.getUserType().equals("student")) {
							stdCommands.viewUserProfileData(user.getId());
							Items.setUserData((User) result);
							JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
							frame.getContentPane().removeAll();
							frame.getContentPane().add(new PanelMenuStd());
							frame.pack();
							frame.setLocationRelativeTo(null);
							frame.getContentPane().validate();
						}
					}
				}
				return;
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
