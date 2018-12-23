package seatInServer.JDBC.ConnectionPool;
/*
 * Sguente classe contiene tutte le informazioni necessarie per stabilire le connessioni al Data Base
 */
public class Configuration {
	
	private final String DB_HOST;
	private final String DB_NAME = "dbSeatIn";
	private final String DB_USERNAME;
	private final String DB_PASSWORD;
	private final String URL;
	
	protected Configuration(String db_host, String db_username, String db_password) {
		this.DB_HOST = db_host;
		this.DB_USERNAME = db_username;
		this.DB_PASSWORD = db_password;
		this.URL = "jdbc:postgresql://"+DB_HOST+":5432/"+DB_NAME;
	}
	
	protected String getHost() {
		return this.DB_HOST;
	}
	
	protected String getDB_Name() {
		return this.DB_NAME;
	}
	
	protected String getUsername() {
		return this.DB_USERNAME;
	}
	
	protected String getPassword() {
		return this.DB_PASSWORD;
	}
	
	protected String getURL() {
		return this.URL;
	}
}
