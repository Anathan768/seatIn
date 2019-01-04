package seatInAdmin.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelGeneralStats extends JPanel {

	Component c = this;

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel gridPanel = new JPanel(new GridBagLayout());
	JPanel avgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel downloadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel generalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel users = new JLabel("Connected Users in a period of time: ");
	JLabel title1 = new JLabel("General Connected Users: ");
	JLabel titleLabel = new JLabel("Statistics");
	JLabel fromLabel = new JLabel("From: ");
	JLabel toLabel = new JLabel("To: ");
	JLabel avgLabel = new JLabel("Average connection time for each Course: ");
	JLabel downloadLabel = new JLabel("Downloads for each Course: ");

	// BUTTONS
	JButton searchButton = new JButton("Search");
	JButton showAvgButton = new JButton("Show");
	JButton showDownloadButton = new JButton("Show");
	JButton backButton = new JButton("Back");

	// FIELDS
	JTextField fromfield = new JTextField(20);
	JTextField tofield = new JTextField(20);

	protected PanelGeneralStats() {

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.PAGE_AXIS));

		titleLabel.setFont(titleLabel.getFont().deriveFont(20.0f));
		gridPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
				"Users connected to SeatIn"));

		generalPanel.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "General Statistics"));

		backButton.addActionListener(new ToMenuAction(c));

		GridBagConstraints gbc;

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(title1, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(users, gbc);

		// COMPONENT: COLUMN 0, ROW 2

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		gridPanel.add(fromLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 2

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(fromfield, gbc);

		// COMPONENT: COLUMN 0, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		gridPanel.add(toLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(tofield, gbc);

		// COMPONENT: COLUMN 1, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		gridPanel.add(searchButton, gbc);
		
		showAvgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelAvgStats());
				frame.pack();
				frame.getContentPane().validate();
			}

		});
		
		showDownloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelDownloadStats());
				frame.pack();
				frame.getContentPane().validate();
			}

		});

		titlePanel.add(titleLabel);

		avgPanel.add(avgLabel);
		avgPanel.add(showAvgButton);

		downloadPanel.add(downloadLabel);
		downloadPanel.add(showDownloadButton);

		generalPanel.add(avgPanel);
		generalPanel.add(downloadPanel);

		backPanel.add(backButton);

		this.add(titlePanel);
		this.add(gridPanel);
		this.add(generalPanel);
		this.add(backPanel);

	}

}
