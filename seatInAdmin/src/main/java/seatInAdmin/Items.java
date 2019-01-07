package seatInAdmin;

import java.util.Collection;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInServer.JDBC.Beans.User;

public class Items {
	
	private static Proxy connection; //Riferimento alla connessione dell'utente al server.
	private static User userData; //Riferimento all'oggetto contenente le informazione relative all'utente.
	private static Collection<Course> courses; //Riferimento alla lista contiene corsi seguiti dall'utente.
	private static Collection<Section> sections; //Riferimento alla lista delle sezioni relativi ad un determianto corso. 
	
	private Items() {
		
	}

	public static void setServerConnection(Proxy conn) {
		connection = conn;
	}
	
	public static Proxy getServerConnection() {
		return connection;
	}
	
	public static void setUserData(User user) {
		userData = user;
	}
	
	public static User getUserData() {
		return userData;
	}
	
	public static void setUserCourses(Collection<Course> userCourses) {
		courses = userCourses;
	}
	
	public static Collection<Course> getUserCourses() {
		return courses;
	}
	
	public static void setSectionsOfCourse(Collection<Section> sectionS) {
		sections = sectionS;
	}
	
	public static Collection<Section> getSectionOfCourse() {
		return sections;
	}
	
	public static void closeConnection() {
		connection.close();
	}

}
