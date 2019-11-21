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
 *
 * @author ppsra
 */
public class Visit {
    public SimpleStringProperty studentID;
    public SimpleStringProperty visitID;
    public SimpleStringProperty reason;
    java.sql.Timestamp time;
    public Visit()
    {}
    
    public Visit(String a, String b, String c, Timestamp time) {
      
        this.studentID = new SimpleStringProperty(a);
        this.visitID = new SimpleStringProperty(b);
        this.reason = new SimpleStringProperty(c);
        this.time = time;
    }
    
   public String getStudentID(){
        return studentIDProperty().get();
    }
   public String getReason()
   {
       return reasonProperty().get();
   }
   public void setReason(String n)
    {
        reasonProperty().set(n);
    }
   
   public void setStudentID(String n)
    {
        studentIDProperty().set(n);
    }
   
   public StringProperty studentIDProperty()
    {
        return studentID;
    }
   
    public StringProperty reasonProperty()
    {
        return reason;
    }
}
