package seatInAdmin.GUI;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PanelMailMulti extends PanelMail {

	protected PanelMailMulti() {
		super();

	}

	@Override
	protected void sendAction() {

		char[] passwordArray = passwordField.getPassword();
		String password = String.copyValueOf(passwordArray);

		String studentsString = reciverField.getText();
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(studentsString.split(" ")));
		ArrayList<Integer> intList = new ArrayList<Integer>();

		for (String s : list) {
			
			int i = Integer.parseInt(s);
			intList.add(i);

		}

		commands.sendEmailToASelectionOfStudents(mailAdressField.getText(), password, intList, subjectField.getText(),
				mail.getText());

		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(c);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new PanelMenu());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.getContentPane().validate();

	}

}
