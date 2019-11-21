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
public class FrequencyReportRow {
     //studentId is declared as simplestringproperty and its getter, setter and property methods are written
     private SimpleStringProperty studentID;
     public void setStudentID(String value) { StudentIDProperty().set(value); }
     public String getStudentID() { return StudentIDProperty().get(); }
     public SimpleStringProperty StudentIDProperty() {
         if (studentID == null) studentID = new SimpleStringProperty(this, "studentID");
         return studentID;
     }
     //name is declared as simplestringproperty and its getter, setter and property methods are written
     private SimpleStringProperty name;
     public void setName(String value) { nameProperty().set(value); }
     public String getName() { return nameProperty().get(); }
     public SimpleStringProperty nameProperty() {
         if (name == null) name = new SimpleStringProperty(this, "name");
         return name;
     }
     //gender is declared as simplestringproperty and its getter, setter and property methods are written
     private SimpleStringProperty gender;
     public void setGender(String value) { genderProperty().set(value); }
     public String getGender() { return genderProperty().get(); }
     public SimpleStringProperty genderProperty() {
         if (gender == null) gender = new SimpleStringProperty(this, "gender");
         return gender;
     }
     //program is declared as simplestringproperty and its getter, setter and property methods are written
     private SimpleStringProperty program;
     public void setProgram(String value) { programProperty().set(value); }
     public String getrogram() { return programProperty().get(); }
     public SimpleStringProperty programProperty() {
         if (program == null) program = new SimpleStringProperty(this, "program");
         return program;
     }
     //reason is declared as simplestringproperty and its getter, setter and property methods are written
     private SimpleStringProperty reason;
     public void setReason(String value) { reasonProperty().set(value); }
     public String getReason() { return reasonProperty().get(); }
     public SimpleStringProperty reasonProperty() {
         if (reason == null) reason = new SimpleStringProperty(this, "reason");
         return reason;
     }
    //parameterised contsructor for FrquencyReportRow
    FrequencyReportRow(String studentID, String name, String gender, String program, String reason) {
        this.studentID = new SimpleStringProperty(studentID);
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.program = new SimpleStringProperty(program);
        this.reason = new SimpleStringProperty(reason);
    }
}