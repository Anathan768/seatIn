package seatInUser.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.User;
import seatInUser.Items;
import seatInUser.LectureCommands;
import seatInUser.StudentCommands;

@SuppressWarnings("serial")
public class PanelMenuTeach extends PanelMenu {

	Component c = this;
	LectureCommands commands;

	JButton globalMail = new JButton("EMail to Course");
	JButton studentMail = new JButton("EMail to Students");

	public PanelMenuTeach() {

		super();
		
		//TODO differenza inizializzazione con admin
		commands = LectureCommands.getInstance();
		// Estrazione dei dati relativi all'utente dall'oggetto "Items"
		User user = Items.getUserData();
		// Richiesta da server della lista dei corsi gestiti dall'utente
		coursesList = commands.viewCourseList(user.getId());
		// Salvataggio della lista dei corsi ricevuti dal server nell'oggetto
		Items.setUserCourses(coursesList);

		globalMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMailCourse());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		studentMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMailMulti());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});
		buttonPanel.add(globalMail);
		buttonPanel.add(studentMail);
		
		fillTable(model);
		
		
		

	}
	
	@Override
	protected void fillTable(DefaultTableModel model) {

		for (Course c : coursesList) {

			model.addRow(new Object[] { c.getId(), c.getName(), c.getActivationDate(), c.getDegreeCourse() });
		}

	}

}
