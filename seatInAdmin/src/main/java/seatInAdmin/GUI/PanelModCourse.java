package seatInAdmin.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Course;

@SuppressWarnings("serial")
public class PanelModCourse extends JPanel {

	Component c = this;
	AdminCommands commands;
	Course course;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel generalPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel();

	// LABELS
	JLabel titleLabel = new JLabel("Modify Course");
	JLabel nameLabel = new JLabel("Title:");
	JLabel descriptionLabel = new JLabel("Description:");
	JLabel degreeLabel = new JLabel("Course Degree:");

	// BUTTONS
	JButton actionButton = new JButton("Modify");
	JButton backButton = new JButton("Back");

	// FIELDS & AREAS
	JTextArea descriptionArea = new JTextArea(10, 20);
	JTextField nameField = new JTextField(20);
	JTextField degreeField = new JTextField(20);

	// CHECKBOX
	JCheckBox isActiveBox = new JCheckBox("Active");

	protected PanelModCourse(Course courseToMod) {

		this.course = courseToMod;
		commands = AdminCommands.getInstance();

		this.setLayout(new BorderLayout());
		this.setBorder((new EmptyBorder(5, 5, 5, 5)));

		titleLabel.setFont(titleLabel.getFont().deriveFont(20.0f));

		GridBagConstraints gbc = new GridBagConstraints();

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(nameLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(nameField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		generalPanel.add(descriptionLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(descriptionArea, gbc);

		// COMPONENT: ROW 2

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(isActiveBox, gbc);

		// COMPONENT: COLUMN 0, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(degreeLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(degreeField, gbc);

		actionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				courseToMod.setId(course.getId());
				courseToMod.setName(nameField.getText());
				courseToMod.setActive(isActiveBox.isSelected());
	
				courseToMod.setDegreeCourse(degreeField.getText());
				courseToMod.setDescription(descriptionArea.getText()); 
				String temp = commands.modifyCourseData(courseToMod);

				if (temp.equals("ACCEPT")) {
					JOptionPane.showMessageDialog(null, "Course updated!");
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelModCourse(courseToMod));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
					
				} else {
					JOptionPane.showOptionDialog(new JFrame(), "Modifie not Legal", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
				}
			}

		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelCourseAdmin(course));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		nameField.setText(course.getName());
		degreeField.setText(course.getDegreeCourse());
		isActiveBox.setSelected(course.isActive());
		descriptionArea.setText(course.getDescription());

		titlePanel.add(titleLabel);

		buttonPanel.add(actionButton);
		buttonPanel.add(Box.createHorizontalStrut(50));
		buttonPanel.add(backButton);

		this.add(titlePanel, BorderLayout.PAGE_START);
		this.add(generalPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_END);

	}

}
