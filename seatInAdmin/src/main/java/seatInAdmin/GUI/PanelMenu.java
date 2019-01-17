package seatInAdmin.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Collection;

import javax.swing.BorderFactory;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import seatInAdmin.AdminCommands;
import seatInAdmin.Items;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.User;

@SuppressWarnings("serial")
public class PanelMenu extends JPanel {

	Component c = this;
	AdminCommands commands;
	
	
	Collection<Course> coursesList;

	// PANELS
	JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel mailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel coursePanel = new JPanel();
	JPanel courseButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	JPanel modifyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	// LABELS
	JLabel titleLabel = new JLabel("Menu Options");

	// BUTTONS
	JButton logoutButton = new JButton("Exit");

	JButton globalMail = new JButton("EMail to Course");
	JButton studentMail = new JButton("EMail to Students");

	JButton unlockButton = new JButton("Unlock User");
	JButton signUserButton = new JButton("Sign user to Course");
	JButton profileButton = new JButton("View User");
	JButton activationButton = new JButton("Activate User");

	JButton createCourseButton = new JButton("Add new Course");
	
	JButton statsButton = new JButton("General Statistics");

	// TABLE & LAYOUTING
	DefaultTableModel model;
	JTable courses;
	JScrollPane coursesScroll;
	ListSelectionModel list;

	public PanelMenu() {
		
		//Oggetto contente la lista delle chiamate al server
		commands = AdminCommands.getInstance();
		//Estrazione dei dati relativi all'utente dall'oggetto "Items"
		User user = Items.getUserData();
		//Richiesta da server della lista dei corsi gestiti dall'utente 
		coursesList = commands.viewCourseList(user.getId());
		//Salvataggio della lista dei corsi ricevuti dal server nell'oggetto "Items"
		Items.setUserCourses(coursesList);
		
		
		
		tableInitalization();
		fillTable(model);
		initializeButtons();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.PAGE_AXIS));

		titleLabel.setFont(titleLabel.getFont().deriveFont(20.0f));

		mailPanel.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "EMail Functions"));
		userPanel.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "User Functions"));
		coursePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Courses"));
		statsPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Statistics"));

		logoutPanel.setBorder((new EmptyBorder(0, 5, 5, 5)));

		titlePanel.add(titleLabel);

		statsPanel.add(statsButton);
		
		mailPanel.add(globalMail);
		mailPanel.add(studentMail);

		userPanel.add(profileButton);
		userPanel.add(signUserButton);
		userPanel.add(activationButton);
		userPanel.add(unlockButton);

		courseButtonPanel.add(createCourseButton);

		coursePanel.add(coursesScroll);
		coursePanel.add(courseButtonPanel);

		logoutPanel.add(logoutButton);
		

		this.add(titlePanel);
		this.add(coursePanel);
		this.add(userPanel);
		this.add(mailPanel);
		this.add(statsPanel);
		this.add(logoutPanel);

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
				if (!list.isSelectionEmpty()) {
					int selectedRow = list.getMinSelectionIndex();
					for(Course course: coursesList) {
						if(course.getId() == (int)courses.getValueAt(selectedRow, 0)) {
							new ToCourse(c, course);
						}
					}
				}

			}
		};

		list.addListSelectionListener(asd);

	}

	private void fillTable(DefaultTableModel model) {
		
		for(Course c: coursesList) {
			
			
			model.addRow(new Object[] { c.getId(), c.getName(), c.getActivationDate() , c.getDegreeCourse() });
		}
	}

	private void initializeButtons() {
		globalMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMailCourse());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		studentMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMailMulti());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		createCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelAddCourse());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		unlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelUnlockUser());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		activationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelActivateUser());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		signUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelUserReg());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();
			}

		});

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commands.close();
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));								
			}

		});

		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelViewUser());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();


			}

		});
		
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelGeneralStats());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.getContentPane().validate();


			}

		});

	}

}
