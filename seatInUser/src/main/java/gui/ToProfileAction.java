package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ToProfileAction extends AbstractAction {
	 Component component;
		String userType;

	public ToProfileAction(Component c1) {
		this.component = c1;
		this.userType = GUI.s;

	}

	public void actionPerformed(ActionEvent e) {
		if (userType.equals("STUDENT")) {
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelProfileStd("a","b","c","d", "e", "f", "g"));
			frame.pack();
			frame.getContentPane().validate();
		}
		if (userType.equals("TEACHER")) {
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelProfileTeach("a","b","c","d", "e"));
			frame.pack();
			frame.getContentPane().validate();

		}
	}

}


