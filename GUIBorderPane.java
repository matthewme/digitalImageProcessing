/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dip_javafx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matthew Martinez
 * 
 */
public class GUIBorderPane extends BorderPane {
    
    File file;
    
    VBox DipOperations = new VBox(20);
    HBox inputOutputResults = new HBox(40);
    HBox inputOutputResults2 = new HBox(40);
    
    //Original Image
    Image queryImage = null;
    ImageView queryImageView;
    
    //Edited Image
    Image outputImage = null;
    ImageView outputImageView;
    
    //Histogram Image
    Image outputImageH = null;
    ImageView outputImageViewH;
    
   //Equalized Histogram Image
    Image outputImageEH = null;
    ImageView outputImageViewEH;
    
   //Equalized Image
    Image equalizedGrayImage = null;
    ImageView equalizedGrayImageView;
    
    //Textboxes and variables to get given data
    final TextField getA = new TextField();
    final TextField getB = new TextField();
    final TextField getAlpha = new TextField();
    final TextField getBeta = new TextField();
    final TextField getGamma = new TextField();
    final TextField getYa = new TextField();
    final TextField getYb = new TextField();  
    final TextField getMaskSize = new TextField();
    final TextField getVariance = new TextField();
    final TextField getMean = new TextField();
    
    
    public GUIBorderPane() {
        createUI();
    }
     
    public void getDipOperations() {
        
        //Create instance of objects
        Button queryImageButton = new Button("Add query Image");
        Button grayButton = new Button("Create Gray Image");
        Button contrastButton = new Button("Change Contrast Gray");
        Button equalizeImageButton = new Button("Equalize Image");
        Button convolutionButton = new Button("Convolution");
        Button noiseButton = new Button("Add Noise");
  
        Label contrastBoxes = new Label("Contrast Attributes");
        getA.setPromptText("Enter A (No Decimal)");
        getB.setPromptText("Enter B (No Decimal)");
        getAlpha.setPromptText("Enter Alpha");
        getBeta.setPromptText("Enter Beta");
        getGamma.setPromptText("Enter Gamma");
        getMaskSize.setPromptText("Masks (1-8)");
        getMean.setPromptText("Enter Mean");
        getVariance.setPromptText("Enter Variance");
        
        
        //Manage Handlers
        queryImageButton.setOnAction(new MyQueryImageHandler());
        grayButton.setOnAction(new MyGrayImageHandler());
        contrastButton.setOnAction(new MyGrayContrastImageHandler());
        equalizeImageButton.setOnAction(new MyEqualizeImageHandler());
        convolutionButton.setOnAction(new MyConvolutionImageHandler());
        noiseButton.setOnAction(new MyNoiseImageHandler());
        
        
        //Attach objects to screen
        DipOperations.getChildren().add(queryImageButton);
        DipOperations.getChildren().add(grayButton);
        DipOperations.getChildren().add(contrastBoxes);
        DipOperations.getChildren().add(getA);
        DipOperations.getChildren().add(getB);
        DipOperations.getChildren().add(getAlpha);
        DipOperations.getChildren().add(getBeta);
        DipOperations.getChildren().add(getGamma);
        DipOperations.getChildren().add(contrastButton);
        DipOperations.getChildren().add(equalizeImageButton);
        DipOperations.getChildren().add(getMaskSize);
        DipOperations.getChildren().add(convolutionButton);
        DipOperations.getChildren().add(getMean);
        DipOperations.getChildren().add(getVariance);
        DipOperations.getChildren().add(noiseButton);
        
    } 

