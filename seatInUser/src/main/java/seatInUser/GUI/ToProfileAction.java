package seatInUser.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import seatInServer.JDBC.Beans.Lecture;
import seatInServer.JDBC.Beans.Student;
import seatInServer.JDBC.Beans.User;
import seatInUser.Items;
import seatInUser.LectureCommands;

@SuppressWarnings("serial")
public class ToProfileAction extends AbstractAction {

	Component component;
	Boolean userType;
	LectureCommands commands = LectureCommands.getInstance();
	User user = null;
	
	public ToProfileAction (Component c1) {
		this.component = c1;
		this.user = Items.getUserData();
		if (user.getUserType().equals("lecturer")) {
			this.userType = true;
		} else {
			this.userType = false;
		}

		
	}

	public void actionPerformed(ActionEvent e) {
		
		User instance = commands.viewUserProfileData(this.user.getId());
		
		if (instance instanceof Lecture) {
			Lecture lt = (Lecture)instance;
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelProfileTeach(lt));    //TODO link al corso teacher
			frame.pack();
			frame.getContentPane().validate();
			
		} else {
				if(instance instanceof Student) {
				Student st = (Student)instance;
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelProfileStd(st));        //TODO link al corso student
				frame.pack();
				frame.getContentPane().validate();
			}else {
				return;
			}

		}

	}

}
