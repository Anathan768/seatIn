package seatInServer;

import seatInServer.GUI.ServerGUI;

public class Start {
	
	public Start() {
		
	}	
	/**
	 * Esegue l'avvio del server.
	 * @param args arguments
	 */
	public static void main(String[] args) {
		new ServerGUI().initializationGUI();		
	}

}
