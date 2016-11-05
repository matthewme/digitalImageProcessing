/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dip_javafx;

/**
 *
 * @author Matthew Martinez
 */
public class Histogram {
    
 
    private byte[][] imageGray; //The original Image
    private int[] histogram = new int[256]; //H[i]
    private int[] histogramC = new int[265];//Hc[i]
    private int[] histogramS = new int[256];//S[i]
    private float[] histogramN = new float[256];//Normalized Histogram(also Hcn[i])
    float sum = 0;
    private int[] histogramGrayLevels = new int[256];
    
    public void scaleto256()
    {
        float min=histogramN[0];
        float max=histogramN[0];
                
        for (int i = 0; i < histogramN.length; i++)
        {
            if( histogramN[i] >max )
                max = histogramN[i];
            if( histogramN[i] < min )
                min = histogramN[i];
        }       
        
        float r=max-min;
        for (int i = 0; i < histogramN.length; i++)
        {
            getHistogramGrayLevels()[i] = (int) (((histogramN[i] - min)/r)*255);
        }
    }
    
    //Make a regular histogram
    public void calcHist() {
        for (int i = 0; i < imageGray.length; i++) {
            for (int j = 0; j < imageGray[0].length; j++) {
                histogram[(imageGray[i][j] & 0xff)]++;
            }
        }
    }
    
    //Calulate Hc[i]. A Cumulative Histogram
    public void calcCumulativeHist()
    {
        //Calulate Hc[i] = Cumulative Histogram.
        for(int i = 1; i < histogram.length; i++)
        {
            histogramC[0] = histogram[0]; 
            histogramC[i] = histogramC[i-1] + histogram[i]; 
        }
    }
    
    //Create a normalized histrogram from a cumulative histogram
    //Calculate Hcn[i]. A normalized cumulative histogram
    public void calcHistNFromC()
    {
        for (int i = 0; i < histogramN.length; i++) 
        {  
           histogramN[i] = histogramC[i] * ((float)1.0 /(imageGray.length * imageGray[0].length));    
        }   
    }
    
    //Create the equalized histogram
    //Calculate S[i]. An equalized histogram
    public void calcEqualHist()
    {
        for(int i = 0; i < histogramS.length; i++)
        {
            histogramS[i] = Math.round(histogramS.length * histogramN[i]);
            
            if(histogramS[i] >= 256)
                histogramS[i] = 255;
        }
    }
    
    //Create a normalized histogram
    public void calcHistN()
    {
        sum=0;
        for (int i = 0; i < histogram.length; i++) {
            sum += histogram[i];

        }
        sum = (float) 1.0/(imageGray.length * imageGray[0].length);
        for (int i = 0; i < histogram.length; i++) 
        {  
           histogramN[i] = histogram[i] * sum;    
        }
    }
    
 

    /**
     * @return the imageGray
     */
    public byte[][] getImageGray() {
        return imageGray;
    }

    /**
     * @param imageGray the imageGray to set
     */
    public void setImageGray(byte[][] imageGray) {
        this.imageGray = imageGray;
    }

    /**
     * @return the histogram
     */
    public int[] getHistogram() {
        return histogram;
    }

    /**
     * @param histogram the histogram to set
     */
    public void setHistogram(int[] histogram) {
        this.histogram = histogram;
    }

    /**
     * @return the histogramN
     */
    public float[] getHistogramN() {
        return histogramN;
    }

    /**
     * @param histogramN the histogramN to set
     */
    public void setHistogramN(float[] histogramN) {
        this.histogramN = histogramN;
    }

    /**
     * @return the histogramGrayLevels
     */
    public int[] getHistogramGrayLevels() {
        return histogramGrayLevels;
    }

    /**
     * @param histogramGrayLevels the histogramGrayLevels to set
     */
    public void setHistogramGrayLevels(int[] histogramGrayLevels) {
        this.histogramGrayLevels = histogramGrayLevels;
    }
    
        public int[] getHistogramC() {
        return histogramC;
    }

    public void setHistogramC(int[] histogramC) {
        this.histogramC = histogramC;
    }

    public int[] getHistogramS() {
        return histogramS;
    }

    public void setHistogramS(int[] histogramS) {
        this.histogramS = histogramS;
    }
    
}
