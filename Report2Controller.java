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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author apple
 */
public class Report2Controller implements Initializable {

    @FXML
    private TableView<FrequencyReportRow> tv;
    @FXML
    private TableColumn<FrequencyReportRow,String> tc1;
    @FXML
    private TableColumn<FrequencyReportRow,String> tc2;
    @FXML
    private TableColumn<FrequencyReportRow,String > tc3;
    @FXML
    private TableColumn<FrequencyReportRow,String > tc4;
    @FXML
    private TableColumn<FrequencyReportRow,String > tc5;
    @FXML
    private DatePicker date1;
    @FXML
    private DatePicker date2;
    
    private ObservableList<FrequencyReportRow> data;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane1;
    LocalDate d1;
    LocalDate d2;
    XYChart.Series set1 = new XYChart.Series<>();
    @FXML
    private BarChart<?, ?> chart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private Label error;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     tc1.setCellValueFactory(new PropertyValueFactory<FrequencyReportRow,String>("studentID"));
     tc2.setCellValueFactory(new PropertyValueFactory<FrequencyReportRow,String>("name"));  
     tc3.setCellValueFactory(new PropertyValueFactory<FrequencyReportRow,String>("gender"));
     tc4.setCellValueFactory(new PropertyValueFactory<FrequencyReportRow,String>("program"));
     tc5.setCellValueFactory(new PropertyValueFactory<FrequencyReportRow,String>("reason"));
     pane1.setVisible(false);
     pane2.setVisible(false);
     
     
    } 
    @FXML
    public void gobutton1(ActionEvent event)
    {
        if(date1==null || date2==null)
        {
            error.setText("Please select date");
            return;
        }
        pane1.setVisible(true);
        pane2.setVisible(false);
        Connection cnn;
        try {
            
            cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/faceRecTrial","App","App");
            cnn.setAutoCommit(false);
            Statement s = cnn.createStatement();
            System.out.println("Hi");
            d1 = date1.getValue();
             d2 = date2.getValue();
            ResultSet rs = s.executeQuery("select studentinfo.studentId,studentinfo.name, studentinfo.gender, studentinfo.program, visitdetails.reason From STUDENTINFO inner join VISITdetails ON studentinfo.studentId = visitdetails.studentId and varchar(visitdetails.time) between '"+d1+"%' and '"+d2+"%'");
           System.out.println("HEllo");
          // LocalDate d1 = date1.getValue();
           System.out.println(d1);
            data = FXCollections.observableArrayList();
            while(rs.next())
            {
                data.add(new FrequencyReportRow(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                //tv.setItems(data);    
            }
            tv.setItems(data);
            System.out.println(data);
        }catch(Exception e){System.out.println(e);}
    }
    @FXML
    public void buttongo2(ActionEvent event) throws IOException
    {
        if(date1==null || date2==null)
        {
            error.setText("Please select date");
            return;
        }
        pane1.setVisible(false);
        pane2.setVisible(true);
        
        
        Connection cnn;
        try {
         
            cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/faceRecTrial","App","App");
            cnn.setAutoCommit(false);
            Statement s = cnn.createStatement();
            System.out.println("Hi");
           //LocalDate d1 = date1.getValue();
            //LocalDate d2 = date2.getValue();
            ResultSet rs = s.executeQuery("select visitdetails.reason, count(studentinfo.studentId) From STUDENTINFO inner join VISITdetails ON studentinfo.studentId = visitdetails.studentId and varchar(visitdetails.time) between '"+d1+"%' and '"+d2+"%' group by visitdetails.reason");
            
          // LocalDate d1 = date1.getValue();
           set1.getData().clear();
           chart.setAnimated(false);
           // data = FXCollections.observableArrayList();
            while(rs.next())
            {
                String a = rs.getString(1);
                String b = rs.getString(2);
                set1.getData().add(new XYChart.Data(a,Integer.parseInt(b)));
                //tv.setItems(data);    
                
            }
            chart.setLegendVisible(false);
           // x.setTickLabelRotation(30);
            x.setStyle("horizontal");
           chart.getData().addAll(set1);
           
            
        
            //System.out.println(data);
        }catch(Exception e){System.out.println(e);}
        
        
    }
    @FXML
    public void backbutton(ActionEvent event) throws IOException
    {
        Parent report = FXMLLoader.load(getClass().getResource("report1.fxml"));
        Scene reports = new Scene(report);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(reports);
        window.show();
    }
    
    
}
