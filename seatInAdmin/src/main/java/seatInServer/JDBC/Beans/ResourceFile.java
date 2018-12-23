package seatInServer.JDBC.Beans;

import java.io.Serializable;

public class ResourceFile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int resourceId;
	
	public ResourceFile() {
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getResourceId() {
		return this.resourceId;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setResourceId(int id) {
		this.resourceId = id;
	}
	
	public String toString() {
		String text = "Id: "+this.getId()+"\n"+
					  "File name: "+this.getName()+"\n"+
					  "Resource id: "+this.getResourceId()+"\n";
		return text;
	}
	
}
