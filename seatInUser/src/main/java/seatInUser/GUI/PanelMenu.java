package seatInUser.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.User;
import seatInUser.Items;

@SuppressWarnings("serial")
public class PanelMenu extends JPanel {

	Component c = this;
	Collection<Course> coursesList;
	
	boolean userType;
	// PANELS
	JPanel summary = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel body = new JPanel();
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel nameLabel = new JLabel("Name:");
	JLabel surnameLabel = new JLabel("Surname:");
	JLabel idLabel = new JLabel("ID:");
	JLabel nameSlot = new JLabel();
	JLabel surnameSlot = new JLabel();
	JLabel idSlot = new JLabel();

	// BUTTONS
	JButton logoutButton = new JButton("Logout");
	JButton profileButton = new JButton("Profile");
	JButton sendMail = new JButton("EMail");

	// TABLE & LAYOUTING
	DefaultTableModel model;
	JTable courses;
	JScrollPane coursesScroll;
	ListSelectionModel list;

	protected PanelMenu() {
		
		User user = Items.getUserData();

		generateBody();
		generateSummary(user.getName(), user.getSurname(), String.valueOf(user.getId()));
		generateButtonPanel();
		generateBackPanel();

		//fillTable(model); // TODO rifare metodo per mettere cosri

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder((new EmptyBorder(5, 5, 5, 5)));

		this.add(summary);
		this.add(body);
		this.add(buttonPanel);
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

		model.addColumn("ID");
		model.addColumn("Nome");
		model.addColumn("Anno");
		model.addColumn("Corso");

		ListSelectionListener asd = new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				User user = Items.getUserData();
				userType = false;
				if(user.getUserType().equals("lecturer")){
					userType = true;
				}
				if (!list.isSelectionEmpty()) {
					int selectedRow = list.getMinSelectionIndex();
					for(Course course: coursesList) {
						if(course.getId() == (int)courses.getValueAt(selectedRow, 0)) {
							new ToCourse(c, course, userType);
						}
					}
				}
			}
		};

		list.addListSelectionListener(asd);

	}

	protected void fillTable(DefaultTableModel model) { //TODO sottoclasse

		model.addRow(new Object[] { "030986", "Astrologia", "20XX", "Scienze " });
		model.addRow(new Object[] { "130986", "Astrologia", "201X", "Scienze Stupide" });
		model.addRow(new Object[] { "230986", "Astrologia", "202X", "Scienze Stupide" });
		model.addRow(new Object[] { "330986", "Astrologia", "203X", "Scienze Stupide" });
		model.addRow(new Object[] { "430986", "Astrologia", "204X", "Scienze Stupide" });

	}

	// BUILD THE SUMMARY PANEL
	protected void generateSummary(String name, String surname, String id) {

		summary.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "User"));

		nameSlot.setText(name);
		surnameSlot.setText(surname);
		idSlot.setText(id);

		summary.add(nameLabel);
		summary.add(nameSlot);
		summary.add(surnameLabel);
		summary.add(surnameSlot);
		summary.add(idLabel);
		summary.add(idSlot);

	}

	private void generateBody() {

		tableInitalization();
		courses.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		body.add(coursesScroll);

	}

	protected void generateButtonPanel() {
		profileButton.addActionListener(new ToProfileAction(c));
		buttonPanel.add(profileButton);
		sendMail.addActionListener(new ToMailSingle(this));
		buttonPanel.add(sendMail);

	}

	private void generateBackPanel() {

		logoutButton.addActionListener(new ToHomeAction(this));
		backPanel.add(logoutButton);

	}
	
}
