package seatInAdmin.GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PanelMailCourse extends PanelMail {

	protected PanelMailCourse() {
		super();
		reciverLabel.setText("Course ID:");

	}

	@Override
	protected void sendAction() {

		char[] passwordArray = passwordField.getPassword();
		String password = String.copyValueOf(passwordArray);

		commands.sendEmailToCourseStudents(mailAdressField.getText(), password, Integer.parseInt(reciverField.getText()),
				subjectField.getText(), mail.getText());

		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelMenu());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.getContentPane().validate();

	}

}
