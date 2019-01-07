package seatInAdmin.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import seatInAdmin.AdminCommands;
import seatInServer.JDBC.Beans.Course;

@SuppressWarnings("serial")
public class PanelAvgStats extends JPanel {

	Component c = this;
	AdminCommands commands;
	HashMap<Course, Double> hashDownloads;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel tablePanel = new JPanel();
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel title = new JLabel("Average Connection Time");

	// BUTTONS
	JButton backButton = new JButton("Back");

	// TABLE & LAYOUTING
	DefaultTableModel model;
	JTable courses;
	JScrollPane coursesScroll;
	ListSelectionModel list;

	protected PanelAvgStats() {

		commands = AdminCommands.getInstance();

		hashDownloads = new HashMap<>(commands.viewAverageConnectionTimeOfStudentsForEachCourse());

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		tableInitalization();
		fillTable(model);

		title.setFont(title.getFont().deriveFont(20.0f));

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelGeneralStats());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		titlePanel.add(title);

		tablePanel.add(coursesScroll);

		backPanel.add(backButton);

		this.add(titlePanel);
		this.add(tablePanel);
		this.add(backPanel);

	}

	protected void tableInitalization() {

		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Set all cells non-editable by default
				return false;

			}

		};

		courses = new JTable(model);

		coursesScroll = new JScrollPane(courses);

		list = courses.getSelectionModel();

		model.addColumn("Course");
		model.addColumn("Average time");

		courses.setPreferredScrollableViewportSize(new Dimension(250, 150));
		courses.setFillsViewportHeight(true);

	}

	private void fillTable(DefaultTableModel model) {

		for (HashMap.Entry<Course, Double> entry : hashDownloads.entrySet()) {

			String course = entry.getKey().getName();
			Double value = entry.getValue();

			// TODO timestamp

			model.addRow(new Object[] { course, value });
		}

	}

}
