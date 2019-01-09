package seatInUser.GUI;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Course;

public class ToCourse {

	Component component;
	Course course;
	String userType;

	public ToCourse(Component c1, Course course, boolean userType) {
		this.component = c1;
		this.course = course;
		if (userType) {
			
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelCourseTeach(this.course));    //TODO link al corso teacher
			frame.pack();
			frame.getContentPane().validate();
			
		} else {

			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelCourseStudent(this.course));        //TODO link al corso student
			frame.pack();
			frame.getContentPane().validate();

		}

	}

}
