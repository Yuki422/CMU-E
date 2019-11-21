/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * This file handles the pie charts!
 * Both the pie charts are being set according to the data from the database!
 * Moreover, date pickers are also set which selects the date. 
 * @author apple
 */
public class Report3Controller implements Initializable {

    @FXML
    private DatePicker date1;
    @FXML
    private DatePicker date2;
    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart pieChart1;
    @FXML
    private Label error;
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane1.setVisible(false);
        pane2.setVisible(false);
    }
    // TODO

    /*
    This function handles the first pie chart.
    SQL query is being executed and genders are being fetched from the database
    Then a pie chart is displayed according to the data 
    */
    @FXML
    public void gobutton1(ActionEvent event) {
        pane1.setVisible(true);
        pane2.setVisible(false);
        if (date1 == null || date2 == null) {
            error.setText("Please select date");
            return;
        }
        //System.out.println("HEllo1");
        Connection cnn;
        try {

            cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/faceRecTrial", "App", "App");
            cnn.setAutoCommit(false);
            Statement s = cnn.createStatement();
            // System.out.println("HEllo");
            LocalDate d1 = date1.getValue();
            LocalDate d2 = date2.getValue();

            ResultSet rs = s.executeQuery("select studentinfo.gender, count(studentinfo.studentId) From STUDENTINFO inner join VISITdetails ON studentinfo.studentId = visitdetails.studentId and varchar(visitdetails.time) between '" + d1 + "%' and '" + d2 + "%' group by studentinfo.gender");

            // LocalDate d1 = date1.getValue();
            
            //Observable list created to gather data from the database.
            ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();
            while (rs.next()) {
                //  System.out.println("hdk");
                String a = rs.getString(1);
                //  System.out.println(a);
                String b = rs.getString(2);
                //  System.out.println(b);
                pieChartData2.add(new PieChart.Data(a, Integer.parseInt(b)));
                //  System.out.println("abcdef");
                // System.out.println(avg1);
            }
            // System.out.println("xyz1");
            //pieChart.setMaxHeight(300);
            // pieChart.setLegendVisible(false);
            //System.out.println("xyz");
            
            //Data is being set into pie chart
            pieChart.setLabelLineLength(3);
            pieChart.setData(pieChartData2);
            // System.out.println("xyz2");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /*
    This function generates the pie chart according to the data from the table
    Connection is being made to the database using Driver Manager and the pie chart is displayed
    */

    @FXML
    public void gobutton(ActionEvent event) {
        pane1.setVisible(false);
        pane2.setVisible(true);
        if (date1 == null || date2 == null) {
            error.setText("Please select date");
            return;
        }
        Connection cnn;
        try {

            cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/faceRecTrial", "App", "App");
            cnn.setAutoCommit(false);
            Statement s = cnn.createStatement();

            LocalDate d1 = date1.getValue();
            LocalDate d2 = date2.getValue();

            //ResultSet rs = s.executeQuery("select studentinfo.gender, count(studentinfo.studentId) From STUDENTINFO inner join VISITdetails ON studentinfo.studentId = visitdetails.studentId and varchar(visitdetails.time) between '"+d1+"%' and '"+d2+"%' group by studentinfo.gender");
            // LocalDate d1 = date1.getValue();
            /* ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            while(rs.next())
            {
                String a = rs.getString(1);
                String b = rs.getString(2);
                pieChartData.add(new PieChart.Data(a,Integer.parseInt(b)));
               // System.out.println(avg1);
            }
            pieChart.setMaxHeight(300);
            pieChart.setLegendVisible(false);
            pieChart.setData(pieChartData);
             */
            // System.out.println(pieChart.getHeight());
            ResultSet rs1 = s.executeQuery("select visitdetails.reason, count(studentinfo.studentId) From STUDENTINFO inner join VISITdetails ON studentinfo.studentId = visitdetails.studentId and varchar(visitdetails.time) between '" + d1 + "%' and '" + d2 + "%' group by visitdetails.reason");

            // LocalDate d1 = date1.getValue();
            
            // Observable list is being made to gather data from the database
            ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList();
            while (rs1.next()) {
                String a = rs1.getString(1);
                String b = rs1.getString(2);
                //  System.out.println(a);
                //data added into observable array list
                pieChartData1.add(new PieChart.Data(a, Integer.parseInt(b)));
                // System.out.println(avg1);
            }
            //pieChart1.setMaxHeight(300);
            
            //Pie chart is being displayed
            pieChart1.setLabelsVisible(true);
            pieChart1.setMaxHeight(700);
            pieChart1.setLabelLineLength(3);
            pieChart1.setData(pieChartData1);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
    This is the back button which opens the report1.fxml
    Scene is set to the window and window is being shown!
     */
    @FXML
    public void backbutton(ActionEvent event) throws IOException {
        Parent report = FXMLLoader.load(getClass().getResource("report1.fxml"));
        Scene reports = new Scene(report);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(reports);
        window.show();
    }

}
