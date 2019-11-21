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

public class FaceRecognitionEigenFaces {
    public static String studentID;
    public static String basePath = System.getProperty("user.dir");
    public static String csvFilePath = basePath + "\\src\\main\\java\\cmu\\TrainingData.txt";
    public static File trainingModelFile = new File(basePath + "\\src\\main\\java\\cmu\\training_model.xml");
    
    public void recogniseFace(File inFile, ActionEvent event) throws IOException {
        System.out.println("Loading library..");
        System.loadLibrary("opencv_java412");
        System.out.println("Library loaded!!");
        
        ArrayList<Mat> images = new ArrayList<>();
        ArrayList<Integer> labels = new ArrayList<>();
        readCSV(csvFilePath, images, labels);
        String testImagePath = basePath + "\\src\\main\\java\\cmu\\saved_images\\" + inFile.getName();
        System.out.println(testImagePath);
        
        Mat testSample = Imgcodecs.imread(testImagePath, 0);
        MatOfInt labelsMat = new MatOfInt();
        labelsMat.fromList(labels);
        
        LBPHFaceRecognizer ffr = LBPHFaceRecognizer.create();
        
        if (!trainingModelFile.exists()) {
            System.out.println("Starting training...");
            ffr.train(images, labelsMat);
            ffr.write(trainingModelFile.getAbsolutePath());
        } else {
            ffr.read(trainingModelFile.getAbsolutePath());
        }
        int[] outLabel = new int[1];
        double[] outConf = new double[1];
        
        System.out.println("Starting Prediction...");
        ffr.predict(testSample, outLabel, outConf);
        System.out.println("***Predicted label is " + outLabel[0] + ".***");
        System.out.println("***Confidence value is " + outConf[0] + ".***");
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
