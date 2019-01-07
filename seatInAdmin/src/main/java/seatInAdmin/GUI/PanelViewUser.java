package seatInAdmin.GUI;

import java.awt.Component;
import java.awt.FlowLayout;
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

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Admin;
import seatInServer.JDBC.Beans.Lecture;
import seatInServer.JDBC.Beans.Student;
import seatInServer.JDBC.Beans.User;

@SuppressWarnings("serial")
public class PanelViewUser extends JPanel {

	Component c = this;
	AdminCommands commands;

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel insertionPanel = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// LABELS
	JLabel title = new JLabel("Select User");
	JLabel idLabel = new JLabel("User ID: ");

	// FILEDS
	JTextField idField = new JTextField(15);

	// JBUTTON
	JButton searchButton = new JButton("Search");
	JButton backButton = new JButton("Back");

	protected PanelViewUser() {
		commands = AdminCommands.getInstance();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String userId = idField.getText();
				
				if(userId.equals(""))
					return;
				
				int id = Integer.parseInt(userId);

				User userData = commands.viewUserProfileData(id);

				if (userData instanceof Lecture) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelProfileTeach((Lecture)userData));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}

				if (userData instanceof Student) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelProfileStd((Student)userData));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();

				}
				
				if (userData instanceof Admin) { 
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelProfileAdmin((Admin)userData));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}

		});

		backButton.addActionListener(new ToMenuAction(c));

		title.setFont(title.getFont().deriveFont(20.0f));

		insertionPanel.add(idLabel);
		insertionPanel.add(idField);

		buttonPanel.add(searchButton);
		buttonPanel.add(Box.createHorizontalStrut(75));
		buttonPanel.add(backButton);

		titlePanel.add(title);

		this.add(titlePanel);
		this.add(insertionPanel);
		this.add(buttonPanel);

	}

}
