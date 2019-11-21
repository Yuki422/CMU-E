/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;

import static cmu.SetDB.printSQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import static cmu.SetDB.printSQLException;import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * This class is used for storing information of each visit of students.
 * @author ppsra
 */
public class Visit {
    public SimpleStringProperty studentID;
    public SimpleStringProperty visitID;
    public SimpleStringProperty reason;
    java.sql.Timestamp time;
    public Visit()
    {}
    
    /**
     * Constructs Visit object and set fields according to arguments.
     * @param a String object of studenID
     * @param b String object of visitID
     * @param c String object of reason
     * @param time Timestamp objedt of visiting time
     */
    public Visit(String a, String b, String c, Timestamp time) {
      
        this.studentID = new SimpleStringProperty(a);
        this.visitID = new SimpleStringProperty(b);
        this.reason = new SimpleStringProperty(c);
        this.time = time;
    }
    
    /**
     * Returns String object of studentID.
     * @return String object of studentID
     */
    public String getStudentID(){
        return studentIDProperty().get();
    }

    /**
     * Returns String object of visiting reason.
     * @return Srring object of reason.
     */
    public String getReason()
   {
       return reasonProperty().get();
   }

    /**
     * Replaces reasonProperty field value with argument.
     * @param n String object of reason
     */
    public void setReason(String n)
    {
        reasonProperty().set(n);
    }
   
    /**
     * Replaces studentID field value with argument.
     * @param n String object of studentID
     */
    public void setStudentID(String n)
    {
        studentIDProperty().set(n);
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
     * Returns StringProperty object of reason
     * @return StringProperty object of reason
     */
    public StringProperty reasonProperty()
    {
        return reason;
    }
}
