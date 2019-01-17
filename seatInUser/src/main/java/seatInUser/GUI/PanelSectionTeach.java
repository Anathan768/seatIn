package seatInUser.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInUser.Items;
import seatInUser.LectureCommands;

@SuppressWarnings("serial")
public class PanelSectionTeach extends PanelSection {

	LectureCommands commands;
	Collection<Section> sectionList;

	JButton deleteResButton = new JButton("Delete");
	JButton modifyResButton = new JButton("Modify");
	JButton addResButton = new JButton("Add");

	JButton deleteSecButton = new JButton("Delete");
	JButton modifySecButton = new JButton("Modify");
	JButton addSecButton = new JButton("Add");

	protected PanelSectionTeach(Section newSection, Course father) {
		super(newSection, father, false);

		commands = LectureCommands.getInstance();
		sectionList = Items.getSectionOfCourse();

		resButtonPanel.add(addResButton);
		resButtonPanel.add(modifyResButton);
		resButtonPanel.add(deleteResButton);

		secButtonPanel.add(addSecButton);
		secButtonPanel.add(modifySecButton);
		secButtonPanel.add(deleteSecButton);

		deleteResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resourceSelection != null) {

					String result = commands.deleteResourceFile(resourceSelection.getId());

					if (result.equals("ACCEPT")) {

						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
						frame.getContentPane().removeAll();
						frame.getContentPane().add(new PanelSectionTeach(newSection, father));
						frame.pack();
						frame.getContentPane().validate();

						JOptionPane.showMessageDialog(null, "Resource Deleted");

					} else {

						JOptionPane.showOptionDialog(new JFrame(), "Failed!", "", JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);

					}

				}

			}
		});

		modifyResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resourceSelection != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelModResource(resourceSelection, newSection, father));
					frame.pack();
					frame.getContentPane().validate();
				}
			}
		});

		addResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelAddResource(newSection, father));
					frame.pack();
					frame.getContentPane().validate();
				
			}
		});

		deleteSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sectionSelection != null) {

					String result = commands.deleteSectionResource(resourceSelection.getId());

					if (result.equals("ACCEPT")) {

						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
						frame.getContentPane().removeAll();
						frame.getContentPane().add(new PanelSectionTeach(newSection, father));
						frame.pack();
						frame.getContentPane().validate();

						JOptionPane.showMessageDialog(null, "File Deleted");

					} else {

						JOptionPane.showOptionDialog(new JFrame(), "Failed!", "", JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);

					}
				}
			}
		});

		modifySecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sectionSelection != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelModSection(sectionSelection, father));
					frame.pack();
					frame.getContentPane().validate();
				}
			}
		});

		addSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelAddSection(father, newSection));
					frame.pack();
					frame.getContentPane().validate();
				
			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelCourseTeach(father));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}
		});

		openSecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sectionSelection != null) {

					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelSectionTeach(sectionSelection, father));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}
		});

		openResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resourceSelection != null) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelResourceTeach(resourceSelection, section, father));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getContentPane().validate();
				}
			}
		});

		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String result = commands.downloadZip(resourceSelection.getId(), resourceSelection.getTitle());

				if (result.equals("ACCEPT")) {
					JOptionPane.showMessageDialog(null, "Download Completed");

				} else {
					JOptionPane.showOptionDialog(new JFrame(), "Download Failed", "", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
				}

			}
		});

	}

}
