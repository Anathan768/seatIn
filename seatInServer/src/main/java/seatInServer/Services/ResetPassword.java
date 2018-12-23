package seatInServer.Services;

import static seatInServer.Utilities.Utilities.getCurrentClassName;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.ExecuteUpdate;
import seatInServer.Services.RandomGenerator;

public class ResetPassword {

	private RandomGenerator random;
	private EmailSender send;
	private ExecuteUpdate execute;
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	
	public ResetPassword() {
		this.random = new RandomGenerator();
		this.execute = ExecuteUpdate.getInstance();
		this.send = new EmailSender();
	}
	
	/**
	 * Esegue il Reset Password dell'account allegato alla mail, passato come argomento.
	 * Se l'argomento passato è null, o non corrisponde ad una mail presente nel db, non succede niente.
	 * Una volta l'operazione andata a buon fine, l'occount viene disattivato e dovra essere rittivato di nuovo dall'utente, al prossimo login.
	 * @param email l'indirizzo della posta eletronica dell'utente che esegue il reset password.
	 */
	public void execute(String email) {
		logger.debug("Esecuzione ResetPassword...");
		String password = ""+random.getRandomPassword();
		String activationCode =""+random.getRandomActivationCode();
		
		execute.updatePasswordAndActivationCode(email, password, activationCode);
		send.resetPasswordEmail(email, activationCode, password);
	}
}
