package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class PanelCourseTeach extends PanelCourse {

	JButton statsButton = new JButton("Statistics");
	JButton addSection = new JButton("Add");
	JButton modifySection = new JButton("Modify");
	JButton deleteSection = new JButton("Delete");


	public PanelCourseTeach(String id, String year, String name, String course) {
		super(id, year, name, course);
		
		functionPanel.add(addSection);
		functionPanel.add(modifySection);
		functionPanel.add(deleteSection);
		functionPanel.add(statsButton);
		
		deleteSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete action

			}
		});
		
		modifySection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelModSection());
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		
		addSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelAddSection());
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});

		
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelStats());
				frame.pack();
				frame.getContentPane().validate();

			}
		});
		

	}

}
