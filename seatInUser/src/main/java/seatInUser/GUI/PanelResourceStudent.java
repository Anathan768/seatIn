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
import seatInUser.StudentCommands;

@SuppressWarnings("serial")
public class PanelResourceStudent extends PanelResource {
	
	Component c = this;
	StudentCommands commands;
	
	JButton deleteButton = new JButton("Delete");
	JButton addButton = new JButton("Add");

	protected PanelResourceStudent(Resource newResource, Section sectionFather, Course course) {
		super(newResource,sectionFather, course);
		
		commands = StudentCommands.getInstance();
		
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
	  
		
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		      String result = commands.downloadFile(tempResFile.getId(), tempResFile.getName());
		      
				if (result.equals("ACCEPT")) {
					JOptionPane.showMessageDialog(null, "Download Completed");

				} else {
					JOptionPane.showOptionDialog(new JFrame(), "Download Failed", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
				}
				

			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelSectionStudent(sectionFather, course, true));
				frame.pack();
				frame.getContentPane().validate();
				

			}
		});
		

	}

}
