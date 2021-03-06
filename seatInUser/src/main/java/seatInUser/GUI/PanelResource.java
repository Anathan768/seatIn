package seatInUser.GUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.ResourceFile;
import seatInServer.JDBC.Beans.Section;

@SuppressWarnings("serial")
public class PanelResource extends JPanel {

	Resource resource;
	ResourceFile tempResFile;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel subTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel nameLabel = new JLabel();
	JLabel idLabel = new JLabel();
	JLabel subTitleLabel = new JLabel("Files:");

	// BUTTONS
	JButton backButton = new JButton("Back");
	JButton downloadButton = new JButton("Download");

	// FIELDS
	JTextArea description = new JTextArea(5, 30);

	// TABLES & LAYOUTING
	JList<ResourceFile> fileList;
	JScrollPane fileScroll;
	
	FileListCellRenderer renderer = new FileListCellRenderer();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected PanelResource(Resource newResource, Section sectionFather, Course course) {

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.resource = newResource;

		idLabel.setText(String.valueOf(resource.getId()));
		nameLabel.setText(resource.getTitle());
		nameLabel.setFont(nameLabel.getFont().deriveFont(25.0f));

		Collection<ResourceFile> tempFileList = resource.getFiles();
		fileList = new JList(tempFileList.toArray());

		fileList.setCellRenderer(renderer);
		
		fileList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				tempResFile = fileList.getSelectedValue();
			}
		});
		
		
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

			}
		});
		
	
		
		fileScroll = new JScrollPane(fileList);
		fileScroll.setPreferredSize(description.getPreferredSize());

		titlePanel.add(nameLabel);
		titlePanel.add(idLabel);

		subTitlePanel.add(subTitleLabel);

		buttonPanel.add(downloadButton);
		backPanel.add(backButton);

		this.add(titlePanel);
		this.add(subTitlePanel);
		this.add(fileScroll);
		this.add(buttonPanel);
		this.add(backPanel);

	}

	public class FileListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof ResourceFile) {
				ResourceFile resource = (ResourceFile) value;
				setText(resource.getName());

			}
			return this;
		}
	}

}