    private void createUI() {
        
        // Top Border
        Text topText = new Text();
        topText.setWrappingWidth(800);
        topText.setFont(Font.font("Verdana", FontPosture.ITALIC, 40));
        topText.setFill(Color.BROWN);
        topText.setText("DIP Operation");
        topText.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setAlignment(topText, Pos.CENTER);

        this.setTop(topText);
        
        //Original Image
        queryImageView = new ImageView();
        queryImageView.setImage(queryImage);
        
        //Edited Image
        outputImageView = new ImageView();
        outputImageView.setImage(outputImage);
        
        //Histogram
        outputImageViewH = new ImageView();
        outputImageViewH.setImage(outputImageH);
       
        
        inputOutputResults.getChildren().addAll(queryImageView);//Query Image First
        inputOutputResults.getChildren().addAll(outputImageView);//Processed Image
        inputOutputResults.getChildren().addAll(outputImageViewH);//Histogram
        
        this.setCenter(inputOutputResults);
        getDipOperations();
        this.setLeft(DipOperations);
        
        //THESE GO IN THE SECOND WINDOW//
        //Equalized Image
        equalizedGrayImageView = new ImageView();
        equalizedGrayImageView.setImage(equalizedGrayImage);
        
        //Equalized Histogram
        outputImageViewEH = new ImageView();
        outputImageViewEH.setImage(outputImageEH);
        
        inputOutputResults2.getChildren().addAll(equalizedGrayImageView);//Processed Image
        inputOutputResults2.getChildren().addAll(outputImageViewEH);//Query Image First
        
    }
    
    
    
    class MyQueryImageHandler implements EventHandler<ActionEvent> {
        
                
        @Override
        public void handle(ActionEvent event) {
            
            final FileChooser fileChooser = new FileChooser();
            //
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home")));//user.home

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"));

            //Handle the queryImage
            file = fileChooser.showOpenDialog(null);
            if (file != null && file.isFile()) {
                Image queryImage2 = null;
                try {
                    queryImage2 = new Image(file.toURI().toURL().toExternalForm());//name of image
                } catch (Exception e) {
                }
                queryImageView.setImage(queryImage2);       
            }
        }
    }
    
    class MyNoiseImageHandler implements EventHandler<ActionEvent> {
        
        BufferedImage inImage;
        BufferedImage outImage;

        @Override
        public void handle(ActionEvent event) {
            {
                int mean = Integer.parseInt(getMean.getText());
                int variance = Integer.parseInt(getVariance.getText());
                
                //Read an image:
                try {
                    System.out.println("File to be opened is: " + file.getPath());
                    inImage = ImageIo.readImage(file.getPath());
                } catch (Exception e) {
                }
                
                //2- Extract the 2-d data
                Object[] byteArrays = ImageIo.getColorByteImageArray2DFromBufferedImage(inImage);
                byte[][] byteData= (byte[][]) byteArrays[0];//Use for length and height

                //Create an array with noise
                byte[][] gausianNoise = Noise.createGaussionNoise(mean, variance, byteData[0].length, byteData.length);

                //Add Noise to the Image
                Object[] byteArrayswithGaussianNoise = Noise.addGaussianNoise_3(byteArrays,gausianNoise);
                byte[][] rByteData= (byte[][]) byteArrayswithGaussianNoise[0];
                byte[][] gByteData= (byte[][]) byteArrayswithGaussianNoise[1];
                byte[][] bByteData= (byte[][]) byteArrayswithGaussianNoise[2];
                
                
                //write the image
                outImage = ImageIo.setColorByteImageArray2DToBufferedImage(rByteData, gByteData, bByteData);

                ImageIo.writeImage(outImage, "jpg", "D:\\OneDrive\\Documents\\NetBeansProjects\\DIP_JavaFX\\src\\noise.jpg"); 

                //Convert buffered Image to image
                Image outputImage = SwingFXUtils.toFXImage(outImage, null);
                //display the new image
                outputImageView.setImage(outputImage);            
            }
        }
    }
    class MyConvolutionImageHandler implements EventHandler<ActionEvent> {
        
        BufferedImage inImage;
        BufferedImage outImage;
        
        @Override
        public void handle(ActionEvent event) {  
            
            int maskNumber = Integer.parseInt(getMaskSize.getText());
            
            //MASKS
            //Blur
            float eMask1[][] = {
                {0.25f, 0.25f},
                {0.25f, 0.25f},};
            //Blur
            float eMask2[][] = {
                {0.0625f, 0.0625f, 0.0625f, 0.0625f},
                {0.0625f, 0.0625f, 0.0625f, 0.0625f},
                {0.0625f, 0.0625f, 0.0625f, 0.0625f},
                {0.0625f, 0.0625f, 0.0625f, 0.0625f},};
            //Blur
            float eMask3[][] = {
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},};
            
