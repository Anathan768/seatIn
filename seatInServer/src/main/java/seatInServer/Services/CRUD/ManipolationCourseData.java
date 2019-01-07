package seatInServer.Services.CRUD;

import static seatInServer.Utilities.Utilities.getCurrentClassName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.ExecuteSelect;
import seatInServer.JDBC.ExecuteUpdate;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;

public class ManipolationCourseData {

	private ExecuteUpdate update;
	private ExecuteSelect select;
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	
	public ManipolationCourseData() {
		update = ExecuteUpdate.getInstance();
		select = ExecuteSelect.getInstance();
	}
	/**
     * Visualizza i dati relativi ad un determinato corso.
     * @param courseId -- l'id del corso.
     * @return Course -- contenente tutte le informazioni relative ad un determinato corso.
     */
	public Course viewCourseData(int courseId) {
		return select.selectCourseById(courseId);
	}
	
	/**
	 * Aggiunge una sezione/sotto-sezione ad un corso.
	 * @param section contenente tutte le info relative alla sezione.
	 * @return int -- l'id, assegnato dal databse, alla sezione creata.
	 */
	public int addSectionToCourse(Section section) {
		return update.addSectionToCourse(section);
	}
	
	/**
	 * Aggiunge una cartella in una sezione.
	 * @param resource -- l'oggetto contenente tutte le informazioni relative ad una cartella.
	 * @return int -- l'id, assegnato dal database, della risorsa/cartella creata.
	 */
	public int addResourseToCourseSection(Resource resource) {
		return update.addResourceToCourseSection(resource);
	}
	/**
	 * Aggiunge un file in una cartella.
	 * @param resourceId l'id della cartella.
	 * @param fileName l'oggetto contenente le informazioni relative al file da caricare.
	 * @param fileContent il contenuto del file passato.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public int addFileToCourseResource(int resourceId, String fileName, byte[] fileContent) {
		String path = "src/CourseResources/"+resourceId; 
		File file = null;
		
		try {
			file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
			path += "/"+fileName;
			file = new File(path);
			Files.write(file.toPath(), fileContent);
		} catch (IOException e) {
			logger.debug("Esecuzione addFileToResource fallita:" +e);
		}
		return update.addFileToCourseResource(resourceId, path);
	}
	/**
	 * Modifica le informazioni relative ad un determinato corso.
	 * Il metodo può essere utilizzato solo dagli utenti di tipo Admin.
	 * @param course contenente informazioni da modificare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String modifyCourseInfo(Course course) {
		int degreeCourse = select.selectDegreeCourseIdByName(course.getDegreeCourse());
		logger.debug("Init modify");
		return update.modifyCourseInfo(course, degreeCourse);
	}
	/**
	 * Esegue la modifica delle informazioni relative ad una sezione/sotto-sezione.
	 * @param section -- l'oggetto contenente tutte le informazione relative alla sezione da modificare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String modifyCourseSection(Section section) {
		return update.modifyCourceSection(section);
	}
	/**
	 * Esegue la modifica di una cartella(resource) di una sezione.
	 * @param resource l'oggetto contente tutte le informazione relative alla risorsa da modificare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String modifySectionResource(Resource resource) {
		return update.modifySectionResource(resource);
	}
	/**
	 * Esegue la cancellazione di una sezione.
	 * @param sectionId -- l'id della sezione da cancellare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String deleteCourseSection(int sectionId) {
		return update.deleteCourseSection(sectionId);
	}
	/**
	 * Esegue la cancellazione di una risorsa/cartella.
	 * @param resourceId -- l'id della risorsa da cancellare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String deleteSectionResource(int resourceId) {
		return update.deleteSectionResource(resourceId);
	}
	
	/**
	 * Esegue la cancellazione di un file.
	 * @param fileId -- l'id del file da cancellare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String deleteResourceFile(int fileId) {
		return update.deleteResourceFile(fileId);
	}
}
