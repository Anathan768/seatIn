package seatInServer.GUI;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


import seatInServer.JDBC.ConnectionPool.ConnectionPool;



@SuppressWarnings("serial")
public class ServerConfig extends JPanel implements ActionListener {

	// PANELS
	JPanel panel1 = new JPanel(new GridBagLayout());
	JPanel titlePanel = new JPanel();

	// LABELS
	JLabel title = new JLabel("CONNECTION SETTINGS");
	JLabel hostLabel = new JLabel("Host: ");
	JLabel dbNameLabel = new JLabel("Database Name: ");
	JLabel userLabel = new JLabel("User: ");
	JLabel passLabel = new JLabel("Password: ");

	// FIELDS
	JTextField hostField = new JTextField("localhost:5432",15);
	JTextField dbNameField = new JTextField("dbSeatIn",15);
	JTextField userField = new JTextField("postgres",15);
	JTextField passField = new JPasswordField("13579sorc768",15);

	// BUTTONS
	JButton connectButton = new JButton("Connect");

	protected ServerConfig() {

		title.setFont(title.getFont().deriveFont(25.0f));
		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		title.setFont(title.getFont().deriveFont(25.0f));

		connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		connectButton.addActionListener(this);

		GridBagConstraints gbc = new GridBagConstraints();

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(hostLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(hostField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(dbNameLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(dbNameField, gbc);

		// COMPONENT: COLUMN 0, ROW 2

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(userLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(userField, gbc);

		// COMPONENT: COLUMN 0, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(passLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 3

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(passField, gbc);

		titlePanel.add(title);

		this.add(titlePanel);
		this.add(panel1);
		this.add(connectButton);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String db_host = hostField.getText();
		String db_name = dbNameField.getText();
		String db_username = userField.getText();
		String db_password = passField.getText();
		
		boolean temp = true;
		
		try {
			ConnectionPool.setConfigurations(db_host,db_name, db_username, db_password);
			temp = true;
		} catch (SQLException e1) {
			temp = false;
		}
		
		if(temp == true){
		JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new ServerMenu());
		frame.pack();
		frame.getContentPane().validate();
		}
		else{
			JOptionPane.showOptionDialog(new JFrame(), "Server Connection Failed. Input data are wrong!", "", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
			
		}
	}

}
