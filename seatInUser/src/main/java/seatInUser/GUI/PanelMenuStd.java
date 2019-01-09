package seatInUser.GUI;

import java.awt.Component;
import java.util.Collection;

import javax.swing.table.DefaultTableModel;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.User;
import seatInUser.Items;
import seatInUser.StudentCommands;

@SuppressWarnings("serial")
public class PanelMenuStd extends PanelMenu {
	
	Component c = this;
	StudentCommands commands;
	public PanelMenuStd() {
		super();

		//TODO differenza inizializzazione con admin
		commands = StudentCommands.getInstance();
		// Estrazione dei dati relativi all'utente dall'oggetto "Items"
		User user = Items.getUserData();
		// Richiesta da server della lista dei corsi gestiti dall'utente
		coursesList = commands.viewCourseList(user.getId());
		// Salvataggio della lista dei corsi ricevuti dal server nell'oggetto
		Items.setUserCourses(coursesList);
		fillTable(model);

		//generateSummary(user.getName(), user.getSurname(), String.valueOf(user.getId()));

	}

	@Override
	protected void fillTable(DefaultTableModel model) {

		for (Course c : coursesList) {

			model.addRow(new Object[] { c.getId(), c.getName(), c.getActivationDate(), c.getDegreeCourse() });
		}

	}

}
