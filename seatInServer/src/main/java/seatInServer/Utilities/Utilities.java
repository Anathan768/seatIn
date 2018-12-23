package seatInServer.Utilities;

import java.sql.Timestamp;
import java.time.Instant;

public class Utilities {
		
	private Utilities() {
		
	}
	
	/**
	 * Il metodo separa la stringa ricevutain input che rapresenta il comando e i suoi parametri separati da "/".
	 * @param arg Stinga
	 * @return String[] contenente il comando con gli eventuali parametri
	 * @exception NullPointerException se arg è null
	 */
	public static final String[] requestParser(String arg){
		
		if(arg == null) throw new NullPointerException();
		
		String[] result = null;
		result = arg.split("/");
		
		return result;
	}
	
	/**
	 * Il seguente metodo serve per il framework log4j.
	 * @return il nome della classe chiamante
	 */
	public static final String getCurrentClassName() {
		
		try {
			throw new RuntimeException();
		}catch(RuntimeException e) {
			return e.getStackTrace()[1].getClassName();
		}
	}
	/**
	 * Indentifica la data e l'ora corrente.
	 * @return la data e ora corrente.
	 */
	public static final Timestamp getCurrentDateAndTime() {
		Timestamp fullDate = new Timestamp(System.currentTimeMillis()); 
		Instant instant = fullDate.toInstant();
		Timestamp fullDateFormat = Timestamp.from(instant);
		return fullDateFormat;
	}
}
