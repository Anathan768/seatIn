package seatInServer.Controllers;

import static seatInServer.Utilities.ResultType.negative;
import static seatInServer.Utilities.Utilities.getCurrentClassName;
import static seatInServer.Utilities.Utilities.requestParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.Beans.*;
import seatInServer.Services.AdminStatistic;
import seatInServer.Services.Download;
import seatInServer.Services.NewsLetter;
import seatInServer.Services.CRUD.*;

public class AdminController extends Thread{
	
	private Socket clientSocket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	
	protected AdminController(Socket clientSocket, ObjectOutputStream objectOutput, ObjectInputStream objectInput) {
		this.clientSocket = clientSocket;
		this.objectOutput = objectOutput;
		this.objectInput = objectInput;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		boolean isActive = true;
		
		while(isActive) {			
			String input = null;
			String[] command = null; 
			Object result = null;
						
			try {
				logger.debug("Admin Controller start work...");
				//1) Ricevimento del comando da parte del cliente
				input = (String) objectInput.readObject();
				Registration registration = new Registration();
				ViewData view = new ViewData();
				ManipolationUserData userData = new ManipolationUserData();
				ManipolationCourseData courseData = new ManipolationCourseData();
				NewsLetter email = new NewsLetter();
				AdminStatistic statistic = new AdminStatistic();
				Download download = new Download();
				
				//2) Parsing del comando ricevuto
				command = requestParser(input);
				//3) Esecuzione comando
				switch(command[0]) {
				case "ViewUserProfileData":
					result = view.viewProfileData(Integer.parseInt(command[1]));
					break;
				case "ViewCourseData":
					result = courseData.viewCourseData(Integer.parseInt(command[1]));
					break;
				case "ViewCourses":	
					result = view.viewAllCourses(Integer.parseInt(command[1]));		
					break;
				case "ViewCourseContent":
					result = view.viewCourseContent(Integer.parseInt(command[1]), Boolean.parseBoolean(command[2]));
					break;
				case "RegistrationAtCourse":
					result = userData.registrationUserAtCourse(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
					break;
				case "DownloadFile": 
					result = download.downloadFile(Integer.parseInt(command[1]));
					break;
				case "DownloadZip":
					result = download.downloadZip(Integer.parseInt(command[1]));	
					break;
				case "RegistrationFromCSVFile":
					Collection<User> col = (Collection<User>) this.objectInput.readObject();
					result = registration.registrationAll(col);					
					break;
				case "ModifyUserData":
					User user = (User) objectInput.readObject();
					result = userData.modifyUserProfileData(user);
					break;
				case "UnlockAccount":
					result = userData.unlockAccount(Integer.parseInt(command[1]));
					break;
				case "CreateCourseInstance":
					Course courseInstance = (Course)this.objectInput.readObject();
					result = registration.createCourseInstance(courseInstance);
					break;
				case "ModifyCourseData": 
					Course modifyCourse = (Course)this.objectInput.readObject();
					result = courseData.modifyCourseInfo(modifyCourse);
					break;	
				case "sendEmailToCourseStudents":
					result = email.allertAllCourseStudents(command[1], command[2], Integer.parseInt(command[3]), command[4], command[5]);
					break;
				case "sendEmailToASelectionOfStudents":
					Collection<Integer> students = (Collection<Integer>) this.objectInput.readObject();
					result = email.allertASelectionOfStudents(command[1], command[2], students, command[3], command[4]);
					break;
				case "viewTotalNumberConnectedUsers":
					result = statistic.getTotalNumberConnectedUsers();
					break;
				case "usersHaveDownloadOneOrMoreResources":	
					result = statistic.getTotalNumberOfUsersHaveDownloadOneOrMoreResoursesInTimeIntervals(Timestamp.valueOf(command[1]), Timestamp.valueOf(command[2]));
					break;
				case "averageConnectionTime":
					result = statistic.getAverageConnectionTimeOfStudentsToTheCoursePage();
					break;
				case "viewTotalNumberOfAccessesPerCourse":
					result = statistic.getTotalNumberOfAccessesPerCourseInTimeBand(Timestamp.valueOf(command[1]), Timestamp.valueOf(command[2]));
					break;
				case "viewAverageConnectionTimeOfStudents":
					result = statistic.getAverageConnectionTimeOfStudentsForEachCourse();
					break;
				case "viewTotalNumberDownloadsPerCourse":
					result = statistic.getTotalNumberDownloadsForEachCourse();
					break;
				case "Close":
					isActive=false;
					logger.info("Chiusura connessione cliente! "+clientSocket);
					break;
				default:
					logger.debug("Comando ricevuto non riconosciuto, AdminController: "+input);
					result = negative;
				}	
				registration = null;
				view = null;
				userData = null;
				courseData = null;
				email = null;
				download = null;
				statistic = null;
				
				//5) Restituzione del risultato dell'operazione eseguita
				objectOutput.writeObject(result);
				objectOutput.flush();
			}catch(NullPointerException e) {
				logger.debug("Errore: Parsing comando "+e);
			}catch (ClassNotFoundException e) {
				logger.debug("L'oggetto ricevuto dal cliente è sconosciuto: "+e);
			}catch (IOException e) {
				isActive=false;
				logger.debug("Cliente si è disconesso: "+e);
				try {
					logger.info("Chiusura connessione cliente! "+clientSocket);
					clientSocket.close();
					isActive=false;
				} catch (IOException e1) {
					logger.debug("Errore durante chiusura connessione: "+e);
					isActive=false;
				}
			}
		}
	}
}
