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

public class VisionAPI{
    
        public static String[] emotion = {"Anger","Joy","Sorrow","Surprise","No expression"};
        public static int index = 0;
	
	public static void detectFaces(File file) throws Exception, IOException {
		  List<AnnotateImageRequest> requests = new ArrayList<>();
                  System.out.println(file.getPath());

      

		  ByteString imgBytes = ByteString.readFrom(new FileInputStream(file));

		  Image img = Image.newBuilder().setContent(imgBytes).build();
		  Feature feat = Feature.newBuilder().setType(Type.FACE_DETECTION).build();
		  AnnotateImageRequest request =
		      AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		  requests.add(request);

		  try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
		    BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    List<AnnotateImageResponse> responses = response.getResponsesList();

		    for (AnnotateImageResponse res : responses) {
		      if (res.hasError()) {
		        System.out.printf("Error: %s\n", res.getError().getMessage());
		        return;
		      }

		      // For full list of available annotations, see http://g.co/cloud/vision/docs
		      for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {
		        int[] emoValue = {annotation.getAngerLikelihoodValue(),
                                   annotation.getJoyLikelihoodValue(),
                                   annotation.getSorrowLikelihoodValue(),
                                   annotation.getSurpriseLikelihoodValue(),
                        };
                        int max = 0;
                        for (int i = 0; i < emoValue.length; i++){
                            System.out.print(emoValue[i] + " ");
                            if (max < emoValue[i]){
                                max = emoValue[i];
                                index = i;
                            }
                        }
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