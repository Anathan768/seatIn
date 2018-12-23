package seatInServer;

import static seatInServer.Utilities.Utilities.getCurrentClassName;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.Controllers.Skeleton;

public class SeatInServer {
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	private ServerSocket server;
	
	public SeatInServer() {
		initServer();
		waitingConnections();
	}
	
	private void initServer() {
		try {
			server = new ServerSocket(8080);
			logger.info("Server started: "+server);
		} catch (IOException e) {
			logger.debug("Creazione SocketServer fallito: "+e);
		}		
	}
	

	private void waitingConnections() {
		
		while(true) {
			try {
				Socket clientSocket = server.accept();
				logger.info("Connesione del cliente: "+clientSocket+"\n");
				new Thread(new Skeleton(clientSocket)).start();
			} catch (Exception e) {
				logger.debug("Connessione del cliente fallita: "+e);
				e.printStackTrace();
				break;
			}
		}
		
	}
	
	

}
