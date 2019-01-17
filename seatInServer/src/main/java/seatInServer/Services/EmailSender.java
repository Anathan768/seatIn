package seatInServer.Services;

import static seatInServer.Utilities.Utilities.getCurrentClassName;
import static seatInServer.Utilities.ResultType.negative;
import static seatInServer.Utilities.ResultType.positive;

import java.util.Collection;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.Beans.User;

public class EmailSender {
	
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	private String host = "smtp.office365.com";
	private Properties props;
	private Session session;
	private Message initMessage;
	
	private String username = "aroshka@studenti.uninsubria.it"; //Qui andrebbe inserita l'indirizzo mail vero(cioè esistente) dell'ateneo.
	private String pswd = "2407Diana1995!"; //Qui la password dell'account della posta eletronica dell'atteneo.
	
	public EmailSender() {
		props = System.getProperties();
	    props.put("mail.smtp.host",this.host);
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.port",587);
	    session = Session.getInstance(this.props);
	}
	/**
	 * Seguente metodo rappresenta la mail che viene mandata all'utente dopo essere stato iscritto da uno degli admin.
	 * @param recipientEmail l'idirizzo email del destinatario
	 * @param activationCode codice attivazione account, generato casualmente. 
	 * @param password generata casualmente.
	 */
	public void registrationEmail(String recipientEmail, String activationCode, String password) {
		String subject = "Iscrizione all'Insubria";
		String body = "Benvenuto all'insubria, \n Il suo codice Attivazione Account è: "+activationCode;
		body += "\n La password è: "+password;
		this.sendEmail(this.username, this.pswd, recipientEmail, subject, body);		
	}
	/**
	 * Seguente metodo rappresenta la mail che viene mandata nel caso della richiesta "Reset Password".
	 * @param recipientEmail l'indirizzo di posta eletronica del destinatario.
	 * @param activationCode il codice attivazione account.
	 * @param password la password generata casualmente.
	 */
	public void resetPasswordEmail(String recipientEmail, String activationCode, String password) {
		String subject = "Reset Password";
		String body = "Salve, \n Il suo codice Attivazione Account è: "+activationCode;
		body += "\n La sua nuova password è: "+password;
		this.sendEmail(this.username, this.pswd, recipientEmail, subject, body);
	}
	/**
	 * Quando un utente si iscrivve ad un corso, li viene mandata una mail di nofifica.
	 * @param recipientEmail l'indirizzo del destinatario.
	 * @param courseName il nome del corso.
	 */
	public void registrationAtCourseEmail(String recipientEmail, String courseName) {
		String subject = "Iscrizione al corso di "+courseName;
		String body = "La tua iscrizione al corso "+courseName+" è andata a buon fine.";
		this.sendEmail(this.username, this.pswd, recipientEmail, subject, body);
	}
	/**
	 * Notifica un professore/professori dell'iscrizione effettuata da parte di uno studente
	 * al corso tenuto da professori stessi.
	 * @param lecturesEmail le mail dei professori che tengono il corso.
	 * @param user -- le informazioni relative ad un determinato user.
	 * @param courseName -- il nome del corso.
	 */
	public void notifyLecturesEmail(Collection<String> lecturesEmail, User user, String courseName) {
		String subject = "Iscrizione di uno Studente al corso: "+courseName;
		String  body = "Iscrizione effettuada da parte di uno studente: "+user.getSurname()+" "+user.getName() +
				"al corso: "+courseName;
		
		for(String email : lecturesEmail)
			this.sendEmail(this.username, this.pswd, email, subject, body);
	}
	/**
	 * Questo metodo può essere utilizzato, sia dagli studenti, che possono mandare delle mail ai professori,
	 * che da professori e admin, per communicare gli avvisi agli studenti.
	 * L'unico svantaggio è che, ci vuole un account esistente. Perchè richiede sia l'indirizzo di posta vero, che la password
	 * dello stesso account email vero.
	 * @param senderEmail l'indirizzo del mittente.
	 * @param senderPassword la password associata all'acount email del mittente.
	 * @param recipientEmail l'indirizzo posta del destinatario.
	 * @param emailSubject il titolo della mail.
	 * @param emailBody il contenuto della mail.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String genericEmail(String senderEmail, String senderPassword, String recipientEmail, String emailSubject, String emailBody) {
		return this.sendEmail(senderEmail, senderPassword, recipientEmail, emailSubject, emailBody);
	}
		
	//Inizializzazione ed invio effettivo della mail.
	private String sendEmail(String username, String pswd, String recipientEmail, String emailSubject, String emailBody) {
		String result = negative;
		try {
			this.initMessage = new MimeMessage(session);
			 initMessage.setFrom(new InternetAddress(username));
			 initMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipientEmail, false));
			 initMessage.setSubject(emailSubject);
			 initMessage.setText(emailBody);
			 Transport.send(initMessage,username,pswd);
			 result = positive;
		}catch(AddressException e) {
			logger.debug("Impostazione indirizzo per inviare mail, fallito: "+e);
		}catch(MessagingException e) {
			logger.debug("Invio messaggio via mail, fallito: "+e);
		}
		return result;
	}
}
