package seatInServer.GUI;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import seatInServer.JDBC.Beans.Admin;
import seatInServer.Services.CRUD.Registration;

@SuppressWarnings("serial")
public class ServerRegistration extends JPanel implements ActionListener {

	// LABELS
	JLabel title = new JLabel("REGISTRATION");
	JLabel surname = new JLabel("Surname: ");
	JLabel name = new JLabel("Name: ");
	JLabel email = new JLabel("EMail: ");
	JLabel structure = new JLabel("Structure: ");

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel panel1 = new JPanel(new GridBagLayout());
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// FIELDS
	JTextField surField = new JTextField(20);
	JTextField nameField = new JTextField(20);
	JTextField emailField = new JTextField(20);
	JTextField structField = new JTextField(20);

	// BUTTONS
	JButton sign = new JButton("Sign In");

	protected ServerRegistration() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		title.setFont(title.getFont().deriveFont(25.0f));

		titlePanel.add(title);
		
		sign.addActionListener(this);

		GridBagConstraints gbc = new GridBagConstraints();

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(surname, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(surField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(name, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(nameField, gbc);

		// COMPONENT: COLUMN 0, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(email, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(emailField, gbc);

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(structure, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(structField, gbc);

		panel2.add(sign);

		this.add(titlePanel);
		this.add(panel1);
		this.add(panel2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Admin admin = new Admin();
		admin.setSurname(surField.getText());
		admin.setName(nameField.getText());
		admin.setEmail(emailField.getText());
		admin.setDepartment(structField.getText());
		
		//Esecuzione registrazione di un utente di tipi admin
		new Registration().createUserInstance(admin);
		
		//Return to Menu
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new ServerMenu());
		frame.pack();
		frame.getContentPane().validate();

	}

}
