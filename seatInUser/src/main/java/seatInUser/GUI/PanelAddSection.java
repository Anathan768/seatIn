package seatInUser.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInUser.LectureCommands;

@SuppressWarnings("serial")
public class PanelAddSection extends PanelActionBase {

	Component c = this;
	Course course;
	Section section;

	LectureCommands commands;

	protected PanelAddSection(Course course, Section section) {
		super();
		this.course = course;
		this.section = section;

		commands = LectureCommands.getInstance();

		titleLabel.setText("Add Section");
		actionButton.setText("Add");

		actionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Section tempSection = new Section();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				String fromDate = fromField.getText();
				String toDate = toField.getText();

				Timestamp fromStamp = null;
				Timestamp toStamp = null;

				try {
					fromStamp = new Timestamp(((Date) sdf.parse(fromDate)).getTime());
					toStamp = new Timestamp(((Date) sdf.parse(toDate)).getTime());
				} catch (ParseException ex) {
					ex.printStackTrace();
				}

				tempSection.setActive(isActiveBox.isSelected());
				tempSection.setActiveFrom(fromStamp);
				tempSection.setActiveTo(toStamp);
				tempSection.setCourseId(course.getId());
				tempSection.setDescription(descriptionArea.getText());
				tempSection.setTitle(nameField.getText());

				if (section != null) {
					tempSection.setParentId(section.getId());
				}


				int newID = commands.addSectionToCourse(tempSection);


				if (newID != 0) {
					
					JOptionPane.showMessageDialog(null, "Update Completed");

					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelCourseTeach(course));
					frame.pack();
					frame.getContentPane().validate();

				}

			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelCourseTeach(course));
				frame.pack();
				frame.getContentPane().validate();

			}
		});
	}
}
