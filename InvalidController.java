/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.sql.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ppsra
 */
public class InvalidController implements Initializable {
	//Declare all of FXML Elements
    ObservableList<String> reasonList = FXCollections.observableArrayList("Student Club Enquiry", "Asking for Directions to Class", "General Enquiry", "Fee Enquiry", "Course Schedule Enquiry", "Other");
    ObservableList<String> programList = FXCollections.observableArrayList("GlobalMISM", "MSIT", "MSPPM");
    ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Other");

    @FXML
    private TextField tx1;
    @FXML
    private TextField tx2;
    @FXML
    private TextField tx6;
    @FXML
    private ChoiceBox<String> cb;
    @FXML
    private Button btn;
    @FXML
    private AnchorPane dp;
    @FXML
    private ChoiceBox<String> cb2;
    @FXML
    private ChoiceBox<String> cb21;
    @FXML
    private DatePicker dob;
    @FXML
    private Label errlabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            cb.setItems(reasonList);
            cb2.setItems(programList);
            cb21.setItems(genderList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	/**
     * A method to save the details of the new student.
     */
    @FXML
    private void saveBtn(ActionEvent event) {
        if ((tx1.getText().isEmpty()) || (tx2.getText().isEmpty()) || (tx6.getText().isEmpty()) || (cb.getValue().isEmpty()) || (cb2.getValue().isEmpty()) || (cb21.getValue().isEmpty()) || dob.getValue()==null) {
                errlabel.setText("Please fill all the details");
                return;
            } 
        
          
        Connection cnn;
        try {
            //Creating required connection to the database
            File file = new File("/Users/apple/NetBeansProjects/CMU/src/pictures/analytics.png");
            cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/faceRecTrial", "App", "App");
            cnn.setAutoCommit(false);
            Statement s = cnn.createStatement();

            //INSERTING STUDENT INFO
            PreparedStatement psInsert = cnn.prepareStatement("insert into StudentInfo values (?, ?, ?, ?, ?, ?)");
            psInsert.setString(1, tx1.getText());
            psInsert.setString(2, tx2.getText());
            psInsert.setString(3, (String) cb2.getValue());      
            psInsert.setDate(4,Date.valueOf(dob.getValue()));
            psInsert.setString(5, tx6.getText());
            psInsert.setString(6, (String) cb21.getValue());
            psInsert.executeUpdate();

            //INSERTING VISITING DETAILS
            PreparedStatement psInsert2 = cnn.prepareStatement("insert into VisitDetails values (?, ?, ?, ?)");
            Random r=new Random();
            int min=1,max=100000;
            int inv_no=r.nextInt(max-min)+min;
            String abc ="V" + inv_no;
            psInsert2.setString(1, abc);        
            psInsert2.setString(2, tx1.getText());
            psInsert2.setString(3, (String) cb.getValue());
            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s1 = formatter.format(new Timestamp(System.currentTimeMillis()));
            psInsert2.setString(4,s1);  
            psInsert2.executeUpdate();
            cnn.commit();
            
			//Setting Scence Back to the Dashboard
            Parent report = FXMLLoader.load(getClass().getResource("page1.fxml"));
            Scene reports = new Scene(report);
        
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(reports);
            window.show();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
