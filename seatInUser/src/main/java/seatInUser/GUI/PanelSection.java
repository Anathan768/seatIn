package seatInUser.GUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;
import seatInUser.Items;
import seatInUser.LectureCommands;

@SuppressWarnings("serial")
public class PanelSection extends JPanel {

	Component c = this;
	Course father;
	Resource resourceSelection;
	Section sectionSelection;
	LectureCommands commands;
	Collection<Section> sectionList;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel resButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel secButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel nameLabel = new JLabel();
	JLabel idLabel = new JLabel();
	JLabel subTitleLabel = new JLabel("SubSections:");
	JLabel subTitleLabel1 = new JLabel("Resources:");

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton downloadButton = new JButton("Download");
	JButton openResButton = new JButton("Open");
	JButton openSecButton = new JButton("Open");

	// FIELDS
	JTextArea description = new JTextArea(5, 30);

	// TABLES & LAYOUTING
	JList<Resource> resList;
	JScrollPane resScroll;
	JList<Section> secList;
	JScrollPane secScroll;
	Section section;

	SectionListCellRenderer renderer = new SectionListCellRenderer();
	ResourceListCellRenderer renderer1 = new ResourceListCellRenderer();

	@SuppressWarnings("unchecked")
	protected PanelSection(Section newSection, Course father, boolean isSection) {

		this.father = father;
		this.section = newSection;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		nameLabel.setText(section.getTitle());
		nameLabel.setFont(nameLabel.getFont().deriveFont(25.0f));
		idLabel.setText(String.valueOf(section.getId()));
		description.setEditable(false);

		commands = LectureCommands.getInstance();
		sectionList = Items.getSectionOfCourse();

		Collection<Section> sections = new LinkedList<Section>();
		for (Section c : sectionList) {
			if(isSection == true) {
				if (c.getParentId() == 0) {
					sections.add(c);
				}
			}else {
				if(newSection.getId() == c.getParentId()) {
					sections.add(c);
				}
			}
		}
		this.secList = new JList(sections.toArray());

		Collection<Resource> b = section.getResources();
		this.resList = new JList(b.toArray());

		this.secList.setCellRenderer(renderer);
		this.resList.setCellRenderer(renderer1);

		resList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				// TODO funz seconda lista
				resourceSelection = resList.getSelectedValue();

			}
		});

		secList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				// TODO funz seconda lista
				sectionSelection = secList.getSelectedValue();

			}
		});

		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO download action

			}
		});

		openResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO open action
				// new ToResource(c, resList.getSelectedValue(), resList.getSelectedValue());

			}
		});

		openSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO open action
				// new ToSection(c, secList.getSelectedValue(), secList.getSelectedValue());

			}
		});

		resScroll = new JScrollPane(resList);
		resScroll.setPreferredSize(description.getPreferredSize());

		secScroll = new JScrollPane(secList);
		secScroll.setPreferredSize(description.getPreferredSize());

		titlePanel.add(nameLabel);
		titlePanel.add(idLabel);

		subTitlePanel.add(subTitleLabel);
		subTitlePanel1.add(subTitleLabel1);

		buttonPanel.add(downloadButton);
		buttonPanel.add(openResButton);

		backPanel.add(backButton);

		this.add(titlePanel);
		if (!description.getText().isEmpty()) {
			// System.out.println(description.getText()); //Test code
			this.add(description);
		}

		if (this.section.getParentId() == 0) {
			this.add(subTitlePanel);
			this.add(secScroll);
			this.add(secButtonPanel);
			secButtonPanel.add(openSecButton);
		}
		this.add(subTitlePanel1);
		this.add(resScroll);
		this.add(resButtonPanel);
		this.add(buttonPanel);
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

	public class ResourceListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof Resource) {
				Resource resource = (Resource) value;
				setText(resource.getTitle());

			}
			return this;
		}
	}

}
