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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelAddCourse extends JPanel {
	
	Component c = this;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel generalPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel();

	// LABELS
	JLabel titleLabel = new JLabel("Add Course");
	JLabel nameLabel = new JLabel("Name:");
	JLabel descriptionLabel = new JLabel("Description:");
	JLabel toLabel = new JLabel("Active from:");
	JLabel courseLabel = new JLabel("Course Degree:");

	// BUTTONS
	JButton addButton = new JButton("Add");
	JButton backButton = new JButton("Back");

	// FIELDS & AREAS
	JTextArea descriptionArea = new JTextArea(10, 20);
	JTextField nameField = new JTextField(20);
	JTextField fromField = new JTextField(20);
	JTextField courseField = new JTextField(20);

	// CHECKBOX
	JCheckBox isActiveBox = new JCheckBox("Active");

	protected PanelAddCourse() {
		
		titleLabel.setFont(titleLabel.getFont().deriveFont(20.0f));

		this.setLayout(new BorderLayout());
		this.setBorder((new EmptyBorder(5, 5, 5, 5)));
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMenu());
				frame.pack();
				frame.getContentPane().validate();
			}

		});

		backButton.addActionListener(new ToMenuAction(c));
		
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
		generalPanel.add(courseLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(fromField, gbc);

		// COMPONENT: COLUMN 0, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(toLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		generalPanel.add(courseField, gbc);

		titlePanel.add(titleLabel);

		buttonPanel.add(addButton);
		buttonPanel.add(Box.createHorizontalStrut(50));
		buttonPanel.add(backButton);

		this.add(titlePanel, BorderLayout.PAGE_START);
		this.add(generalPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}

}
