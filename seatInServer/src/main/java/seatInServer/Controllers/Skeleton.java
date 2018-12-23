package seatInServer.Controllers;

import static seatInServer.Utilities.ResultType.negative;
import static seatInServer.Utilities.Utilities.getCurrentClassName;
import static seatInServer.Utilities.Utilities.requestParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.Beans.User;
import seatInServer.Services.Login;
import seatInServer.Services.ResetPassword;
import seatInServer.Services.CRUD.ManipolationUserData;

public class Skeleton extends Thread{
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	private Socket clientSocket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	
	public Skeleton(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket; 
		logger.debug("Inizializzo la connessione con il cliente: "+clientSocket);
		initializeStreams();
	}

	public void run() {
				
		boolean isActive = true;
		
		while(isActive) {
			String input = null;
			String[] command = null; 
			Object result = null;
			
			try {
				//1) Ricevimento della chiamata da parte del cliente
				input = (String) objectInput.readObject();
				
				//2) Parsing della chiamata ricevuta
				command = requestParser(input);

				Login login = new Login();; 
				ResetPassword reset = new ResetPassword();
				ManipolationUserData execute = new ManipolationUserData();
				
				//3) Esecuzione della richiesta effettuata
				switch(command[0]) {
				case "Login":
						result = login.verifyUserData(command[1],command[2]);
					break;
				case "ResetPassword":
						reset.execute(command[1]);
					break;
				case "ActivationAccount":
					result = execute.activationUserAccount(Integer.parseInt(command[1]), command[2]);
					break;				
				default: 
					logger.debug("Comando ricevuto non riconosciuto, Skeleton: "+input);
					result = negative;
				}
			
			//4) Restituzione del risultato dell'operazione eseguita
			objectOutput.writeObject(result);
			objectOutput.flush();
			
			//5)Assegnazione di un determinato controller ad un determinato utente. 
			if(result instanceof User) {
				User user = (User) result;
				if(user.isActive()) {
					if(user.getUserType().equals("student")) {
						new Thread(new StudentController(clientSocket, objectOutput, objectInput)).start();
					}
					if(user.getUserType().equals("lecturer") || user.getUserType().equals("lecture")) {
						new Thread(new LectureController(clientSocket, objectOutput, objectInput)).start();
					}
					if(user.getUserType().equals("admin")) {
						new Thread(new AdminController(clientSocket, objectOutput, objectInput)).start();
					}
					clientSocket = null;
					objectOutput = null;
					objectInput = null;
					isActive = false;
				}
			}
			
			}catch(NullPointerException e) {
				logger.debug("Errore: Parsing comando "+e);
			}catch (ClassNotFoundException e) {
				logger.debug("L'oggetto ricevuto dal cliente è sconosciuto: "+e);
			}catch(SocketException e) {
				isActive = false;
				logger.debug("Errore connessione client: "+e);
			}catch (IOException e) {
				try {
					logger.debug("Chiusura connessione cliente! "+clientSocket);
					clientSocket.close();
				} catch (IOException e1) {
					logger.debug("Errore durante chiusura connessione: "+e1);
				}
			}
		}
	}
	/*
	 *  Inizializzazione degli Stream per la connessione con l'utente.
	 *  Ogni inizializzazione è stato messo nel prorpio blocco try catch,
	 *  per gestire meglio e separatamente gli errori che si possono verificare durante ogni passaggio.
	 */
	private void initializeStreams() throws IOException {
		try {
			this.objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			logger.debug("Errore: inizializzazione ObjectOutputStream!"+e);
			throw e;
		}
		try {
			this.objectInput = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			logger.debug("Errore: inizializzazione ObjectInputStream!"+e);
			throw e;
		}
	}

}
