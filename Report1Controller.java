/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author apple
 */
public class Report1Controller implements Initializable {

   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void gobutton1(ActionEvent event) throws IOException
    {
        Parent report = FXMLLoader.load(getClass().getResource("report2.fxml"));
        Scene reports = new Scene(report);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(reports);
        window.show();
    }
    public void gobutton2(ActionEvent event) throws IOException
    {
        Parent report = FXMLLoader.load(getClass().getResource("report3.fxml"));
        Scene reports = new Scene(report);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(reports);
        window.show();
    }
    
    public void backbutton(ActionEvent event) throws IOException
    {
        Parent report = FXMLLoader.load(getClass().getResource("page1.fxml"));
        Scene reports = new Scene(report);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(reports);
        window.show();
    }
    
}
