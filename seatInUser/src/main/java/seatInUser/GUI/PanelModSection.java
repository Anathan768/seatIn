package seatInUser.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInUser.LectureCommands;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PanelModSection extends PanelActionBase {

	Component c = this;
	LectureCommands commands;
	Section section;
	Course course;

	protected PanelModSection(Section section, Course course) {
		super();
		commands = LectureCommands.getInstance();
		titleLabel.setText("Modify Section");
		actionButton.setText("Modify");

		this.section = section;
		this.course = course;

		nameField.setText(section.getTitle());
		fromField.setText(String.valueOf(section.getActiveFrom()));
		toField.setText(String.valueOf(section.getActiveTo()));
		descriptionArea.setText(section.getDescription());
		isActiveBox.setSelected(section.isActive());

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

				tempSection.setId(section.getId());
				tempSection.setTitle(nameField.getText());
				tempSection.setActiveFrom(fromStamp);
				tempSection.setActiveTo(toStamp);
				tempSection.setDescription(descriptionArea.getText());
				tempSection.setActive(isActiveBox.isSelected());

				String result = commands.modifyCourseSection(tempSection);

				if (result.equals("ACCEPT")) {

					System.out.println(section.getId());

					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelModSection(tempSection, course));
					frame.pack();
					frame.getContentPane().validate();

					JOptionPane.showMessageDialog(null, "Update Completed");

				} else {

					JOptionPane.showMessageDialog(null, "Update Failed");

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
