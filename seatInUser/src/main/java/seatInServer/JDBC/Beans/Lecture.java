package seatInServer.JDBC.Beans;

import java.io.Serializable;

public class Lecture extends User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String department;
	
	public Lecture() {
		
	}
	
	public Lecture(int id) {
		super(id);	
	}		
	
	public String getDepartment() {
		return this.department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
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
				"ActivationCode: "+this.getActivationCode()+"\n"+
				"Department: "+this.getDepartment()+"\n";
		
		return text;
	}
}
