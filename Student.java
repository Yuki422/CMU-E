/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;

import static cmu.SetDB.printSQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author ppsra
 */
public class Student {
   public SimpleStringProperty studentID;
   public SimpleStringProperty name;
   public SimpleStringProperty program;
   public SimpleStringProperty address;
   public SimpleStringProperty gender;
   public LocalDate DOB;

    public Student(String a,String b,String c,String d,LocalDate e)
    {
        this.studentID = new SimpleStringProperty(a);
        this.name = new SimpleStringProperty(b);
        this.program = new SimpleStringProperty(c);
        this.gender = new SimpleStringProperty(d);
        this.DOB = e;
    }
    
    
    public String getStudentID(){
        return studentIDProperty().get();
    }
    public String getName(){
        return name.get();
    }
    public String getprogram(){
        return program.get();
    }
    public String getgender(){
        return gender.get();
    }
    public LocalDate getDOB(){
        return DOB;
    }
    public void setStudentID(String n)
    {
        studentIDProperty().set(n);
    }
    public void setName(String n)
    {
        name.set(n);
    }
    public void setprogram(String n)
    {
        program.set(n);
    }
    public void setgender(String n)
    {
        gender.set(n);
    }
    public void setDOB(LocalDate n)
    {
        DOB = n;
    }
    public StringProperty studentIDProperty()
    {
        return studentID;
    }
    public StringProperty nameProperty()
    {
        return name;
    }
    public StringProperty programProperty()
    {
        return program;
    }
    public StringProperty genderProperty()
    {
        return gender;
    }
}
