package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PanelMenuTeach extends PanelMenu {

	Component c = this;

	JButton globalMail = new JButton("EMail to Course");
	JButton studentMail = new JButton("EMail to Students");

	public PanelMenuTeach() {

		super();

		globalMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMailCourse());
				frame.pack();
				frame.getContentPane().validate();
			}

		});

		studentMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMailMulti());
				frame.pack();
				frame.getContentPane().validate();
			}

		});
		buttonPanel.add(globalMail);
		buttonPanel.add(studentMail);

	}

}
