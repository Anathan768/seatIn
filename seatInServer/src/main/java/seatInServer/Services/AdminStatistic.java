package seatInServer.Services;

import java.sql.Timestamp;

import seatInServer.JDBC.ExecuteSelect;

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
	 * Mostra il tempo medio di connesione degli studenti per corso.
	 * @param courseId --l'id del corso.
	 * @return double il tempo medio di connessione.
	 */
	public double getAverageConnectionTimeOfStudentsPerCourse(int courseId) {
		return execute.selectAverageConnectionTimeOfStudentsPerCourse(courseId);
	}
	/**
	 * Mosrtra il numero complessivo di download per corso.
	 * @param courseId -- l'id del corsi
	 * @return int -- numero di download.
	 */
	public int getTotalNumberDownloadsPerCourse(int courseId) {
		return execute.selectTotalNumberDownloadsForEachCourse(courseId);
	}
}
