package seatInUser.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;
import seatInUser.LectureCommands;

@SuppressWarnings("serial")
public class PanelAddFile extends JPanel {
	
	Component c = this;
	LectureCommands commands;
	Resource newResource;
	Section sectionFather;
	Course course;

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel(new BorderLayout());

	// LABELS
	JLabel title = new JLabel("Add File");
	JLabel pathLabel = new JLabel("File Path: ");

	// FILEDS
	JTextField pathField = new JTextField(20);

	// JBUTTON
	JButton addButton = new JButton("Add");
	JButton BackButton = new JButton("Back");

	
	

	protected PanelAddFile(Resource newResource, Section sectionFather, Course course) {

		this.newResource = newResource;
		this.sectionFather = sectionFather;
		this.course = course;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder((new EmptyBorder(5, 5, 10, 5)));
		title.setFont(title.getFont().deriveFont(25.0f));
		panel2.setBorder((new EmptyBorder(5, 20, 5, 20)));
		
		commands = LectureCommands.getInstance();


		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				commands.addFileToResource(newResource.getId(), pathField.getText());
				
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelResourceTeach(newResource, sectionFather, course));
				frame.pack();
				frame.getContentPane().validate();
				
			}
		});
		
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelResourceTeach(newResource, sectionFather, course));
				frame.pack();
				frame.getContentPane().validate();

			}
		});
		
		
		
		panel1.add(pathLabel);
		panel1.add(pathField);

		panel2.add(addButton, BorderLayout.WEST);
		panel2.add(BackButton, BorderLayout.EAST);

		titlePanel.add(title);

		this.add(titlePanel);
		this.add(panel1);
		this.add(panel2);

	}


}
