/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
* This class is used to recognize the face present in the detected image.
* In this, the training model is created with existing images of different persons
* and then predicts the sample image from the trained model. The face recognizer
* uses the LBPHFaceRecognizer to recognise the face in sample image.
*/
public class FaceRecognitionEigenFaces {
    public static String studentID;
    public static String basePath = System.getProperty("user.dir");
    public static String csvFilePath = basePath + "\\src\\main\\java\\cmu\\TrainingData.txt";
    public static File trainingModelFile = new File(basePath + "\\src\\main\\java\\cmu\\training_model.xml");
    
    /**
    * This function predicts the face in the image passes with respect to the trained model
    * and loads the next FXML screen i.e Dashboard of student if the image is identified, 
    * otherwise loads the new student information screen.
    */
    public void recogniseFace(File inFile, ActionEvent event) throws IOException {
	//Load the opencv library
        System.out.println("Loading library..");
        System.loadLibrary("opencv_java412");
        System.out.println("Library loaded!!");
	    
        // array of images
        ArrayList<Mat> images = new ArrayList<>();
	// array of labels
        ArrayList<Integer> labels = new ArrayList<>();
	// call function to read the training data file
        readCSV(csvFilePath, images, labels);
	    
	// path of the captured image
        String testImagePath = basePath + "\\src\\main\\java\\cmu\\saved_images\\" + inFile.getName();
        System.out.println(testImagePath);
        // convert the file to be read as image object
        Mat testSample = Imgcodecs.imread(testImagePath, 0);
        MatOfInt labelsMat = new MatOfInt();
        labelsMat.fromList(labels);
	    
        // create the recognizer object
        LBPHFaceRecognizer ffr = LBPHFaceRecognizer.create();
	    
        // if training model doesnot exist, train the images.
        if (!trainingModelFile.exists()) {
            System.out.println("Starting training...");
            ffr.train(images, labelsMat);
	    //write the training model to the file system.
            ffr.write(trainingModelFile.getAbsolutePath());
        } else {
	    // read the existing training model
            ffr.read(trainingModelFile.getAbsolutePath());
        }
	    
        int[] outLabel = new int[1];
        double[] outConf = new double[1];
	    
	// predict the sample image with respect to the traning model  
        System.out.println("Starting Prediction...");
        ffr.predict(testSample, outLabel, outConf);
        System.out.println("***Predicted label is " + outLabel[0] + ".***");
        System.out.println("***Confidence value is " + outConf[0] + ".***");
	    
	// if confidence value less than 60, load the predicted student's dashboard
        if (outConf[0] < 60) {
            studentID = String.valueOf(outLabel[0]);
            Parent report = FXMLLoader.load(getClass().getResource("dash.fxml"));
            Scene reports = new Scene(report);
            
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(reports);
            window.show();
        }
        else {
            Parent report = FXMLLoader.load(getClass().getResource("Invalid.fxml"));
            Scene reports = new Scene(report);
            
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(reports);
            window.show();
        }
        
    }
	/**
	* This fuction reads the training data file and seperates the image paths and labels into two array lists
	*/
	private static void readCSV(String csvFilePath2, ArrayList<Mat> images, ArrayList<Integer> labels)  {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(csvFilePath2));
		
		String line;
		while((line=br.readLine())!=null){
			String[] tokens=line.split("\\;");
			Mat readImage=Imgcodecs.imread(tokens[0], 0);
			images.add(readImage);
			labels.add(Integer.parseInt(tokens[1]));
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
