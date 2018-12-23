package seatInServer.Services;

import static seatInServer.Utilities.ResultType.negative;
import static seatInServer.Utilities.ResultType.positive;

import java.util.Collection;

import seatInServer.JDBC.ExecuteSelect;
import seatInServer.JDBC.Beans.User;

public class NewsLetter {

	private ExecuteSelect executeSelect;
	private EmailSender send;
	
	public NewsLetter() {
		executeSelect = ExecuteSelect.getInstance();
		send = new EmailSender();
	}

	/**
	 * Il metodo si occupa di inoltrare la mail a tutti gli studente che seguono un determinato corso.
	 * @param senderEmail -- l'indirizzo del mittente.
	 * @param senderPassword -- la password della posta eletrinoca del mittenre.
	 * @param courseId -- l'id del corso.
	 * @param emailSubject -- il titolo della mail;
	 * @param emailBody -- Il messaggio da inviare via mail.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String allertAllCourseStudents(String senderEmail, String senderPassword, int courseId, String emailSubject, String emailBody) {
		String result = negative;
		Collection<String> emails = executeSelect.selectAllStudentsWhoFollowDeterminedCourse(courseId);
		for(String email: emails) {
			send.genericEmail(senderEmail, senderPassword, email, emailSubject, emailBody);
		}
		result = positive;
		return result;
	}
	
	/**
	 * Il metodo si occupa di inoltrare la mail ad una selezione di studenti.
	 * @param senderEmail -- la mail del mittente.
	 * @param senderPassword -- la password dell'account del mittente.
	 * @param emailSubject il titolo della mail.
	 * @param emailBody il contenuto della mail.
	 * @param students la selezione degli studenti a cui viene mandata la mail.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String allertASelectionOfStudents(String senderEmail, String senderPassword, Collection<Integer> students, String emailSubject, String emailBody) {
		String result = negative;
		for(Integer id: students) {
			User user = executeSelect.selectUserById(id);
			send.genericEmail(senderEmail, senderPassword, user.getEmail(), emailSubject, emailBody);
		}
		result = positive;
		return result;
	}
}
