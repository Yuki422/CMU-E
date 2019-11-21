package cmu;

/**
 *
 * @author ymurayam
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.io.File;

/**
 * This class detects emotion of the picture.
 * @author ymurayam
 */
public class VisionAPI{
    
    /**
     * data set of emotion
     */
    public static String[] emotion = {"Anger","Joy","Sorrow","Surprise","No expression"};

    /**
     * index to choose proper emotion from emotion array
     */
    public static int index = 0;
	
    /**
     * Detects emotion of the given picture. The picture is provided as File object.
     * @param file File object of the input picture
     * @throws Exception Any ecxeptions caused by this method
     * @throws IOException Exceptions related to I/O of input picture file
     */
    public static void detectFaces(File file) throws Exception, IOException {
		  List<AnnotateImageRequest> requests = new ArrayList<>();
                  System.out.println(file.getPath());

      
                  //convert picture file into original ByteString object and set values to request for google vision API
		  ByteString imgBytes = ByteString.readFrom(new FileInputStream(file));

		  Image img = Image.newBuilder().setContent(imgBytes).build();
		  Feature feat = Feature.newBuilder().setType(Type.FACE_DETECTION).build();
		  AnnotateImageRequest request =
		      AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		  requests.add(request);

                  //call google vision API engine and returns annotations of the image
		  try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
		    BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    List<AnnotateImageResponse> responses = response.getResponsesList();

		    for (AnnotateImageResponse res : responses) {
		      if (res.hasError()) {
		        System.out.printf("Error: %s\n", res.getError().getMessage());
		        return;
		      }

		      // retrieve annotation value of each emotion
		      for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {
		        int[] emoValue = {annotation.getAngerLikelihoodValue(),
                                   annotation.getJoyLikelihoodValue(),
                                   annotation.getSorrowLikelihoodValue(),
                                   annotation.getSurpriseLikelihoodValue(),
                        };
                        
                        //choose highest annotation value of each emotion
                        int max = 0;
                        for (int i = 0; i < emoValue.length; i++){
                            System.out.print(emoValue[i] + " ");
                            if (max < emoValue[i]){
                                max = emoValue[i];
                                index = i;
                            }
                        }
                        //if all of emotion likelihood balue = 1, no expression
                            if (max == 1){index = emotion.length-1;}
                            System.out.println();
                            System.out.println(emotion[index]);
                        }

		      }
                            
		    }catch (Exception e){
                        System.out.println(e);
                    }

		}
}