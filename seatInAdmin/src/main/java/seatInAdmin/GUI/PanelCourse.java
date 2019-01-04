package seatInAdmin.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;

@SuppressWarnings("serial")
public class PanelCourse extends JPanel {

	Component c = this;
	AdminCommands commands;
	Collection<Section> sectionList;
	String s;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel titlePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel = new JPanel();
	JPanel descriptionPanel = new JPanel();
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel listPanel = new JPanel();
	JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel modifyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel title = new JLabel();
	JLabel idLabel = new JLabel();
	JLabel title1 = new JLabel("Sections");
	JLabel actYear = new JLabel();
	JLabel subject = new JLabel();

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton openButton = new JButton("Open");

	// FIELDS & AREAS
	JTextArea description = new JTextArea(10, 30);

	// TABLES & LAYOUTING
	JList<Section> list;
	JScrollPane scroll;

	@SuppressWarnings("unchecked")
	protected PanelCourse(Course course) {

		commands = AdminCommands.getInstance();
		sectionList = commands.viewCourseContent(course.getId(), false);

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		description.setEditable(false);

		title.setText(course.getName());
		title.setFont(title.getFont().deriveFont(25.0f));
		idLabel.setText(String.valueOf(course.getId()));
		actYear.setText("Activation year: " + String.valueOf(course.getActivationDate()) + " ");
		subject.setText(course.getDegreeCourse());

		backButton.addActionListener(new ToMenuAction(this));


		list = new JList(sectionList.toArray());

		scroll = new JScrollPane(list);
		scroll.setPreferredSize(description.getPreferredSize());

		list.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
			//	s = list.getSelectedValue();
			}
		});

		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				//frame.getContentPane().add(new PanelSectionAdmin(list.getSelectedValue(), list.getSelectedValue()));
				frame.pack();
				frame.getContentPane().validate();
			}
		});

		functionPanel.add(openButton);

		listPanel.add(scroll);

		titlePanel.add(title);
		titlePanel.add(idLabel);

		subTitlePanel.add(subject);
		subTitlePanel.add(Box.createRigidArea(new Dimension(120, 0)));
		subTitlePanel.add(actYear);

		titlePanel1.add(title1);
		descriptionPanel.add(description);

		backPanel.add(backButton);

		this.add(titlePanel);
		this.add(subTitlePanel);
		this.add(descriptionPanel);
		this.add(modifyPanel);
		this.add(titlePanel1);
		this.add(listPanel);
		this.add(functionPanel);
		this.add(backPanel);

	}

}
