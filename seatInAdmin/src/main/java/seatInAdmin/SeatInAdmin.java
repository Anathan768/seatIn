package seatInAdmin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static seatInAdmin.Items.setServerConnection;

public class SeatInAdmin {

	private InetAddress addr;
	private Socket socket;
	private final int PORT = 8080;
	
	private SeatInAdmin() throws IOException {
		addr = InetAddress.getByName(null);
		socket = new Socket(addr, PORT);
		System.out.println("Connect to address:"+addr+":"+PORT);
		Proxy connection = new Proxy(socket);
		setServerConnection(connection);
		//TODO creare l'oggetto relativo alla finestra del login
	}
	
	public static void main(String[] args) throws IOException {
		new SeatInAdmin();
	}

}
