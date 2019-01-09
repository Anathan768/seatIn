package seatInUser.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;
import seatInUser.LectureCommands;
import seatInUser.StudentCommands;

@SuppressWarnings("serial")
public class PanelResourceTeach extends PanelResource {
	
	Component c = this;
	
	JButton deleteButton = new JButton("Delete");
	JButton addButton = new JButton("Add");
	
	LectureCommands commands;

	protected PanelResourceTeach(Resource newResource, Section sectionFather, Course course) {
		super(newResource,sectionFather, course);
		
		commands = LectureCommands.getInstance();
		
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		      String result = commands.downloadFile(tempResFile.getId(), tempResFile.getName());
		      
		      if(result.equals("ACCEPT")) {
		    	  JOptionPane.showMessageDialog(null, "Download Completed");
		    	  
		      }else {
		    	  JOptionPane.showOptionDialog(new JFrame(), "Download Failed", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
		      }
				

			}
		});
	  
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete action

			}
		});
		
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelAddResource());
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelSectionTeach(sectionFather, course));
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		

	}

}
