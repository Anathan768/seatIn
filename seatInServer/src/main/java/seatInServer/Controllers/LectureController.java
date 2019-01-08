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

import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;
import seatInServer.Services.Download;
import seatInServer.Services.LectureStatistic;
import seatInServer.Services.NewsLetter;
import seatInServer.Services.CRUD.ManipolationCourseData;
import seatInServer.Services.CRUD.ManipolationUserData;
import seatInServer.Services.CRUD.ViewData;

public class LectureController extends Thread{
	
	private Socket clientSocket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	
	protected LectureController(Socket clientSocket, ObjectOutputStream objectOutput, ObjectInputStream objectInput) {
		this.clientSocket = clientSocket;
		this.objectOutput = objectOutput;
		this.objectInput = objectInput;	
	}
	
	@SuppressWarnings("unused")
	public void run() {
		
		boolean isActive = true;
		
		while(isActive) {			
			String input = null;
			String[] command = null; 
			Object result = null;
						
			try {
				logger.debug("Lecture Controller Start work...");
				//1) Ricevimento del comando da parte del cliente
				input = (String) objectInput.readObject();
				
				//2) Parsing del comando ricevuto
				command = requestParser(input);
				
				ViewData view = new ViewData();
				ManipolationUserData execute = new ManipolationUserData();
				ManipolationCourseData upgradeCourse = new ManipolationCourseData();
				NewsLetter email = new NewsLetter();
				Download download = new Download();
				LectureStatistic statistic = new LectureStatistic();
				
				//3) Esecuzione comando
				switch(command[0]) {
				case "ViewUserProfileData":
					result = view.viewProfileData(Integer.parseInt(command[1]));
					break;
				case "ViewCourses":
					result = view.viewAllCourses(Integer.parseInt(command[1]));
					break;
				case "ViewCourseContent":
					result = view.viewCourseContent(Integer.parseInt(command[1]), Boolean.parseBoolean(command[1]));
					break;
				case "RegistrationAtCourse":
					result = execute.registrationUserAtCourse(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
					break;
				case "addSectionToCourse":
					Section section = (Section) this.objectInput.readObject();
					result = upgradeCourse.addSectionToCourse(section);					
					break;
				case "addResourceToSection":
					Resource resource = (Resource) this.objectInput.readObject();
					result = upgradeCourse.addResourseToCourseSection(resource);
					break;
				case "addFileToResource":
					byte[] fileContent = (byte[]) this.objectInput.readObject();
					result = upgradeCourse.addFileToCourseResource(Integer.parseInt(command[1]), command[2], fileContent);
					break;
				case "DownloadFile":
					result = download.downloadFile(Integer.parseInt(command[1]));
					break;
				case "DownloadZip":
					result = download.downloadZip(Integer.parseInt(command[1]));
					break;
				case "modifyCourseSection":
					Section modifySection = (Section)this.objectInput.readObject();
					result = upgradeCourse.modifyCourseSection(modifySection);
					break;
				case "modifySectionResource":
					Resource modifyRresource = (Resource) this.objectInput.readObject();
					result = upgradeCourse.modifySectionResource(modifyRresource);	
					break;
				case "deleteCourseSection":
					result = upgradeCourse.deleteCourseSection(Integer.parseInt(command[1]));
					break;
				case "deleteSectionResource":
					result = upgradeCourse.deleteSectionResource(Integer.parseInt(command[1]));
					break;
				case "deleteResourceFile":
					result = upgradeCourse.deleteResourceFile(Integer.parseInt(command[1]));
					break;
				case "sendEmailToCourseStudents":
					result = email.allertAllCourseStudents(command[1], command[2], Integer.parseInt(command[3]), command[4], command[5]);
					break;
				case "sendEmailToASelectionOfStudents":
					@SuppressWarnings("unchecked") Collection<Integer> students = (Collection<Integer>) this.objectInput.readObject();
					result = email.allertASelectionOfStudents(command[1], command[2], students, command[3], command[4]);
					break;
				case "viewTotalNumberConnectedUsers":
					result = statistic.getTotalNumberConnectedUsers();
					break;
				case "usersHaveDownloadOneOrMoreResources":
					if(statistic == null)		
					result = statistic.getTotalNumberOfUsersHaveDownloadOneOrMoreResoursesInTimeIntervals(Timestamp.valueOf(command[1]), Timestamp.valueOf(command[2]));
					break;
				case "averageConnectionTime":
					result = statistic.getAverageConnectionTimeOfStudentsToTheCoursePage();
					break;
				default:
					logger.debug("Comando ricevuto non riconosciuto, LectureController: "+input);
					result = negative;
				}	
				
				view = null;
				execute = null;
				upgradeCourse = null;
				email= null;
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
				isActive = false;
				logger.debug("Cliente si è disconesso: "+e);
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
