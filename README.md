# digitalImageProcessing
Various methods of Image processing in JavaFX

This program allows a user to select an image from thier PC and apply several methods of Image processing.
GUIBorderPane contains all the image handlers:
-MyNoiseImageHandler: Method adds noise to the image with a given mean and variance. 
-MyConvolutionImageHandler: Convolution is the process of placing a mask over the pixels and performing calulations. As the mask moves over the pixels the new values are saved in the output image. Some masks included let the user smooth/blurr and perform edge detection.
-MyGrayImageHandler: Converts an image to gray and displays its gray level histogram
-MyGrayContrastImageHandler: Converts an Image to gray and changes the contrast. Contrast values given by user.
-MyEqualizeImageHandler: This Method takes a dark image and brightens it by even out the pixel values using histrogram equalization.

ImageIo.java and Noise.java were written by instructor.
