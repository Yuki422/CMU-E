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
 * This class is used for storing information of each student.
 * @author ppsra
 */
public class Student {
   public SimpleStringProperty studentID;
   public SimpleStringProperty name;
   public SimpleStringProperty program;
   public SimpleStringProperty address;
   public SimpleStringProperty gender;
   public LocalDate DOB;

   /**
    * Constructs new Student object and set field values according to arguments.
    * @param a studentID in String object (formatted in S245****)
    * @param b name of student in String object
    * @param c program of student in String object
    * @param d gender of student in String object (male / female)
    * @param e date of birth of student in LocalDate object
    */
    public Student(String a,String b,String c,String d,LocalDate e)
    {
        this.studentID = new SimpleStringProperty(a);
        this.name = new SimpleStringProperty(b);
        this.program = new SimpleStringProperty(c);
        this.gender = new SimpleStringProperty(d);
        this.DOB = e;
    }
    

    /**
     *Returns studentID in String object
     * @return String object of studentID
     */

    public String getStudentID(){
        return studentIDProperty().get();
    }

    /**
     *Returns name in String object
     * @return String object of name
     */
    public String getName(){
        return name.get();
    }

    /**
     *Returns program in String object
     * @return String object of program
     */
    public String getprogram(){
        return program.get();
    }

    /**
     *Returns gender in String object
     * @return String object of gender 
     */
    public String getgender(){
        return gender.get();
    }

    /**
     * Returns date of birth in LocalDate object
     * @return LocalDate object of date of birth
     */
    public LocalDate getDOB(){
        return DOB;
    }

    /**
     * Replaces value of studentID property with argument
     * @param n String object of studentID
     */
    public void setStudentID(String n)
    {
        studentIDProperty().set(n);
    }

    /**
     * Replaces value of name property with argument
     * @param n String object of name
     */
    public void setName(String n)
    {
        name.set(n);
    }

    /**
     * Replaces value of program property with argument 
     * @param n String object of program
     */
    public void setprogram(String n)
    {
        program.set(n);
    }

    /**
     * Replaces value of program property with argument
     * @param n
     */
    public void setgender(String n)
    {
        gender.set(n);
    }

    /**
     * Replaces value of date of birth with argument
     * @param n LocalDate object of date of birth
     */
    public void setDOB(LocalDate n)
    {
        DOB = n;
    }

    /**
     * Returns StringProperty object of studentID
     * @return StringProperty object of studentID
     */
    public StringProperty studentIDProperty()
    {
        return studentID;
    }

    /**
     * Returns StringProperty object of name
     * @return StringProperty object of name
     */
    public StringProperty nameProperty()
    {
        return name;
    }

    /**
     * Returns StringProperty object of program
     * @return StringProperty object of program
     */
    public StringProperty programProperty()
    {
        return program;
    }

    /**
     * Returns StringProperty object of gender
     * @return StringProperty object of gender
     */
    public StringProperty genderProperty()
    {
        return gender;
    }
}
