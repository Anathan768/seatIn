package seatInServer.GUI;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import seatInServer.*;
import seatInServer.JDBC.ExecuteUpdate;


@SuppressWarnings("serial")
public class ServerNewPassword extends JPanel implements ActionListener {

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel panel1 = new JPanel(new GridBagLayout());
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// LABELS
	JLabel title = new JLabel("SETTING PASSWORD");
	JLabel pswLabel = new JLabel("Password: ");
	JLabel confirmPswLabel = new JLabel("Repeat Password: ");

	// FIELDS
	JTextField pswField = new JPasswordField(15);
	JTextField confirmPswField = new JPasswordField(15);

	// BUTTONS
	JButton startButton = new JButton("Start Server");
	
	int userId;

	protected ServerNewPassword(int userId) {

		this.userId = userId;
		
		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		title.setFont(title.getFont().deriveFont(25.0f));
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(this);

		GridBagConstraints gbc = new GridBagConstraints();

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(pswLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(pswField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(confirmPswLabel, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(confirmPswField, gbc);

		titlePanel.add(title);
		this.add(titlePanel);
		this.add(panel1);
		this.add(startButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String password = pswField.getText();
		String confirmPsw = confirmPswField.getText();
		
		if(password.equals(confirmPsw)) {
			ExecuteUpdate update = ExecuteUpdate.getInstance();
			
			//Aggiornamento psw nel database(attivazione account)
			update.activationUserAccount(this.userId, password);
			
			// Window closing
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.dispose();
			
			new SeatInServer();
			
		}else {
			JOptionPane.showOptionDialog(new JFrame(), "Passwords do not match!", "", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
		}

	

	}

}
