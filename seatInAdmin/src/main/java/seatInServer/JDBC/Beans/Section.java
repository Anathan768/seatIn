package seatInServer.JDBC.Beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

public class Section implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Integer parentId;
	private String title;
	private String description;
	private boolean active;
	private Timestamp active_from;
	private Timestamp active_to;
	private int courseId;
	private Collection<Resource> resources;
	
	public Section() {
		
	}
		
	public int getId() {
		return this.id;
	}
	
	public Integer getParentId() {
		return this.parentId;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public Timestamp getActiveFrom() {
		return this.active_from;
	}
	
	public Timestamp getActiveTo() {
		return this.active_to;
	}
	public int getCourseId() {
		return this.courseId;
	}
	
	public Collection<Resource> getResources(){
		return this.resources;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setActiveFrom(Timestamp active_from) {
		this.active_from = active_from;
	}
	
	public void setActiveTo(Timestamp active_to) {
		this.active_to = active_to;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public void setResources(Collection<Resource> res) {
		this.resources = res;
	}
	
	public String toString() {
		
		String text = "Id: "+this.getId()+"\n"+
					  "ParentId:  "+this.getParentId()+"\n"+
					  "Title: "+this.getTitle()+"\n"+
					  "Description: "+this.getDescription()+"\n"+
					  "Active: "+this.isActive()+"\n"+
					  "Active from: "+this.getActiveFrom()+"\n"+
					  "Active to: "+this.getActiveTo()+"\n";
		
		return text;
	}
}
