package seatInServer.JDBC.ConnectionPool;

import static seatInServer.Utilities.Utilities.getCurrentClassName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
	
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	private static Configuration config;
	//= new Configuration("localhost","dbSeatIn","postgres","13579sorc768");
	private static ConnectionPool pool = new ConnectionPool();
	private static LinkedBlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(100);
	private int usedConnections = 0;
	

	private ConnectionPool() {
		
	}
	
	public static ConnectionPool getInstance() {
		if(pool == null)
			pool = new ConnectionPool();
		
		return pool;
	}
	/**
	 * Imposta i dati necessari per la connessione al data base.
	 * @param db_host -- host del data base.
	 * @param db_username -- nome profilo del data base.
	 * @param db_password -- password del profilo utente del db.
	 * @throws SQLException se i dati inseriti sono sbagliati. 
	 */
	public static void setConfigurations(String db_host, String db_username, String db_password) throws SQLException {
		config = new Configuration(db_host, "template1", db_username, db_password);
		Connection testConn = openConnection();
		config = new InitDataBase(db_host, db_username, db_password).createIfNotExist(testConn);
	}
	
	private static Connection openConnection() throws SQLException{
		Connection conn = null;
			conn = DriverManager.getConnection(config.getURL(), config.getUsername(), config.getPassword());
		return conn;
	}
	/**
	 * Prende il riferimento ad una connessione e la restituisce al chiamante.
	 * @return una connessione al data base
	 */
	public synchronized Connection getConnection() {
		Connection newConn = null;
		
		try {
			while(true) {
				if(usedConnections < 100) {
					if(connections.size() >= 1) {
						newConn = connections.take();
						logger.debug("Get connection...");
					}else {
						newConn = openConnection();
						logger.debug("Open connection...");
					}
					usedConnections++;
					break;
				}else {	
					wait();					
				}
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
	public synchronized void putbackConnection(Connection conn) {
		try {
			connections.add(conn);
			logger.debug("Putback connection");
			usedConnections--;
			notify();
		}catch(NullPointerException e) {
			logger.debug("Errore restituzione connessione al ConnctionPool: "+e);
		}
	}	
}
