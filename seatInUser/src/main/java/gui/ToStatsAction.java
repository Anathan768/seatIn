package gui;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ToStatsAction extends AbstractAction {
	 Component component;

	public ToStatsAction(Component c1) {
		this.component = c1;

	}

	public void actionPerformed(ActionEvent e) {
		JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(component);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelMenu()); //TODO aggiornare il new
		frame.pack();
		frame.getContentPane().validate();

	}

}