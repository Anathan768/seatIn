package seatInAdmin;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import seatInServer.JDBC.Beans.Admin;
import seatInServer.JDBC.Beans.Lecture;
import seatInServer.JDBC.Beans.Student;
import seatInServer.JDBC.Beans.User;

public class BasicCSVReader {

   protected BasicCSVReader(){

    }

   protected Collection<User> readFile(String path) {
        Collection<User> users = new ArrayList<>();
        CSVParser parser = null;
        try {
            Reader input = Files.newBufferedReader(Paths.get(path));
            parser = new CSVParser(input, CSVFormat.EXCEL
                    .withHeader("Surname", "Name", "Email", "Type", "Department", "DegreeCourse", "CareerStatus")
                    .withIgnoreHeaderCase()
                    .withTrim());
        }catch (IOException e){
            e.printStackTrace();
        }
        User user = null;
        int count = 0;
        for(CSVRecord r: parser) {
            if(count != 0) {
                String surname = r.get("Surname");
                String name = r.get("Name");
                String email = r.get("Email");
                String type = r.get("Type");
                String department = r.get("Department");
                String degreeCourse = r.get("DegreeCourse");
                String careerStatus = r.get("CareerStatus");

                if(type.equals("student") || type.equals("null")) {
                    Student student = new Student();
                    String t = "student";
                    student.setUserType(t);
                    student.setDegreeCourse(degreeCourse);
                    student.setCareerStatus(careerStatus.toCharArray()[0]);
                    user = student;

                }
                if(type.equals("lecture")) {
                    Lecture lecture = new Lecture();
                    String t = "lecturer";
                    lecture.setUserType(t);
                    lecture.setDepartment(department);
                    user = lecture;

                }
                if(type.equals("admin")) {
                    Admin admin = new Admin();
                    String t = "admin";
                    admin.setUserType(t);
                    admin.setDepartment(department);
                    user = admin;

                }
                user.setSurname(surname);
                user.setName(name);
                user.setEmail(email);
                users.add(user);
            }else
                count = 1;
        }
        return users;
    }
}
