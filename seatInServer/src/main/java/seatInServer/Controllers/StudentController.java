package seatInServer.Controllers;

import static seatInServer.Utilities.ResultType.negative;
import static seatInServer.Utilities.Utilities.getCurrentClassName;
import static seatInServer.Utilities.Utilities.requestParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.Services.Download;
import seatInServer.Services.EmailSender;
import seatInServer.Services.CRUD.ManipolationUserData;
import seatInServer.Services.CRUD.ViewData;

public class StudentController extends Thread{
	
	private Socket clientSocket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	
	protected StudentController(Socket clientSocket, ObjectOutputStream objectOutput, ObjectInputStream objectInput) {
		this.clientSocket = clientSocket;
		this.objectOutput = objectOutput;
		this.objectInput = objectInput;
	}
	
	public void run() {
		
		boolean isActive = true;
		
		while(isActive) {	
			String input = null; 
			String[] command = null; 
			Object result = null;
			
			try {
				logger.debug("Student Controller Start work...");
				//1) Ricevimento del comando da parte del cliente
				input = (String) objectInput.readObject();
			
				//2) Parsing del comando ricevuto
				command = requestParser(input);
				
				ViewData view = new ViewData();
				ManipolationUserData execute = new ManipolationUserData();
				Download download = new Download();
				EmailSender sendEmail= new EmailSender();
				
				//3) Esecuzione comando
				switch(command[0]) {
				case "ViewUserProfileData":
					result = view.viewProfileData(Integer.parseInt(command[1]));
					break;
				case "ViewCourses":
					result = view.viewAllCourses(Integer.parseInt(command[1]));
					break;
				case "ViewCourseContent":
					result = view.viewCourseContent(Integer.parseInt(command[1]), false);
					break;
				case "RegistrationAtCourse":
					result = execute.registrationUserAtCourse(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
					break;
				case "DownloadFile":
					result = download.downloadFile(Integer.parseInt(command[1]));
					break;
				case "DownloadZip":
					result = download.downloadZip(Integer.parseInt(command[1]));
					break;
				case "SendEmail":
					result = sendEmail.genericEmail(command[1], command[2], command[3], command[4], command[5]);
					break;
				default:
					logger.debug("Comando ricevuto non riconosciuto, StudentController: "+input);
					result = negative;	
				}	
				
				view = null;
				execute = null;
				download = null;
				sendEmail= null;
				
				//5) Restituzione del risultato dell'operazione efettuata
				objectOutput.writeObject(result);
				objectOutput.flush();
				
			}catch(NullPointerException e) {
				logger.info("Errore: Parsing comando "+e);
			}catch (ClassNotFoundException e) {
				logger.debug("L'oggetto ricevuto dal cliente è sconosciuto: "+e);
			}catch (IOException e) {
				isActive = false;
				logger.info("Cliente si è disconesso: "+e);
				try {
					logger.debug("Chiusura connessione cliente! "+clientSocket);
					clientSocket.close();
					isActive = false;
				} catch (IOException e1) {
					logger.debug("Errore durante chiusura connessione: "+e);
					isActive = false;
				}
			}
		}
	}
}
