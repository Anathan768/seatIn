package seatInUser.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;

@SuppressWarnings("serial")
public class PanelCourse extends JPanel {

	Component c = this;
	Section s;
	Course course;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel titlePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel = new JPanel();
	JPanel descriptionPanel = new JPanel();
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel listPanel = new JPanel();
	JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel title = new JLabel();
	JLabel idLabel = new JLabel();
	JLabel title1 = new JLabel("Sections");
	JLabel actYear = new JLabel();
	JLabel subject = new JLabel();

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton openButton = new JButton("Open");
	// JButton subButton = new JButton("Subscribe");

	// FIELDS & AREAS
	JTextArea description = new JTextArea(10, 30);

	// TABLES & LAYOUTING
	JList<Section> list;
	JScrollPane scroll;

	SectionListCellRenderer renderer = new SectionListCellRenderer();

	protected PanelCourse(Course course) {

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.course = course;

		description.setEditable(false);

		title.setText(this.course.getName());
		title.setFont(title.getFont().deriveFont(25.0f));
		idLabel.setText(String.valueOf(this.course.getId()));
		actYear.setText("Activation year: " + this.course.getActivationDate() + " ");
		subject.setText(this.course.getDegreeCourse());

		backButton.addActionListener(new ToMenuAction(this));

		scroll = new JScrollPane(list);
		scroll.setPreferredSize(description.getPreferredSize());

		

		functionPanel.add(openButton);


		titlePanel.add(title);
		titlePanel.add(idLabel);

		subTitlePanel.add(subject);
		subTitlePanel.add(Box.createRigidArea(new Dimension(120, 0)));
		subTitlePanel.add(actYear);

		titlePanel1.add(title1);
		descriptionPanel.add(description);

		backPanel.add(backButton);
		// buttonPanel.add(subButton);

		this.add(titlePanel);
		this.add(subTitlePanel);
		this.add(descriptionPanel);
		this.add(titlePanel1);
		this.add(listPanel);
		this.add(functionPanel);
		this.add(backPanel);

	}

	public class SectionListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof Section) {
				Section section = (Section) value;
				setText(section.getTitle());

			}
			return this;
		}
	}

}
