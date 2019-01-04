package gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ToSection {
	Component component;
	String id;
	String name;
	String userType;

	public ToSection(Component c1, String id, String name) {
		this.component = c1;
		this.id = id;
		this.name = name;
		this.userType = GUI.s;
		
		
		if (userType.equals("TEACHER")) {

			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelSectionTeach(id, name));
			frame.pack();
			frame.getContentPane().validate();

		} else {

			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelSection(id, name));
			frame.pack();
			frame.getContentPane().validate();

		}

	}

}
