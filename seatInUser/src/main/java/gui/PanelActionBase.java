package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelActionBase extends JPanel {

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel generalPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel();

	// LABELS
	JLabel titleLabel = new JLabel("Action Name");
	JLabel nameLabel = new JLabel("Title:");
	JLabel descriptionLabel = new JLabel("Description:");
	JLabel toLabel = new JLabel("Active from:");
	JLabel fromLabel = new JLabel("Active to:");

	// BUTTONS
	JButton actionButton = new JButton("Action");
	JButton backButton = new JButton("Back");

	// FIELDS & AREAS
	JTextArea descriptionArea = new JTextArea(10, 20);
	JTextField nameField = new JTextField(20);
	JTextField fromField = new JTextField(20);
	JTextField toField = new JTextField(20);

	// CHECKBOX
	JCheckBox isActiveBox = new JCheckBox("Active");

	protected PanelActionBase() {

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
		generalPanel.add(fromLabel, gbc);

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
		generalPanel.add(toField, gbc);
		
		
		titlePanel.add(titleLabel);
		
		buttonPanel.add(actionButton);
		buttonPanel.add(Box.createHorizontalStrut(50));
		buttonPanel.add(backButton);
		
		this.add(titlePanel, BorderLayout.PAGE_START);
		this.add(generalPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_END);


	}

}
