package seatInAdmin.GUI;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Admin;
import seatInServer.JDBC.Beans.Lecture;

@SuppressWarnings("serial")
public class PanelModTeachProf extends JPanel {
	
	AdminCommands commands;
	Lecture teacher;

	// PANELS
	JPanel containerPanel = new JPanel(new GridBagLayout());
	JPanel titlePanel = new JPanel();
	JPanel backPanel = new JPanel(new BorderLayout());
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel title = new JLabel("TEACHER");

	JLabel name = new JLabel("Name:");
	JLabel surname = new JLabel("Surname:");
	JLabel email = new JLabel("E-Mail:");
	JLabel department = new JLabel("Department:");

	JTextField nameField = new JTextField(20);
	JTextField surnameField = new JTextField(20);
	JTextField emailField = new JTextField(20);
	JTextField departmentField = new JTextField(20);

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton modifyButton = new JButton("Modify");

	protected PanelModTeachProf(Lecture user) {
		
		this.teacher = user;
		
		commands = AdminCommands.getInstance();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
		containerPanel.add(department, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(departmentField, gbc);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Lecture tempTeacher = new Lecture(teacher.getId());
				 tempTeacher.setName(nameField.getText());
				 tempTeacher.setSurname(surnameField.getText());
				 tempTeacher.setDepartment(departmentField.getText());
				 tempTeacher.setEmail(emailField.getText());
				 commands.modifyUserData(tempTeacher);
			}

		});

		title.setFont(title.getFont().deriveFont(25.0f));
		titlePanel.add(title);

		backPanel.add(modifyButton, BorderLayout.WEST);
		backPanel.add(backButton, BorderLayout.EAST);
		backPanel.setBorder((new EmptyBorder(5, 5, 5, 5)));

		backButton.addActionListener(new ToMenuAction(this));
		this.setBorder((new EmptyBorder(5, 5, 5, 5)));

		this.add(titlePanel, BorderLayout.PAGE_START);
		this.add(containerPanel, BorderLayout.PAGE_START);
		this.add(buttonPanel);
		this.add(backPanel);

	}

	protected void setValues(Lecture user) {

		nameField.setText(user.getName());
		surnameField.setText(user.getSurname());
		emailField.setText(user.getEmail());
		departmentField.setText(user.getDepartment());

	}

}
