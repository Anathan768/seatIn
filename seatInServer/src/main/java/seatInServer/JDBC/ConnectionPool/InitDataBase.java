package seatInServer.JDBC.ConnectionPool;

public class InitDataBase {

	private String db_host;
	private String db_username;
	private String db_password;
	
	
	protected InitDataBase(String host, String username, String password) {
		this.db_host = host;
		this.db_username = username;
		this.db_password = password;
	}
	
	protected Configuration createIfNotExist() {
		return null;
	}

}


