package seatInServer.Services;

import static seatInServer.Utilities.ResultType.negative;

import seatInServer.JDBC.ExecuteUpdate;
import seatInServer.JDBC.Beans.User;
import seatInServer.JDBC.ExecuteSelect;

public class Login {
	
	private String email;
	private String password;
	private int countWrongLogins = 0;	
	private ExecuteSelect execute;
	
	public Login() {
		execute = ExecuteSelect.getInstance();
	}
	
	/**
	 * Il metod gestisce l'avvio del server. Colui che avvia il server deve essere un user di tipo admin.
	 * Se non lo è, il server non si avvia. 
	 * @param email -- la mail dell'admin.
	 * @param password -- la password dell'account.
	 * @return Object -- che può essere o una Stringa (di tipo DENIED) che indica il fallimento del login.
	 * Oppure un oggetto di tipi User, nel caso il login andasse a buon fine.
	 */
	public Object verify_isAdmin_Data(String email, String password) {

		Object result = this.verifyUserData(email, password);
		if(result instanceof User) {
			User user = (User)result;
			if(user.getUserType().equals("admin"))
				return user;
			else
				return negative;
		}
		return negative;
	}
	/**
	 * Controlla la coretteza dei dati inseriti nella fase di login.
	 * @param email -- la mail dell'utente.
	 * @param password -- la password dell'account.
	 * @return Object -- che può essere o una Stringa (di tipo DENIED) che indica il fallimento del login.
	 * Oppure un oggetto di tipi User, nel caso il login andasse a buon fine.
	 */
	public Object verifyUserData(String email, String password) {
		this.email = email;
		this.password = password;
	
		User user = this.requestData(this.email);
		if(user == null) { 
			countWrongLogins++;
			return negative;
		}
		
		countWrongLogins += user.getWrognLogins();

		if(countWrongLogins >= 10) {
			this.blockAccount(user.getEmail(), this.countWrongLogins);
			return negative;
		}
		
		//Verifica della correttezza della mail e password inseriti da utente
		if(this.checkEmail(this.email, user.getEmail())) {
				if(this.checkPassword(this.password, user.getPassword())) {
					return user;
				}
		}
		countWrongLogins++;
		return negative;
	}
	
	private User requestData(String email) {
		User user = execute.selectUserByEmail(email);
		return user;
	}
	
	/*
	 * "firstEmail" rappresenta l'email inserita dall'utente nel momento del login.
	 * "secondEmail" è la mail che si trova nella base di dati.
	 * Se non coincidono, lo user non può accedere alla piattaforma.
	 */
	private boolean checkEmail(String firstEmail, String secondEmail) {
		//TODO Aggiungere il controllo che blocca l'accaunt in seguito a 10 tentativi d'isenrimento
		//dei dati sbagliati
		if(firstEmail.equals(secondEmail)) {
				return true;
		}
		return false;
	}
	
	/*
	 * "firstPassword" rappresenta l'email inserita dall'utente nel momento del login.
	 * "secondPassword" è la pswd che si trova nella base di dati.
	 * Se non coincidono, lo user non può accedere alla piattaforma.
	 */
	private boolean checkPassword(String firstPswd, String secondPswd) {
		if(firstPswd.equals(secondPswd)) {
			return true;
		}
		return false;
	}
	
	private void blockAccount(String email, int wrongLogins) {
		ExecuteUpdate blockAccount = ExecuteUpdate.getInstance();
		blockAccount.blockAccount(email, wrongLogins);
	}

}
