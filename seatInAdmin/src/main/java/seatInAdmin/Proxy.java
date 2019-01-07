package seatInAdmin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Proxy {
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public Proxy(Socket socket) throws IOException {
		this.output = new ObjectOutputStream(socket.getOutputStream());
		this.input = new ObjectInputStream(socket.getInputStream());
	}
	
	/**
	 * Il seguente metodo si occupa del inviare le richieste al server.
	 * @param command rappresenta il comando/richiesta.
	 */
	protected void sendCommand(Object command) {
		try {
			output.writeObject(command);
			output.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Il seguente metodo si occupa del ricevere la risposta mandata dal server.
	 * @return Object che può essere qualsiasi oggetto.
	 * Il cast viene eseguito nel locale(nel posto da cui viene invocato il seguente metodo) al tipo al tipo aspettato.
	 */
	protected Object getResult() {
		Object result = null;
		
		try {
			result = input.readObject();
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Chiusura connessione con il server.
	 */
	protected void close() {
		try {
			this.output.close();
			this.input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
