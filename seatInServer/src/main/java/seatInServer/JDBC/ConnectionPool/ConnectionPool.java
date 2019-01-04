package seatInServer.JDBC.ConnectionPool;

import static seatInServer.Utilities.Utilities.getCurrentClassName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.ExecuteUpdate;

public class ConnectionPool {
	
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	private static Configuration config;
	private static LinkedBlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(100);

	private ConnectionPool() {
		
	}
	
	public static void setConfigurations(String db_host, String db_username, String db_password) throws SQLException {
		//config = new InitDataBase(db_host, db_username, db_password).createIfNotExist();
		config = new Configuration(db_host, "dbSeatIn", db_username, db_password);
		openConnection();
		ExecuteUpdate.executeCreationTables();
	}
	
	private static Connection openConnection() throws SQLException{
		Connection conn = null;
			logger.debug("Inizializzazione nuova connessione al database.");
			//setConfigurations("localhost","postgres","13579sorc768");//TODO da togliere dopo la fase di testing
			conn = DriverManager.getConnection(config.getURL(), config.getUsername(), config.getPassword());
		return conn;
	}
	/**
	 * Prende il riferimento ad una connessione e la restituisce al chiamante.
	 * @return una connessione al data base
	 */
	public static synchronized Connection getConnection() {
		Connection newConn = null;
		
		try {
			if(connections.size() == 0) {
				newConn = openConnection();
			}else {
				newConn = connections.take();
			}
		} catch (SQLException e) {
			logger.debug("Errore: connessione non trovata nella Connection Pool: "+e.getMessage());
		}catch(InterruptedException e) {
			logger.debug("Inizializzazione connesione al Data Base fallita: "+e.getMessage());
		}
		return newConn;
	}
	/**
	 * Rimette la connessione nel ConnectionPool
	 * @param conn Connections
	 * @throws NullPointerException se il riferimento passato è null
	 */
	public static synchronized void putbackConnection(Connection conn) {
		try {
			connections.add(conn);
		}catch(NullPointerException e) {
			logger.debug("Errore restituzione connessione al ConnctionPool: "+e);
		}
	}	
}
