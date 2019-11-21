/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author apple
 */
public class Page1Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Loads the dashboard screen
     */
	@FXML
    public void handleButtonAction1(ActionEvent event) throws IOException {
        Parent report = FXMLLoader.load(getClass().getResource("dash.fxml"));
        Scene reports = new Scene(report);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(reports);
        window.show();
        
    }
	
    /**
     * Loads the Reports Menu
     */
    public void handleButtonAction(ActionEvent event) throws IOException {
        Parent report = FXMLLoader.load(getClass().getResource("report1.fxml"));
        Scene reports = new Scene(report);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(reports);
        window.show();
        
    }
    
    /**
     * Loads the camera for face detetction
     */
    public void handleButtonAction2(ActionEvent event) throws IOException{
        try
		{
			// load the FXML resource
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FaceDetection.fxml"));
			BorderPane root = (BorderPane) loader.load();
			// set a whitesmoke background
			root.setStyle("-fx-background-color: whitesmoke;");
			// create and style a scene
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// create the stage with the given title and the previously created
			// scene
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setTitle("Face Detection and Tracking");
			stage.setScene(scene);
			// show the GUI
			stage.show();
			
			// init the controller
			FaceDetectionController controller = loader.getController();
			controller.init();
			
			// set the proper behavior on closing the application
			stage.setOnCloseRequest((new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we)
				{
					controller.setClosed();
				}
			}));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}   
    }    
}
