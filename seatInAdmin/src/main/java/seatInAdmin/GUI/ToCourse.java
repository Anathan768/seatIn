package seatInAdmin.GUI;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Course;

public class ToCourse {

	Component component;
	Course course;


	public ToCourse(Component c1, Course course) {
		this.component = c1;
		this.course = course;
		

		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelCourseAdmin(course));
		frame.pack();
		frame.getContentPane().validate();

	}

}
