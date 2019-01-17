package seatInServer.GUI;
import static seatInServer.Utilities.ResultType.negative;

import java.awt.Component;
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

import seatInServer.SeatInServer;
import seatInServer.JDBC.Beans.User;
import seatInServer.Services.Login;

@SuppressWarnings("serial")
public class ServerLogin extends JPanel implements ActionListener{
	
	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel panel1 = new JPanel(new GridBagLayout());
	
	//LABELS
	JLabel title = new JLabel("ADMIN AUTHENTICATION");
	JLabel email = new JLabel("EMail: ");
	JLabel password = new JLabel("Password: ");
	
	//FIELDS
	JTextField emailField = new JTextField(15);
	JPasswordField passwordField = new JPasswordField(15);
	
	//BUTTONS
	JButton nextButton = new JButton("Next");
	
	protected ServerLogin(){
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		title.setFont(title.getFont().deriveFont(25.0f));
		titlePanel.add(title);
		
		nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		nextButton.addActionListener(this);
		
		
		GridBagConstraints gbc = new GridBagConstraints();

		// COMPONENT: COLUMN 0, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(email, gbc);

		// COMPONENT: COLUMN 1, ROW 0

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(emailField, gbc);

		// COMPONENT: COLUMN 0, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		panel1.add(password, gbc);

		// COMPONENT: COLUMN 1, ROW 1

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		panel1.add(passwordField, gbc);

		
		this.add(titlePanel);
		this.add(panel1);
		this.add(nextButton);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String admin_email = emailField.getText();
		char[] password = passwordField.getPassword();
		String admin_password = String.copyValueOf(password);
		
		Object result = new Login().verify_isAdmin_Data(admin_email, admin_password);
		
		if(!result.equals(negative)){
			User user = (User) result;
			if(user.isActive()) {
				
				// Window closing
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
				frame.dispose();
				new SeatInServer();				
			}
			else{
				
				JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new ServerActivation(emailField.getText(), user));
				frame.pack();
				frame.getContentPane().validate();
				
			}
		}else{
			JOptionPane.showOptionDialog(new JFrame(), "Wrong input data!", "", JOptionPane.DEFAULT_OPTION,
			JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
		}		
	}
}
