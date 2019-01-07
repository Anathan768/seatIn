package seatInAdmin.GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Admin;

@SuppressWarnings("serial")
public class PanelProfileAdmin extends PanelProfile {
	
	AdminCommands commands;

	JLabel department = new JLabel("Department: ");
	JLabel depField = new JLabel();

	public PanelProfileAdmin(Admin admin) {
		super();
		
		commands = AdminCommands.getInstance();
		
		setTable(admin.getDepartment());
		title.setText("ADMINISTRAOR");
		setValues(admin.getName(), admin.getSurname(), admin.getEmail(), String.valueOf(admin.getId()));
		depField.setText(admin.getDepartment());

		
	}

	private void setTable(String dep){
		
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelModAdminProf());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		// COMPONENT: COLUMN 0, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(department, gbc);

		// COMPONENT: COLUMN 1, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(depField, gbc);

	}
	
	
	
}


