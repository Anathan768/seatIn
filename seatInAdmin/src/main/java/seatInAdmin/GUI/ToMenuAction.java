package seatInAdmin.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ToMenuAction extends AbstractAction {
	Component component;
	String userType;

	public ToMenuAction(Component c1) {
		this.component = c1;
	}

	public void actionPerformed(ActionEvent e) {

		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelMenu());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.getContentPane().validate();

	}

}
