package seatInUser.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import seatInServer.JDBC.Beans.User;

@SuppressWarnings("serial")
public class PanelProfile extends JPanel {
	
	Component c;
	
	User user;

	// PANELS
	JPanel containerPanel = new JPanel();
	JPanel titlePanel = new JPanel();
	JPanel backPanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel title = new JLabel("STD/TCH/ADM");

	JLabel name = new JLabel("Name:");
	JLabel surname = new JLabel("Surname:");
	JLabel email = new JLabel("E-Mail:");
	JLabel id = new JLabel("ID:");

	JLabel nameField = new JLabel();
	JLabel surnameField = new JLabel();
	JLabel emailField = new JLabel();
	JLabel idField = new JLabel();

	// BUTTONS
	JButton backButton = new JButton("Back");

	protected PanelProfile(User user) {
		this.c = this;
		this.user = user;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		containerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		setValues(user);

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(name, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(nameField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(surname, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(surnameField, gbc);

		// COMPONENT: COLUMN 0, ROW 2

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(email, gbc);

		// COMPONENT: COLUMN 1, ROW 2

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(emailField, gbc);

		// COMPONENT: COLUMN 0, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(id, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(idField, gbc);
		
		backButton.addActionListener(new ToMenuAction(c));

		title.setFont(title.getFont().deriveFont(25.0f));
		titlePanel.add(title);


		backPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		backPanel.add(backButton);

		containerPanel.setBorder((new EmptyBorder(5, 60, 5, 80)));
		this.setBorder((new EmptyBorder(5, 5, 5, 5)));

		this.add(titlePanel, BorderLayout.PAGE_START);
		this.add(containerPanel, BorderLayout.PAGE_START);
		this.add(buttonPanel);
		this.add(backPanel);

	}

	protected void setValues(User user) {

		nameField.setText(user.getName());
		surnameField.setText(user.getSurname());
		emailField.setText(user.getEmail());
		idField.setText(String.valueOf(user.getId()));

	}

}
