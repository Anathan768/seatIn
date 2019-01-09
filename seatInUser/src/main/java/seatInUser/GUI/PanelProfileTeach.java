package seatInUser.GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

import seatInServer.JDBC.Beans.Lecture;

@SuppressWarnings("serial")
public class PanelProfileTeach extends PanelProfile {

	JLabel department = new JLabel("Department: ");
	JLabel depField = new JLabel();

	public PanelProfileTeach(Lecture user) {
		super(user);
		setTable(user.getDepartment());
		title.setText("PROFESSOR");
		//setValues(name, surname, email, id);

		
	}

	private void setTable(String dep){
		
		
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
		depField.setText(dep);

	}
	
	
	
}


