package seatInAdmin.GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Lecture;

@SuppressWarnings("serial")
public class PanelProfileTeach extends PanelProfile {
	
	AdminCommands commands;
	Lecture lecture;

	JLabel department = new JLabel("Department: ");
	JLabel depField = new JLabel();

	public PanelProfileTeach(Lecture lecture) {
		super();
		
		this.lecture = lecture;
		
		commands = AdminCommands.getInstance();
		
		setTable();
		title.setText("PROFESSOR");
		setValues(lecture.getName(), lecture.getSurname(), lecture.getEmail(), String.valueOf(lecture.getId()));
		depField.setText(lecture.getDepartment());

		
	}

	private void setTable(){
		
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelModTeachProf(lecture));
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


