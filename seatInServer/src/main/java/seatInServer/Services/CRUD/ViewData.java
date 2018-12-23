package seatInServer.Services.CRUD;

import static seatInServer.Utilities.Utilities.getCurrentDateAndTime;

import java.sql.Timestamp;
import java.util.Collection;

import seatInServer.JDBC.ExecuteSelect;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInServer.JDBC.Beans.User;

public class ViewData {
	
	private ExecuteSelect executeSelect;
	
	public ViewData() {
		this.executeSelect = ExecuteSelect.getInstance();
	}
	/**
	 * Estrae i dati relativi ad un determinato utente. 
	 * @param userId -- l'id dell'utente.
	 * @return User contenente tutti i dati relativi all'utente. 
	 */
	public User viewProfileData(int userId) {
		return executeSelect.selectUserById(userId);
	}
	/**
	 * Seleziona tutti i corsi seguiti/sostenuti da uno Student/Lecture
	 * @param userId -- l'id dell'utente, può essere uno Studente, Professore o Admin.
	 * @return Collection contenente tutte le informazioni relative ai corsi seguiti/tenuti di una determinato utente.
	 */
	public Collection<Course> viewAllCourses(int userId) {
		Collection<Course> courses = executeSelect.selectAllCoursesLiableByUser(userId);
		return courses;
	}
	/**
	 * Seleziona tutte le sezioni di un determinato corso prendendo in considerazione i modificatori di visibilità,
	 * e l'itervalli di visibilità impostati.
	 * @param courseId -- l'id del corso.
	 * @param isLecture -- indica se il metodo viene invocato da un professore o studente.
	 * Nel caso sia un professore, esso deve vedere il contenuto del corso indipendentemente dai modificatori di visibilità impostati.
	 * I modificatori possono essere: "Public" - visibile a tutti, o "Private" visibile solo alle persone titolari di un determinato corso. 
	 * Nel caso di uno studente, devono essere visualizzati solo i contenuti con modificatori di visibilità "public".
	 * @return Collection contenente tutte le informazioni relative alle sezione e al loro contenuto.
	 */
	public Collection<Section> viewCourseContent(int courseId, boolean isLecture){
		Timestamp now = getCurrentDateAndTime();
		long thisMoment = now.getTime();
		
		Collection<Section> sectionsOfCourse = executeSelect.selectAllCourseSections(courseId);
		
		/*Se la richiesta arriva da parte di un professore/admin che tiene il corso,
		 *allora vengono visualizzate tutte le sezione indipendentemente dai modificatori di visibilità,
		 *o intervalli temporali impostati.
		 *Se invece la richiesta arriva da parte di uno studente, allora vengono effettuati tutti i controlli
		 *per visualizzare le sezioni in modo corretto. 
		 */
		if(!isLecture) {
			for(Section section: sectionsOfCourse) {
				if(section.isActive()) {
					long start = section.getActiveFrom().getTime();
					long end = section.getActiveTo().getTime();
					
					if(thisMoment >= start && thisMoment <= end) {
						continue;
					}else {
						//sectionsOfCourse.remove(section);
					}
				}else {
					sectionsOfCourse.remove(section);
				}
			}
		}
		return sectionsOfCourse;
	}
}
