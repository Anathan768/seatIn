package seatInAdmin.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Course;

@SuppressWarnings("serial")
public class PanelCourseAdmin extends PanelCourse {
	
	Course course;

	JButton statsButton = new JButton("Statistics");
	JButton modifyCourse = new JButton("Modify Course");

	public PanelCourseAdmin(Course givenCourse) {
		super(givenCourse);

		this.course = givenCourse;
		
		modifyPanel.add(modifyCourse);

		modifyPanel.add(statsButton);

		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelStats(course));
				frame.pack();
				frame.getContentPane().validate();

			}
		});

		modifyCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelModCourse(course));
				frame.pack();
				frame.getContentPane().validate();

			}

		});

	}

}
