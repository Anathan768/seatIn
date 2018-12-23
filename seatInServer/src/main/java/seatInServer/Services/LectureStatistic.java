package seatInServer.Services;

import java.sql.Timestamp;

import seatInServer.JDBC.ExecuteSelect;

public class LectureStatistic {

	private ExecuteSelect execute;
	
	public LectureStatistic() {
		this.execute = ExecuteSelect.getInstance();
	}
	
	/**
	 * Mostra il numero complessivo di untenti che stanno visualizzando/integrando con il contenuto
	 * del corso.
	 * @return int
	 */
	public int getTotalNumberConnectedUsers() {
		return execute.selectTotalNumberConnectedUsers();
	}
	/**
	 * Mostra il numero complessivo di utenti che hanno effettuato il download di una o più
	 * risorse in intervalli temporali.
	 * @param start -- inizio dell'intervallo.
	 * @param end -- fine dell'intervallo.
	 * @return int -- numero utenti.
	 */
	public int getTotalNumberOfUsersHaveDownloadOneOrMoreResoursesInTimeIntervals(Timestamp start, Timestamp end) {
		return execute.selectTotalNumberOfUsersHaveDownloadOneOrMoreResoursesInTimeIntervals(start, end);
	}
	/**
	 * Derivare il tempo medio di connessione degli studenti alle pagine del corso.
	 * @return double 
	 */
	public double getAverageConnectionTimeOfStudentsToTheCoursePage() {
		return execute.selectAverageConnectionTimeOfStudentsToTheCoursePage();
	}
}
