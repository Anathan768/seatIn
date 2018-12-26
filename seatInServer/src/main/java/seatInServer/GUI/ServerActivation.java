package seatInServer.GUI;
import java.awt.FlowLayout;
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

import seatInServer.JDBC.Beans.User;

@SuppressWarnings("serial")
public class ServerActivation extends JPanel implements ActionListener {

	// PANELS
	JPanel titlePanel = new JPanel();
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	// LABELS
	JLabel title = new JLabel("ADMIN CODE");
	JLabel eMail = new JLabel();
	JLabel code = new JLabel("Activation Code: ");

	// FILEDS
	JTextField codeField = new JPasswordField(10);

	// JBUTTON
	JButton send = new JButton("Send");

	private User user;
	
	protected ServerActivation(String admin, User user) {
		
		this.user = user;
		
		eMail .setText("Admin: " + admin);
		eMail.setAlignmentX(CENTER_ALIGNMENT);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setBorder((new EmptyBorder(5, 5, 10, 5)));

		title.setFont(title.getFont().deriveFont(25.0f));

		send.addActionListener(this);

		panel1.add(code);
		panel1.add(codeField);

		panel2.add(send);

		titlePanel.add(title);

		this.add(titlePanel);
		this.add(eMail);
		this.add(panel1);
		this.add(panel2);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String activationCode = user.getActivationCode();
		String inputActivetionCode = codeField.getText();
		
		if(activationCode.equals(inputActivetionCode)) {
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new ServerNewPassword(user.getId()));
			frame.pack();
			frame.getContentPane().validate();
		}else {
			JOptionPane.showOptionDialog(new JFrame(), "Wrong Activation Code!", "", JOptionPane.DEFAULT_OPTION,
			JOptionPane.ERROR_MESSAGE, null, new Object[] {}, null);
			
		}

	}

}
