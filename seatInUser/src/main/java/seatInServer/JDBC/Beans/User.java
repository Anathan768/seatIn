package seatInServer.JDBC.Beans;

import java.io.Serializable;

public class User implements Beans, Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String surname;
	private String name;
	private String email;
	private String password;
	protected String userType;
	private boolean isActive;
	private int wrongLogins;
	private String activationCode;
	
	public User() {
		
	}
	
	public User(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUserType() {
		return this.userType;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public int getWrognLogins() {
		return this.wrongLogins;
	}	
	
	public String getActivationCode() {
		return this.activationCode;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
		
	public void setName(String name) {
		this.name = name;
	}
		
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserType(String type) {
		this.userType = type;
	}
	
	public void setActive(boolean active) {
		this.isActive = active;
	}
	
	public void setWrongLogins(int number) {
		this.wrongLogins = number;
	}
	
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}	

	public String toString() {
		String text = null;
		
		text = 	"Matricola: "+this.getId()+"\n"+
				"Tipo di utente: "+this.getUserType()+"\n"+
				"Cognome: "+this.getSurname()+"\n"+
				"Nome: "+this.getName()+"\n"+
				"Email: "+this.getEmail()+"\n"+
				"Password: "+this.getPassword()+"\n"+
				"Active: "+this.isActive()+"\n"+
				"ActivationCode: "+this.getActivationCode()+"\n";
		
		return text;
	}	
}
