package seatInUser.GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Student;
import seatInServer.JDBC.Beans.User;
import seatInUser.Items;
import seatInUser.LectureCommands;
import seatInUser.StudentCommands;

@SuppressWarnings("serial")
public class PanelMailSingle extends PanelMail {

	StudentCommands commands;

	User user;

	protected PanelMailSingle() {
		super();
		user = Items.getUserData();
		if (user.getUserType().equals("student")) {

			commands = StudentCommands.getInstance();

		} else {
			if (user.getUserType().equals("lecturer"))
				commands = LectureCommands.getInstance();
		}

	}

	@Override
	protected void sendAction() {

		char[] passwordArray = passwordField.getPassword();
		String password = String.copyValueOf(passwordArray);

		commands.sendEmail(mailAdressField.getText(), password, reciverField.getText(), subjectField.getText(),
				mail.getText());

		if (user.getUserType().equals("student")) {

			commands.sendEmail(mailAdressField.getText(), password, reciverField.getText(), subjectField.getText(),
					mail.getText());

			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelMenuStd());
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.getContentPane().validate();

		}

		if (user.getUserType().equals("lecturer")) {
			
			commands.sendEmail(mailAdressField.getText(), password, reciverField.getText(), subjectField.getText(),
					mail.getText());


			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelMenuTeach());
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.getContentPane().validate();

		}

	}

}
