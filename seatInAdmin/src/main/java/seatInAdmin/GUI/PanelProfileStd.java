package seatInAdmin.GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Student;

@SuppressWarnings("serial")
public class PanelProfileStd extends PanelProfile {
	
	AdminCommands commands;
	Student student;

	//LABELS
	JLabel adr = new JLabel("Adress: ");
	JLabel adrField = new JLabel();
	JLabel reg = new JLabel("Registration: ");
	JLabel regField = new JLabel();
	JLabel stat = new JLabel("Career Status: ");
	JLabel statField = new JLabel();

	public PanelProfileStd(Student student) {
		super();
		setTable();
		this.student = student;
		commands = AdminCommands.getInstance();
		title.setText("STUDENT");
		setValues(student.getName(), student.getSurname(), student.getEmail(), String.valueOf(student.getId()));
		adrField.setText(student.getDegreeCourse());
		regField.setText(String.valueOf(student.getRegistrationYear()));
		statField.setText(String.valueOf(student.getCareerStatus()));
		

	}

	private void setTable() {
		
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelModStdProf(student));
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
		containerPanel.add(adr, gbc);

		// COMPONENT: COLUMN 1, ROW 4

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		containerPanel.add(adrField, gbc);

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

	}

}
