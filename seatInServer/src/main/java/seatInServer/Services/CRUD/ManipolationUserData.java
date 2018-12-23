package seatInServer.Services.CRUD;

import static seatInServer.Utilities.ResultType.positive;

import java.util.Collection;

import seatInServer.JDBC.ExecuteSelect;
import seatInServer.JDBC.ExecuteUpdate;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.User;
import seatInServer.Services.EmailSender;

public class ManipolationUserData {
	
	private ExecuteUpdate executeUpdate;
	private ExecuteSelect executeSelect;
	private EmailSender send;
	
	public ManipolationUserData() {
		this.executeUpdate = ExecuteUpdate.getInstance();
		this.executeSelect = ExecuteSelect.getInstance();
	}
	/**
	 * Esegue le modifiche relative ad un determinato utente.
	 * @param user l'oggetto contenente i dati da modificare. 
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String modifyUserProfileData(User user) {
		return this.executeUpdate.modifyUserInfo(user);
	}
	
	/**
	 * Abilita un profilo utente. Cambia il tipo del profilo ad uno dei tre possibili: Admin, Lecture o Student.
	 * @param userId dello user, il profilo del quale è da cambiare.
	 * @param userType il tipo di profilo a cui si vuole passare.
	 */
	public void modifyUseProfileType(int userId, String userType) {
		int type = 3;
		
		if(userType.equals("Lecture")) {
			type = 2;
		}else {
			if(userType.equals("Admin")) {
				type = 1;
			}
		}
		executeUpdate.modifyUserType(userId, type);
	}

	/**
	 * Sblocca un profilo utente.
	 * Il metodo può essere utilizzato da un utente di tipi Admin.
	 * @param userId che si vuole sbloccare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String unlockAccount(int userId) {
		return executeUpdate.unlockAccount(userId);
	}
	
	/**
	 * Esegue la registrazione di uno user ad un corso ed in seguito invia la mail di notifica
	 * sia allo studente che hai professori che tenogno il corso.
	 * @param userId - l'id dello user (Studente o Professore).
	 * @param courseId - l'id del corso a cui si vuole registrare. 
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String registrationUserAtCourse(int userId, int courseId) {
		send = new EmailSender();
		
		//Estrazione dei dati relativi ad un determinato utente.
		User user = executeSelect.selectUserById(userId);
		
		//Estrazione delle informazioni relative al corso a cui si è iscritto l'utente.
		Course course = executeSelect.selectCourseById(courseId);
		
		//Estrazione delle mail dei professori che tengono il corso
		Collection<String> lecturesEmail = executeSelect.selectLecturesEmailByCourseId(courseId);
		
		//Registraziione dell'utente al corso
		String result = executeUpdate.registrationUserAtCourse(userId, courseId);
		
		if(result.equals(positive)) {
			//Invio notifica allo studente e ai professori
			send.registrationAtCourseEmail(user.getEmail(), course.getName());
			send.notifyLecturesEmail(lecturesEmail, user, course.getName());
		}
		return result;
	}
	/**
	 * Esegue l'attivazione dell'acount di un utente, in seguito al primo accesso. 
	 * @param userId -- l'id dell'utente.
	 * @param password -- la nuova password inserita dall'utente.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String activationUserAccount(int userId, String password) {
		return executeUpdate.activationUserAccount(userId, password);
	}
}
