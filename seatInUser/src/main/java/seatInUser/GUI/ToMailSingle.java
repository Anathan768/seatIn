package seatInUser.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ToMailSingle extends AbstractAction {
	 Component component;

	public ToMailSingle(Component c1) {
		this.component = c1;

	}

	public void actionPerformed(ActionEvent e) {
		JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(component);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelMailSingle());
		frame.pack();
		frame.getContentPane().validate();

	}

}
