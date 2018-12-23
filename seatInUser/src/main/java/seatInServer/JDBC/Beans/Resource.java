package seatInServer.JDBC.Beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

public class Resource implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String description;
	private boolean isActive;
	private Timestamp active_from;
	private Timestamp active_to;
	private int moduleId;
	private Collection<ResourceFile> files;
	
	public Resource() {
		
	}
		
	public int getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public Timestamp getActiveFrom() {
		return this.active_from;
	}
	
	public Timestamp getActiveTo() {
		return this.active_to;
	}
	
	public int getModuleId() {
		return this.moduleId;
	}
	
	public Collection<ResourceFile> getFiles(){
		return this.files;
	}
	
	public void setId(int id) {
		this.id= id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setActive(boolean active) {
		this.isActive = active;
	}
	
	public void setActiveFrom(Timestamp from) {
		this.active_from = from;
	}
	
	public void setActiveTo(Timestamp to) {
		this.active_to = to;
	}
	
	public void setModuleId(int id) {
		this.moduleId = id;
	}
	
	public void setFiles(Collection<ResourceFile> files) {
		this.files = files;
	}
	
	public String toString() {
		String text = "Resource id: "+this.getId()+"\n"+
					  "Title: "+this.getTitle()+"\n"+
					  "Description: "+this.getDescription()+"\n"+
					  "Active: "+this.isActive()+"\n"+
					  "Active From: "+this.getActiveFrom()+"\n"+
					  "Active To: "+this.getActiveTo()+"\n"+
					  "Module id: "+this.getModuleId()+"\n";
		return text;
	}
}
