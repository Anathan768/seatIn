package seatInServer.JDBC.Beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class Course implements Beans, Serializable{

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private Timestamp activationDate;
    private String degreeCourse;
    private String description;
    private boolean isActive;

    public Course() {

    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Timestamp getActivationDate() {
        return this.activationDate;
    }

    public String getDegreeCourse() {
        return this.degreeCourse;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActivationDate(Timestamp date) {
        this.activationDate = date;
    }

    public void setDegreeCourse(String course) {
        this.degreeCourse = course;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public String toString() {
        String text ="";

        text += "Course ID: "+this.getId()+"\n"+
                "Course Name: "+this.getName()+"\n"+
                "Activation Date: "+this.getActivationDate()+"\n"+
                "Degree Course: "+this.getDegreeCourse()+"\n"+
                "Desctiption: "+this.getDescription()+"\n"+
                "Active: "+this.isActive()+"\n";

        return text;
    }

}
