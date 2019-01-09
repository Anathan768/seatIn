package seatInUser.GUI;

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
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelStats extends JPanel {
	
	Component c = this;

	// PANELS
	JPanel gridPanel = new JPanel(new GridBagLayout());
	JPanel titlePanel = new JPanel();
	JPanel bodyPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel();
	JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel title = new JLabel("Course Statistics");
	JLabel usersLabel = new JLabel("Connected users: ");
	JLabel avgConnectedLabel = new JLabel("Average Connection Time: ");
	JLabel avgDownloadsTitle = new JLabel("Average Downloads: (gg/mm/yyyy)");
	JLabel fromLabel = new JLabel("From: ");
	JLabel toLabel = new JLabel("To: ");
	JLabel avgDownloadsLabel = new JLabel("Total Downloads: ");

	// FIELDS
	JTextField fromField = new JTextField(10);
	JTextField toField = new JTextField(10);

	// BUTTONS
	JButton avgButton = new JButton("Search");
	JButton backButton = new JButton("Back");

	protected PanelStats() {

		title.setFont(title.getFont().deriveFont(25.0f));
		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		title.setFont(title.getFont().deriveFont(25.0f));

		GridBagConstraints gbc = new GridBagConstraints();

		// COMPONENT: COLUMN 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		bodyPanel.add(usersLabel, gbc);
		usersLabel.setText(usersLabel.getText() + "5");

		// COMPONENT: COLUMN 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		bodyPanel.add(avgConnectedLabel, gbc);
		avgConnectedLabel.setText(avgConnectedLabel.getText() + "5");

		// COMPONENT: COLUMN 2

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		bodyPanel.add(avgDownloadsTitle, gbc);

		// -----------------------------------------------------------//

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(fromLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(fromField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(toLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(toField, gbc);


		
		
		avgDownloadsLabel.setText(avgDownloadsLabel.getText() + "0");
		
		avgDownloadsLabel.setVisible(false);
		
		
		avgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				avgDownloadsLabel.setVisible(true);
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.pack();

			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelStats());
				frame.pack();
				frame.getContentPane().validate();

			}
		});
		

		titlePanel.add(title);

		buttonPanel.add(avgButton);
		buttonPanel.add(Box.createHorizontalStrut(100));
		buttonPanel.add(backButton);
		
		totalPanel.add(Box.createHorizontalStrut(20));
		totalPanel.add(avgDownloadsLabel);
		
		this.add(titlePanel);
		this.add(bodyPanel);
		this.add(gridPanel);
		this.add(totalPanel);
		this.add(buttonPanel);

	}

}
