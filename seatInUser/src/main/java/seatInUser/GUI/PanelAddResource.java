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
public class PanelAddResource extends PanelActionBase {
	
	Component c = this;
	
	LectureCommands commands;
	Section section;
	Course course;
	
	protected PanelAddResource(Section section, Course father){
		super();
		
		this.section = section;
		this.course = father;
		
		commands = LectureCommands.getInstance();
		
		titleLabel.setText("Add Resource");
		actionButton.setText("Add");
		
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
				
				
				tempResource.setTitle(nameField.getText());
				tempResource.setActiveFrom(fromStamp);
				tempResource.setActiveTo(toStamp);
				tempResource.setDescription(descriptionArea.getText());
				tempResource.setActive(isActiveBox.isSelected());
				tempResource.setModuleId(section.getId());
				
				int newID = commands.addResourceToSection(tempResource);
				
				if(newID != 0){
					
					JOptionPane.showMessageDialog(null, "Update Completed");
					
					
					
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelCourseTeach(father));
					frame.pack();
					frame.getContentPane().validate();

				}
				
				
			}
		});
			
		
		
		
	}

}
