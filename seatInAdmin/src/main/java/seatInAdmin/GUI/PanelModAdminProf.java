package seatInAdmin.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelModAdminProf extends JPanel {

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
	JLabel id = new JLabel("ID:");
	JLabel department = new JLabel("Department:");

	JTextField nameField = new JTextField(20);
	JTextField surnameField = new JTextField(20);
	JTextField emailField = new JTextField(20);
	JTextField idField = new JTextField(20);
	JTextField departmentField = new JTextField(20);

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton modifyButton = new JButton("Modify");

	protected PanelModAdminProf() {
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
		containerPanel.add(id, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(idField, gbc);

		// COMPONENT: COLUMN 0, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(department, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(departmentField, gbc);

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

	protected void setValues(String name, String surname, String email, String id) {

		nameField.setText(name);
		surnameField.setText(surname);
		emailField.setText(email);
		idField.setText(id);

	}

}
