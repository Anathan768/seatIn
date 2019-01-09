package seatInAdmin.GUI;

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

import seatInAdmin.AdminCommands;
import seatInAdmin.Items;
import seatInAdmin.GUI.PanelCourse.SectionListCellRenderer;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;

@SuppressWarnings("serial")
public class PanelSection extends JPanel {

	Component c = this;
	Section section;
	Collection<Section> sectionList;
	AdminCommands commands;

	Resource resourceSelection;
	Section sectionSelection;

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
	JLabel subTitleLabel = new JLabel("Sections:");
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

	SectionListCellRenderer renderer = new SectionListCellRenderer();
	ResourceListCellRenderer renderer1 = new ResourceListCellRenderer();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected PanelSection(Section newSection) {

		this.section = newSection;

		commands = AdminCommands.getInstance();
		sectionList = Items.getSectionOfCourse();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		nameLabel.setText(section.getTitle());
		nameLabel.setFont(nameLabel.getFont().deriveFont(25.0f));
		idLabel.setText(String.valueOf(section.getId()));
		description.setEditable(false);
		description.setText(section.getDescription());

		Collection<Section> sections = new LinkedList<Section>();
		for (Section c : sectionList) {
			if (c.getParentId() == newSection.getId()) {
				sections.add(c);
			}
		}
		secList = new JList(sections.toArray());

		Collection<Resource> b = section.getResources();
		resList = new JList(b.toArray());

		secList.setCellRenderer(renderer);
		resList.setCellRenderer(renderer1);

		secList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				sectionSelection = secList.getSelectedValue();

			}
		});

		resList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				resourceSelection = resList.getSelectedValue();

			}
		});

		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				commands.downloadZip(resourceSelection.getId(), resourceSelection.getTitle());

			}
		});

		openResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resourceSelection != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelResourceAdmin(resourceSelection, section));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}
		});

		openSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sectionSelection != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelSectionAdmin(sectionSelection));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int courseID = section.getCourseId();
				Course sectionCourse = commands.viewCourseData(courseID);

				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelCourseAdmin(sectionCourse));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();

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
			this.add(description);
		}

		 if (section.getParentId() == 0) {
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
