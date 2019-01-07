package seatInServer.Services.CRUD;

import seatInServer.JDBC.ExecuteSelect;
import seatInServer.JDBC.ExecuteUpdate;
import seatInServer.JDBC.Beans.Admin;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Lecture;
import seatInServer.JDBC.Beans.Student;
import seatInServer.JDBC.Beans.User;
import seatInServer.Services.EmailSender;
import seatInServer.Services.RandomGenerator;

import static seatInServer.Utilities.Utilities.getCurrentClassName;
import static seatInServer.Utilities.ResultType.positive;
import static seatInServer.Utilities.ResultType.negative;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Registration {
	
	private EmailSender send;
	private RandomGenerator random;
	private ExecuteUpdate executeUpdate;
	private ExecuteSelect executeSelect;
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	
	public Registration() {
		this.send = new EmailSender();
		this.random = new RandomGenerator();
		this.executeUpdate = ExecuteUpdate.getInstance();
		this.executeSelect = ExecuteSelect.getInstance();
	}
	/**
	 * Esegue la registrazione di tutti gli oggetti (che possono essere di tipo o sotto tipo di User)
	 * presenti nella struttura dati passato come argomento.
	 * @param users -- contiene gli oggetti da inserire nel database.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
	public String registrationAll(Collection<User> users) {
		String result = negative;
		for(User u: users) {
			if(u instanceof User) {
				this.createUserInstance((User) u);
			}else {
				logger.debug("Il tipo dell'oggetto non è identificabile!!!.");
				result = negative;
			}
		}
		result = positive;
		return result;
	}
	/**
	 * Verifica il tipo concreto dell'oggetto passato, in seguito per inserire nel database.
	 * @param user l'oggetto contenente tutte le informazioni relative ad determinato utente.
	 */
	public void createUserInstance(User user) {
		Integer department = null;
		Integer degreeCourse = null;
		Character careerStatus = null;
		user.setPassword(random.getRandomPassword());
		user.setActivationCode(random.getRandomActivationCode());
		int userType = 0;
		
		if(user instanceof Student) {
			userType = 3;
			Student student = (Student)user;
			careerStatus = student.getCareerStatus();
			degreeCourse = this.executeSelect.selectDegreeCourseIdByName(student.getDegreeCourse());
		}else {
			if(user instanceof Lecture) {
				userType = 2;
				Lecture lecture = (Lecture)user;
				department = executeSelect.selectDepartementIdByName(lecture.getDepartment()); 
			}else 
			{
				if(user instanceof Admin) {
					userType = 1;
					Admin admin = (Admin)user;
					department = executeSelect.selectDepartementIdByName(admin.getDepartment());
				}else {
					logger.debug("Il tipo dell'oggetto passato non corrisponde a nessuno tipo di utente...");
				}
			}
		}
		this.executeInsertUser(user, userType, department, degreeCourse, careerStatus);
	}
	/**
	 * Crea l'istanza di un corso ed esegue l'inserisce nel database.
	 * @param course -- l'oggetto contenente le informazioni riguardanti il determianto corso.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String createCourseInstance(Course course) { 
		Integer degreeCourseId = this.executeSelect.selectDegreeCourseIdByName(course.getDegreeCourse());
		return this.executeUpdate.insertCourse(course, degreeCourseId);
	}
	
	//Raccoglie tutte le informazioni necessarierelative ad un determinato utente per inserirle nel database,
	//ed in seguito manda la mail allo user appena registrato.
	private void executeInsertUser(User user, int userType, Integer department, Integer degreeCourse, Character careerStatus) {	
		this.executeUpdate.insertUser(user, userType, department, degreeCourse, careerStatus);
		send.registrationEmail(user.getEmail(), user.getActivationCode(), user.getPassword());
	}
}
