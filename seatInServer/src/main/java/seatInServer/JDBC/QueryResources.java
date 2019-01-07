package seatInServer.JDBC;

import static seatInServer.Utilities.Utilities.getCurrentClassName;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.ConnectionPool.ConnectionPool;

/*
 * Segeunte classe si occupa della chiusare di tutte le risorse usate durante esecuzione delle query.
 */
public class QueryResources {
		
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	private static ConnectionPool pool = ConnectionPool.getInstance();
	
	private QueryResources() {
		
	}
			
	protected static void closeResources(Statement st, Connection conn) {
		try {
			st.close();
			pool.putbackConnection(conn);
		}catch(SQLException e) {
			logger.debug("Errore: chiusura \"Statement\": "+conn+"\t "+st+"\n"+e);
		}	
	}
	
	protected static void closeResources(ResultSet rs, Statement st, Connection conn) {
		try {
			rs.close();
			closeResources(st, conn);			
		}catch(SQLException e) {
			logger.debug("Errore: chiusura \"ResultSet\": "+conn+"\t"+rs+"\n"+e);
		}
	}
}
