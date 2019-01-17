package seatInUser.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInUser.Items;
import seatInUser.LectureCommands;

@SuppressWarnings("serial")
public class PanelCourseTeach extends PanelCourse {

	Course course;

	JButton statsButton = new JButton("Statistics");
	JButton addSection = new JButton("Add");
	JButton modifySection = new JButton("Modify");
	JButton deleteSection = new JButton("Delete");

	Collection<Section> sectionList;
	LectureCommands commands;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelCourseTeach(Course course) {
		super(course);
		this.course = course;
		commands = LectureCommands.getInstance();
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

		functionPanel.add(addSection);
		functionPanel.add(modifySection);
		functionPanel.add(deleteSection);
		functionPanel.add(statsButton);

		deleteSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (s != null) {
					String result = commands.deleteCourseSection(s.getId());
					
					if (result.equals("ACCEPT")) {
						
						sectionList = commands.viewCourseContent(course.getId(), true);
						Items.setSectionsOfCourse(sectionList);
						
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
						frame.getContentPane().removeAll();
						frame.getContentPane().add(new PanelCourseTeach(course));
						frame.pack();
						frame.getContentPane().validate();

						JOptionPane.showMessageDialog(null, "Section Deleted");

					} else {

						JOptionPane.showOptionDialog(new JFrame(), "Failed!", "", JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);

					}
				}
			}
		});

		modifySection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (s != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelModSection(s, course));
					frame.pack();
					frame.getContentPane().validate();
				}
			}
		});

		addSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelAddSection(course, null));
				frame.pack();
				frame.getContentPane().validate();

			}
		});

		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelStats());
				frame.pack();
				frame.getContentPane().validate();

			}
		});

		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (s != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelSectionTeach(s, course));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}
		});

	}

}
