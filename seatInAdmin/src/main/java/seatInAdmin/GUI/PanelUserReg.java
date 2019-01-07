package seatInAdmin.GUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import seatInAdmin.AdminCommands;

@SuppressWarnings("serial")
public class PanelUserReg extends JPanel {

	Component c = this;
	AdminCommands commands;

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel insertionPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// LABELS
	JLabel title = new JLabel("Register User to Course");
	JLabel idLabel = new JLabel("User ID: ");
	JLabel courseLabel = new JLabel("Course ID: ");

	// FILEDS
	JTextField idField = new JTextField(15);
	JTextField courseField = new JTextField(15);

	// JBUTTON
	JButton signButton = new JButton("Sign");
	JButton backButton = new JButton("Back");

	protected PanelUserReg() {

		commands = AdminCommands.getInstance();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		signButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commands.registrationAtCourse(Integer.parseInt(idField.getText()), Integer.parseInt(idField.getText()));
				
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMenu());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		title.setFont(title.getFont().deriveFont(20.0f));

		backButton.addActionListener(new ToMenuAction(c));

		GridBagConstraints gbc = new GridBagConstraints();

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		insertionPanel.add(idLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		insertionPanel.add(idField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		insertionPanel.add(courseLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		insertionPanel.add(courseField, gbc);

		titlePanel.add(title);

		buttonPanel.add(signButton);
		buttonPanel.add(Box.createHorizontalStrut(75));
		buttonPanel.add(backButton);

		this.add(titlePanel);
		this.add(insertionPanel);
		this.add(buttonPanel);

	}

}
