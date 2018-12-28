package seatInServer.Services;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;

import seatInServer.JDBC.ExecuteSelect;
import seatInServer.JDBC.Beans.Course;

public class AdminStatistic extends LectureStatistic{

	private ExecuteSelect execute;
	
	public AdminStatistic() {
		execute = ExecuteSelect.getInstance();
	}
	/**
	 * Mostra il numero complessivo accessi per corso in una fascia temporale.
	 * @param start inizio periodo temporale.
	 * @param end fine periodo temporale.
	 * @return int -- numero accessi.
	 */
	public int getTotalNumberOfAccessesPerCourseInTimeBand(Timestamp start, Timestamp end) {
		return execute.selectTotalNumberOfAccessesPerCourseInTimeBand(start, end);
	}
	/**
	 * Mostra il tempo medio di connesione degli studenti per ogni corso.
	 * @return HashMap -- Contenente come chiave il corso e come valore il tempo medio di connessione per quel corso. 
	 */
	public HashMap<Course, Double> getAverageConnectionTimeOfStudentsForEachCourse() {

		Collection<Course> courses = execute.selectAllCourses();
		
		HashMap<Course, Double> result = new HashMap<>();
		
		for(Course c: courses) {
			double courseStatistic = execute.selectAverageConnectionTimeOfStudentsPerCourse(c.getId());
			result.put(c, courseStatistic);
		}
				
		return result;
	}
	/**
	 * Mosrtra il numero complessivo di download per ogni corso.
	 * @return HashMap -- Contenente come chiave il corso e come valore il numero complessivo di download per quel corso.
	 */
	public HashMap<Course, Integer> getTotalNumberDownloadsForEachCourse() {
		Collection<Course> courses = execute.selectAllCourses();
		
		HashMap<Course, Integer> result = new HashMap<>();
		
		for(Course c: courses) {
			int numberDownloads = execute.selectTotalNumberDownloadsForEachCourse(c.getId());
			result.put(c, numberDownloads);
		}
		return result;
	}
}