            float hMask1[][] = {
                {-1.0f, 0, 1.0f},
                {-2.0f, 0, 2.0f},
                {-1.0f, 0, 1.0f},};

            float hMask2[][] = {
                {-1.0f, -2.0f, -1.0f},
                {0.0f, 0.0f, 0.0f},
                {1.0f, 2.0f, 1.0f},};

            float hMask3[][] = {
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f}};
            
            float hMask4[][] = {
                {-1.0f, -1.0f, -1.0f},
                {-1.0f, 9.0f, -1.0f},
                {-1.0f, -1.0f, -1.0f}};

            float hMask5[][] = {
                {0f, -1.0f, 0f},
                {-1.0f, 5.0f, -1.0f},
                {0f, -1.0f, 0f}};
            
            //Read an image:
            try {
                    System.out.println("File to be opened is: " + file.getPath());
                    inImage = ImageIo.readImage(file.getPath());
                } catch (Exception e) {
                }
            
            //2- Extract the 2-d data
            Object[] byteArrays = ImageIo.getColorByteImageArray2DFromBufferedImage(inImage);
            byte[][] byteData= (byte[][]) byteArrays[0];//Use for height and length
            byte[][] rByteData= (byte[][]) byteArrays[0];
            byte[][] gByteData= (byte[][]) byteArrays[1];
            byte[][] bByteData= (byte[][]) byteArrays[2];

            //The convoluted image arrays
            byte[][] rByteDataNew = new byte[byteData.length] [byteData[0].length];
            byte[][] gByteDataNew = new byte[byteData.length] [byteData[0].length];
            byte[][] bByteDataNew = new byte[byteData.length] [byteData[0].length];

            Convolution cnv = new Convolution();

