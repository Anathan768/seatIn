package seatInAdmin.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Student;

@SuppressWarnings("serial")
public class PanelModStdProf extends JPanel {
	
	Component c = this;
	
	AdminCommands commands;
	Student student;

	// PANELS
	JPanel containerPanel = new JPanel(new GridBagLayout());
	JPanel titlePanel = new JPanel();
	JPanel backPanel = new JPanel(new BorderLayout());
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel title = new JLabel("STUDENT");

	JLabel name = new JLabel("Name:");
	JLabel surname = new JLabel("Surname:");
	JLabel email = new JLabel("E-Mail:");
	JLabel adress = new JLabel("Adress:");
	JLabel registration = new JLabel("Registration:");
	JLabel career = new JLabel("Career Status:");

	JTextField nameField = new JTextField(20);
	JTextField surnameField = new JTextField(20);
	JTextField emailField = new JTextField(20);
	JTextField adressField = new JTextField(20);
	JTextField registrationField = new JTextField(20);
	JTextField careerField = new JTextField(20);

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton modifyButton = new JButton("Modify");

	protected PanelModStdProf(Student studentM) {
		
		commands = AdminCommands.getInstance();
				
		this.student = studentM;
		setValues(student);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GridBagConstraints gbc = new GridBagConstraints();

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
		containerPanel.add(adress, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(adressField, gbc);

		// COMPONENT: COLUMN 0, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(registration, gbc);

		// COMPONENT: COLUMN 1, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(registrationField, gbc);
		
		// COMPONENT: COLUMN 0, ROW 5

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(career, gbc);

		// COMPONENT: COLUMN 1, ROW 5

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(careerField, gbc);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				 student.setName(nameField.getText());
				 student.setSurname(surnameField.getText());
				 student.setEmail(emailField.getText());
				 student.setDegreeCourse(adressField.getText());
				 student.setCareerStatus('p');

				 
				 commands.modifyUserData(student);
				 
				 JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelProfileStd(student));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();

			}

		});


		title.setFont(title.getFont().deriveFont(25.0f));
		titlePanel.add(title);

		backPanel.add(modifyButton, BorderLayout.WEST);
		backPanel.add(backButton, BorderLayout.EAST);
		backPanel.setBorder((new EmptyBorder(5, 5, 5, 5)));

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelProfileStd(student));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();

			}

		});
		this.setBorder((new EmptyBorder(5, 5, 5, 5)));

		this.add(titlePanel, BorderLayout.PAGE_START);
		this.add(containerPanel, BorderLayout.PAGE_START);
		this.add(buttonPanel);
		this.add(backPanel);

	}

	protected void setValues(Student student) {

		nameField.setText(student.getName());
		surnameField.setText(student.getSurname());
		emailField.setText(student.getEmail());
		adressField.setText(student.getDegreeCourse());
		registrationField.setText(String.valueOf(student.getRegistrationYear()));
		careerField.setText(String.valueOf(student.getCareerStatus()));

	}

}
