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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class PanelActivateUser extends JPanel {
	
	Component c = this;
	
	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel insertionPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// LABELS
	JLabel title = new JLabel("Activate Account");
	JLabel idLabel = new JLabel("User ID: ");
	JLabel passwordLabel = new JLabel("Password: ");

	// FILEDS
	JTextField idField = new JTextField(15);
	JTextField passwordField = new JPasswordField(15);

	// JBUTTON
	JButton activateButton = new JButton("Activate");
	JButton backButton = new JButton("Back");
	
	protected PanelActivateUser(){
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		activateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMenu());
				frame.pack();
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
		insertionPanel.add(passwordLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		insertionPanel.add(passwordField, gbc);
		
		titlePanel.add(title);
		
		buttonPanel.add(activateButton);
		buttonPanel.add(Box.createHorizontalStrut(75));
		buttonPanel.add(backButton);
		
		this.add(titlePanel);
		this.add(insertionPanel);
		this.add(buttonPanel);
		
		
	}
	
	
}
