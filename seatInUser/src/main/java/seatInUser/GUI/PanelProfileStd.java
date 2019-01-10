package seatInUser.GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

import seatInServer.JDBC.Beans.Student;

@SuppressWarnings("serial")
public class PanelProfileStd extends PanelProfile {

	//LABELS
	JLabel adr = new JLabel("Adress: ");
	JLabel adrField = new JLabel();
	JLabel reg = new JLabel("Registration: ");
	JLabel regField = new JLabel();
	JLabel stat = new JLabel("Career Status: ");
	JLabel statField = new JLabel();

	public PanelProfileStd(Student user) {
		super(user);
		String status = String.valueOf(user.getCareerStatus());
		String registration = String.valueOf(user.getRegistrationYear());
		setTable(user.getDegreeCourse(), registration, status);
		title.setText("STUDENT");
		//setValues(name, surname, email, id);
		

	}

	private void setTable(String adress, String registration, String status) {

		GridBagConstraints gbc = new GridBagConstraints();
		// COMPONENT: COLUMN 0, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(adr, gbc);

		// COMPONENT: COLUMN 1, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(adrField, gbc);
		adrField.setText(adress);

		// COMPONENT: COLUMN 0, ROW 5

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(reg, gbc);

		// COMPONENT: COLUMN 1, ROW 5

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(regField, gbc);
		regField.setText(registration);

		// COMPONENT: COLUMN 0, ROW 6

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		containerPanel.add(stat, gbc);

		// COMPONENT: COLUMN 1, ROW 6

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(statField, gbc);
		statField.setText(status);

	}

}
