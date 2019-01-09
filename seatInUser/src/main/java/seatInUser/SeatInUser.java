package seatInUser;

import static seatInUser.Items.setServerConnection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import seatInUser.GUI.GUI;

public class SeatInUser {
	private InetAddress addr;
	private Socket socket;
	private final int PORT = 8080;
	
	private SeatInUser() throws IOException {
		addr = InetAddress.getByName(null);
		socket = new Socket(addr, PORT);
		Proxy connection = new Proxy(socket);
		setServerConnection(connection);
		new GUI();
	}
	
	public static void main(String[] args) throws IOException {
		new SeatInUser();
	}
}