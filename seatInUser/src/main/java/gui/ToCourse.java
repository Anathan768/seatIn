package gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ToCourse {

	Component component;
	String id;
	String year;
	String name;
	String course;
	String userType;

	public ToCourse(Component c1, String id, String year, String name, String course) {
		this.component = c1;
		this.id = id;
		this.year = year;
		this.name = name;
		this.course = course;
		this.userType = GUI.s;

		if (userType.equals("TEACHER")) {
			
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelCourseTeach(id, year, name, course));
			frame.pack();
			frame.getContentPane().validate();
			
		} else {

			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelCourse(id, year, name, course));
			frame.pack();
			frame.getContentPane().validate();

		}

	}

}
