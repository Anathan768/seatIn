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
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;
import seatInUser.LectureCommands;

@SuppressWarnings("serial")
public class PanelModResource extends PanelActionBase {

	Component c = this;
	LectureCommands commands;
	Resource resource;
	Course course;
	Section fatherSection;

	protected PanelModResource(Resource resource, Section fatherSection, Course course) {
		super();
		commands = LectureCommands.getInstance();
		titleLabel.setText("Modify Resource");
		actionButton.setText("Modify");

		this.resource = resource;
		this.course = course;
		this.fatherSection = fatherSection;

		nameField.setText(resource.getTitle());
		fromField.setText(String.valueOf(resource.getActiveFrom()));
		toField.setText(String.valueOf(resource.getActiveTo()));
		descriptionArea.setText(resource.getDescription());
		isActiveBox.setSelected(resource.isActive());

		actionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Resource tempResource = new Resource();

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

				tempResource.setId(resource.getId());
				tempResource.setTitle(nameField.getText());
				tempResource.setActiveFrom(fromStamp);
				tempResource.setActiveTo(toStamp);
				tempResource.setDescription(descriptionArea.getText());
				tempResource.setActive(isActiveBox.isSelected());
				tempResource.setModuleId(fatherSection.getId());

				String resoult = commands.modifySectionResource(tempResource);

				if (resoult.equals("ACCEPT")) {

					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelCourseTeach(course));
					frame.pack();
					frame.getContentPane().validate();

					JOptionPane.showMessageDialog(null, "Update Completed");

				} else{
					
					JOptionPane.showMessageDialog(null, "Update Failed");
					
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelSectionTeach(fatherSection, course));
				frame.pack();
				frame.getContentPane().validate();

			}
		});

	}

}