            //Use mask chosen by the user
            float mask[][];
            switch (maskNumber) {
                case 1:
                    mask = eMask1;
                    cnv.convolveEvenMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);   
                    break;
                case 2:
                    mask = eMask2;
                    cnv.convolveEvenMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);   
                    break;
                case 3:
                    mask = eMask3;
                    cnv.convolveEvenMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);   
                    break;
                case 4:
                    mask = hMask1;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;
                case 5:
                    mask = hMask2;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;   
                case 6:
                    mask = hMask3;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;
                case 7:
                    mask = hMask4;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;
                case 8:
                    mask = hMask5;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;
                default:
                    break;
            }    

            outImage = ImageIo.setColorByteImageArray2DToBufferedImage(rByteDataNew, gByteDataNew, bByteDataNew);
            
            ImageIo.writeImage(outImage, "jpg", "D:\\OneDrive\\Documents\\NetBeansProjects\\DIP_JavaFX\\src\\convolution.jpg"); 
            
            //Convert buffered Image to image
            Image outputImage = SwingFXUtils.toFXImage(outImage, null);
            //display the new image
            outputImageView.setImage(outputImage);   
     
        }
    }    
    
    
    //Convert an image to gray
    class MyGrayImageHandler implements EventHandler<ActionEvent> {
                          
        BufferedImage inImage;
        BufferedImage outImage;
       
        @Override
        public void handle(ActionEvent event) {
        
            //Read an image:
            try {
                    System.out.println("File to be opened is: " + file.getPath());
                    inImage = ImageIo.readImage(file.getPath());
                } catch (Exception e) {
                }
   
            //Convert an image to gray
            BufferedImage grayImage = ImageIo.toGray(inImage);
            byte[][] grayByteData=ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);
            
            //Write out as a file
            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
            ImageIo.writeImage(outImage, "jpg", "D:\\OneDrive\\Documents\\NetBeansProjects\\DIP_JavaFX\\src\\toGray.jpg"); 
            
            //Convert buffered Image to image
            Image outputImage = SwingFXUtils.toFXImage(outImage, null);
            //display the new image
            outputImageView.setImage(outputImage);
            
            
            //CREATE THE HISTOGRAM
            //Create Graph X and Y
            byte[][] grayLevel = new byte[256][256];

            Histogram myHist = new Histogram();
            //Show Histogram
            myHist.setImageGray(grayByteData);
            //Histogram
            myHist.calcHist();
            //Normalized Histogram
            myHist.calcHistN();
            myHist.scaleto256();

            //Creat Histogram Image
            int[] graylevelcount = myHist.getHistogramGrayLevels();
                  
            for (int i = 0; i < graylevelcount.length; i++) 
            {      
                for (int j = 256 -graylevelcount[i]; j < 256; j++)
                {
                    grayLevel[j][i]= (byte)(255);
                }
            }
            
            //Write out as a file
            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayLevel);
            ImageIo.writeImage(outImage, "jpg", "D:\\OneDrive\\Documents\\NetBeansProjects\\DIP_JavaFX\\src\\normalHistogram.jpg"); 
            
            //Convert buffered Image to image
            Image outputImageH = SwingFXUtils.toFXImage(outImage, null);
            //display the new image
            outputImageViewH.setImage(outputImageH);
                
        }
    }
    
     //Change the contrast of a gray Image
    class MyGrayContrastImageHandler implements EventHandler<ActionEvent> {
        
        BufferedImage inImage;
        BufferedImage outImage;
        
        @Override
        public void handle(ActionEvent event) {
            
            //Read an image:
            try {
                    //System.out.println("File to be opened is: " + file.getPath());
                    inImage = ImageIo.readImage(file.getPath());
                } catch (Exception e) {
                }   
            
            //Convert to gray. 3 channels to 1
            BufferedImage grayImage = ImageIo.toGray(inImage);
            //Extract 2 byte array
            byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);
            
            
            //Retrieve string from textbox and convert to appropiate data type
            int a = Integer.parseInt(getA.getText());
            int b = Integer.parseInt(getB.getText());
            double alpha = Double.parseDouble(getAlpha.getText());
            double beta = Double.parseDouble(getBeta.getText());
            double gamma = Double.parseDouble(getGamma.getText());
            
            //Create a lookup Table
            double[] lut= new double[256];
            for (int i = 0; i < lut.length; i++) 
            {
                double clip;

                //Between 0 and a
                if(i < a)
                {
                    clip = (alpha * i);

                    if(clip > 255)
                        clip = 255;
                    if(clip < 0)
                        clip = 0;

                    lut[i] = clip;
                }        
                else if(i >= a && i < b)//Between more than or equal to a and less than b
                {
                     clip = (beta*(i-a)) + 30;//+ya
                     if(clip > 255)
                        clip = 255;
                     if(clip < 0)
                        clip = 0;

                    lut[i] = clip;

                }
                else if(i >= b)//Values higher than L
                {
                    clip = ((gamma * (i-b)) + 200);//+yb

                    if(clip > 255)
                        clip = 255;
                    if(clip < 0)
                        clip = 0;

                    lut[i] = clip;
                }
            }

            //Replace values according to lookup table
            for(int i = 0; i < grayByteData.length; i++)
            {
                for(int j = 0; j < grayByteData[0].length; j++)
                { 
                    grayByteData[i][j] = (byte)lut[(grayByteData[i][j] & 0xff)];              
                }
            }    

            //Write out as a file
            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
            ImageIo.writeImage(outImage, "jpg", "D:\\OneDrive\\Documents\\NetBeansProjects\\DIP_JavaFX\\src\\changeContrast.jpg"); 
            
            //Convert BufferedImage to Image
            Image outputImage = SwingFXUtils.toFXImage(outImage, null);
            //Display the new image
            outputImageView.setImage(outputImage);
            
            //CREATE THE HISTOGRAM
            //Create graph x and y
            byte[][] grayLevel = new byte[256][256];

            Histogram myHist = new Histogram();
            //Set Histogram
            myHist.setImageGray(grayByteData);
            //Create Histogram
            myHist.calcHist();
            //Normalized histogram
            myHist.calcHistN();
            myHist.scaleto256();

            //Create the histogram image
            int[] graylevelcount = myHist.getHistogramGrayLevels();

            for (int i = 0; i < graylevelcount.length; i++) 
            {      
                for (int j = 256 -graylevelcount[i]; j < 256; j++)
                {
                    grayLevel[j][i]= (byte)(255);
                }
            }
            
            //Write out as a file
            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayLevel);
            ImageIo.writeImage(outImage, "jpg", "D:\\OneDrive\\Documents\\NetBeansProjects\\DIP_JavaFX\\src\\normalHistogram.jpg"); 
            
            //Convert buffered Image to image
            Image outputImageH = SwingFXUtils.toFXImage(outImage, null);
            //display the new image
            outputImageViewH.setImage(outputImageH);             
   
        }   
    }
    
        //Convert an image to gray
    class MyEqualizeImageHandler implements EventHandler<ActionEvent> {
                          
        BufferedImage inImage;
        BufferedImage outImage;
       
        @Override
        public void handle(ActionEvent event) {
            
            //Read an image:
            try {
                    System.out.println("File to be opened is: " + file.getPath());
                    inImage = ImageIo.readImage(file.getPath());
                } catch (Exception e) {
            }
        
            //Convert an image to gray
            BufferedImage grayImage = ImageIo.toGray(inImage);
            //Extract the image data by placing into a 2-Dimensional Array
            byte[][] grayByteData=ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);        
            
            //CREATE AN EQUALIZED HISTOGRAM    
            //Create instance of Histogram
            Histogram myHist = new Histogram();
            //The histogram image
            byte[][] grayLevel = new byte[256][256];
            
            //Pass the read image to myHist
            myHist.setImageGray(grayByteData);
            //Calculate H[i] - Regular Histogram
            myHist.calcHist();
            //Calulate the cumulative histogram Hc[i]
            myHist.calcCumulativeHist();
            //Calulate the normalized cumulative histogram Hcn[i]
            myHist.calcHistNFromC();
            //Calculate the equalized histogram S[i] (lookup table)
            myHist.calcEqualHist();
                        
            //equalize the actual Image from the S[i] lookup table
            for(int i = 0; i < grayByteData.length; i++)
            {
                for(int j = 0; j < grayByteData[0].length; j++)
                {
                    //Get old gray level value at i,j
                    int oldGrayLevelValue = (grayByteData[i][j] & 0xff); 

                    //lookup new value
                    int newGrayLevelValue = myHist.getHistogramS()[oldGrayLevelValue];

                    //Set new gray level value at i,j
                    grayByteData[i][j] = (byte) newGrayLevelValue;
                }
            }
            
            //Write out as a file
            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
            ImageIo.writeImage(outImage, "jpg", "D:\\OneDrive\\Documents\\NetBeansProjects\\DIP_JavaFX\\src\\equalizedImage.jpg");
            
            //Convert buffered Image to image
            Image equalizedImage = SwingFXUtils.toFXImage(outImage, null);
            //display the new image
            equalizedGrayImageView.setImage(equalizedImage); 
            
                        
            //CREATE AN EQUALIZED HISTOGRAM FROM THE EQUALIZED IMAGE
            //Send equalized Image
            myHist.setImageGray(grayByteData);
            //Calculate the new histogram
            myHist.calcHist();
            //Normalize it
            myHist.calcHistN();
            //Scale it
            myHist.scaleto256();
            
            //Create a equalized histogram image array
            int[] graylevelcount = myHist.getHistogramGrayLevels();

            for (int i = 0; i < graylevelcount.length; i++) 
            {      
                for (int j = 256 -graylevelcount[i]; j < 256; j++)
                {
                    grayLevel[j][i]= (byte)(255);
                }
            }
            
            //Write out as a file
            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayLevel);
            ImageIo.writeImage(outImage, "jpg", "D:\\OneDrive\\Documents\\NetBeansProjects\\DIP_JavaFX\\src\\equalizedHistogram.jpg");
            
            //Convert buffered Image to image
            Image outputImageEH = SwingFXUtils.toFXImage(outImage, null);
            //display the new image
            outputImageViewEH.setImage(outputImageEH);     
            

            //Make a window
            Pane secondWindow = new Pane();
            //Make a scene
            Scene secondScene = new Scene(secondWindow);
            //Make a stage
            Stage secondStage = new Stage();
            secondStage.setTitle("Equalized Image");
            secondStage.setScene(secondScene);
            
            secondWindow.getChildren().add(inputOutputResults2);

            secondStage.show();
          
          
  
        }
    }
    
}
