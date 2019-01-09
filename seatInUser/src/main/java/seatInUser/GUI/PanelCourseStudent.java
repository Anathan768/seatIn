package seatInUser.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInUser.Items;
import seatInUser.StudentCommands;

@SuppressWarnings("serial")
public class PanelCourseStudent extends PanelCourse {
	
	Course course;

	Collection<Section> sectionList;
	StudentCommands commands;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelCourseStudent(Course course) {
		super(course);
		this.course = course;

		commands = StudentCommands.getInstance();
		sectionList = commands.viewCourseContent(course.getId(), true);
		Items.setSectionsOfCourse(sectionList);

		Collection<Section> sections = new LinkedList<Section>();
		for (Section c : sectionList) {
			if (c.getParentId() == 0) {
				sections.add(c);
			}
		}
		list = new JList(sections.toArray());

		scroll = new JScrollPane(list);
		scroll.setPreferredSize(description.getPreferredSize());

		listPanel.add(scroll);

		list.setCellRenderer(renderer);

		list.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				s = list.getSelectedValue();
			}
		});
		
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (s != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelSectionStudent(s ,course, false));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}
		});


	}

}
