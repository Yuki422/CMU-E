/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author apple
 */
public class Dashclass {
 private SimpleStringProperty studentID;
 public void setStudentID(String value) { StudentIDProperty().set(value); }
     public String getStudentID() { return StudentIDProperty().get(); }
     public SimpleStringProperty StudentIDProperty() {
         if (studentID == null) studentID = new SimpleStringProperty(this, "studentID");
         return studentID;
     }
     
}
