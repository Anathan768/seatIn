package seatInUser.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInUser.Items;
import seatInUser.LectureCommands;
import seatInUser.StudentCommands;

@SuppressWarnings("serial")
public class PanelSectionStudent extends PanelSection {

	StudentCommands commands;
	Collection<Section> sectionList;

	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected PanelSectionStudent(Section newSection, Course father, boolean isSection) {
		super(newSection, father, isSection);

		commands = StudentCommands.getInstance();
		sectionList = Items.getSectionOfCourse();
		
		Collection<Section> sections = new LinkedList<Section>();
		for (Section c : sectionList) {
			if (c.getParentId() == newSection.getId()) {
				sections.add(c);
			}
		}
		
		secList = new JList(sections.toArray());

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelCourseStudent(father));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}
		});

		openSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sectionSelection != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelSectionStudent(sectionSelection, father, false));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}
		});

		openResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resourceSelection != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelResourceStudent(resourceSelection, section, father));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}
		});
		
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String result = commands.downloadZip(resourceSelection.getId(), resourceSelection.getTitle());
				
				if (result.equals("ACCEPT")) {
					JOptionPane.showMessageDialog(null, "Download Completed");

				} else {
					JOptionPane.showOptionDialog(new JFrame(), "Download Failed", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
				}
				
			}
		});
	}

}
