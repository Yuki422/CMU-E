/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author apple
 */
public class DashController implements Initializable {
//to declare all the fields used in the front-end
 @FXML
 private ImageView img;
 @FXML
 private Label lb1;
 @FXML
 private Label lb2;
 @FXML
 private Label lb3;
 @FXML
 private Label lb4;
 @FXML
 private Label lb5;
 private TextArea ann;
 
 @FXML
 private ComboBox<String> cbinput;
 @FXML
 private Label lb6;
 @FXML
 private Label lb51;
 @FXML
 private Label lb7;
 @FXML
 private Label lb8;
 @FXML
 private Label lbann;
 @FXML
 private Label lb511;
 @FXML
 private Label lb9;
 @FXML
 private Label lb10;
 @FXML
 private Label lb11;
 @FXML
 private Label lb12;
 @FXML
 private Label errlabel;
 @FXML
 private Pane pane2;
 @FXML
 private Pane pane1;
 @FXML
 private Label lb61;

 /**
 * Initializes the controller class.
 */
 @Override
 public void initialize(URL url, ResourceBundle rb) {
 //to initialize the combo-box for reason of current visit	
 cbinput.getItems().removeAll(cbinput.getItems());
 cbinput.getItems().addAll("Student Club Enquiry","Asking for Directions to Class","General Enquiry","Fee Enquiry","Course Schedule Enquiry","Check Fee Payment");
 cbinput.getSelectionModel().select("Fee Enquiry");
 String studentId = String.format("S245000%s",FaceRecognitionEigenFaces.studentID); 
 //to take the studentId input from facerecognitioneigenfaces class, which is linked to the student id in the database 
 
 Connection cnn;
 try {
 File file; Image image;
 String basePath = System.getProperty("user.dir");
 file = new File(basePath + "\\src\\main\\java\\pictures\\"+studentId+".JPG");
 image = new Image(file.toURI().toString());
 img.setImage(image);
 //setting up the file path
 
 cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/faceRecTrial","App","App");
 cnn.setAutoCommit(false);
 Statement s = cnn.createStatement();
 
 
 ResultSet rs4 = s.executeQuery("select * from studentinfo where studentId = '"+studentId+"'");
 //obtaining the result set by executing the query
 //ResultSet rs1 = s.executeQuery("select reason from visitdetails where studentId = '"+studentId+"'");
 
 
 
 while(rs4.next())
 {
 // System.out.println(rs4.getString(1));
 lb1.setText(rs4.getString(1));
 lb2.setText(rs4.getString(2));
 lb3.setText(rs4.getString(6));
 lb4.setText(rs4.getString(3));
 lb5.setText(VisionAPI.emotion[VisionAPI.index]);
 }
 //reading the basic details of the student - student id, name, gender, program and emotion
 ResultSet rs1 = s.executeQuery("select reason,time from visitdetails where studentId = '"+studentId+"'");
 //obtaining the result set by executing the query
 while(rs1.next())
 {
 lb6.setText(rs1.getString(1));
 String[] words = rs1.getString(2).split(" ");
 //System.out.println(words[0]);
 lb7.setText(words[0]);
 
 }
 //displaying the reason and date of last visit
 
 ResultSet rs2 = s.executeQuery("select announcement from announcements where studentId = '"+studentId+"'");
  //obtaining the result set by executing the query
 while(rs2.next())
 {
 lbann.setText(rs2.getString(1));
 }
 //setting the field for announcement
 ResultSet rs3 = s.executeQuery("select telecom,odi,sps,dbs,java from marks where studentId = '"+studentId+"'");
  //obtaining the result set by executing the query
 double num = 0; 
 if(rs3.next() == false)
 {
 //if marks not available, then a pane with an alert pops up; otherwise, else follows
 pane2.setVisible(true); 
 pane1.setVisible(false);
 }
 else
 {
 //the visibility of other pane is set
 pane1.setVisible(true); 
 pane2.setVisible(false);
 lb8.setText(rs3.getString(1));
 lb9.setText(rs3.getString(2));
 lb10.setText(rs3.getString(3));
 lb11.setText(rs3.getString(4));
 lb12.setText(rs3.getString(5));
 //to display the academic performance of the student with marks from five subjects being displayed
 num = num + Double.parseDouble(rs3.getString(1)) + Double.parseDouble(rs3.getString(2)) + Double.parseDouble(rs3.getString(3)) + Double.parseDouble(rs3.getString(4)) + Double.parseDouble(rs3.getString(5));
 }
 //
 num = num/5;
 
 ResultSet rs11 = s.executeQuery("select count(studentId) from visitdetails where studentId = '"+studentId+"'");
  //obtaining the result set by executing the query
 while(rs11.next())
 {
 lb61.setText(rs11.getString(1));
 }
 //this displays the frequency of visit of the student 
 
 }catch(Exception e){System.out.println(e);}
 
 } 
 @FXML
 public void exitbutton(ActionEvent event) throws SQLException, IOException
 {
 if (cbinput.getValue().isEmpty()) {
 //it is required for the user to enter the reason of current visit
 errlabel.setText("Please select the reason of current visit");
 return;
 }
 
 Connection cnn;
 try{ 
 cnn = DriverManager.getConnection("jdbc:derby://localhost:1527/faceRecTrial","App","App");
 cnn.setAutoCommit(false);
 // Statement s = cnn.createStatement();
 //System.out.println("HEllo");
 
 String query = "insert into visitdetails values(?,?,?,?)"; //sql query
 PreparedStatement ps = cnn.prepareStatement(query);
 Random r=new Random();
 int min=1,max=100000;
 int inv_no=r.nextInt(max-min)+min;
 // System.out.println("HEllo1");
 String abc ="V" + inv_no;
 //random visit id is generated
 ps.setString(1,abc);
 //visit id is set
 ps.setString(2,lb1.getText());
 //student id is set
 //System.out.println(lb1.getText());
 ps.setString(3, cbinput.getValue());
 System.out.println(cbinput.getValue());
 //current reason of visit is set
 Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 String s1 = formatter.format(new Timestamp(System.currentTimeMillis()));
 ps.setString(4,s1);
 //current timestamp is set
 //System.out.println("HEllo2");
 ps.executeUpdate();
 cnn.commit();
 }catch(Exception e){System.out.println(e);}
 
 //after saving the details of current check-in, the landing page is loaded back
 Parent report = FXMLLoader.load(getClass().getResource("page1.fxml"));
 Scene reports = new Scene(report);
 
 Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
 window.setScene(reports);
 window.show(); 
 }
}