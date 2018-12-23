package seatInServer.JDBC.Beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class Student extends User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String degreeCourse;
	private Timestamp regisrationYear;
	private char careerStatus;
	
	public Student() {
		
	}
	
	public Student(int id) {
		super(id);
	}
	
	public String getDegreeCourse() {
		return this.degreeCourse;
	}
	
	public void setDegreeCourse(String degreeCourse) {
		this.degreeCourse = degreeCourse;
	}
	
	public Timestamp getRegistrationYear() {
		return this.regisrationYear;
	}
	
	public void setRegistrationYear(Timestamp year) {
		this.regisrationYear = year;
	}
	
	public char getCareerStatus() {
		return this.careerStatus;
	}
	
	public void setCareerStatus(Character string) {
		this.careerStatus = string;
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
				"Degree Course: "+this.getDegreeCourse()+"\n"+
				"Registration Date: "+this.getRegistrationYear()+"\n"+
				"Career Status: "+this.getCareerStatus()+"\n";
		
		return text;
	}
		
}
