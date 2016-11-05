/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dip_javafx;

import java.awt.image.BufferedImage;

/**
 *
 * @authors Matthew Martinez, Dr. Quweider
 */
public class Convolution {
    

    public void convolveEvenMask(byte[][]rByteData,byte[][]gByteData,byte[][]bByteData, byte[][] rByteDataNew, byte[][] gByteDataNew,byte[][] bByteDataNew, float[][] eMask) 
    {
 
        //Check your mask
        for (int i = 0; i < eMask.length; i++) {
            for (int j = 0; j < eMask[0].length; j++) {
                System.out.print(" " + eMask[i][j]);
            }
            System.out.println("\n------------------------------");
        }

        // Convolution
        int m2 = (int) (eMask.length / 2);//Top to Bottom
        int n2 = (int) (eMask[0].length / 2);//Left to Right
        System.out.println("m2 = :" + m2 + " n2 = " + n2);
       
        float sumR, sumG, sumB;
        for(int i = 0; i < rByteDataNew.length - eMask.length; i++)
        {
            for(int j = 0; j < rByteDataNew[0].length - eMask[0].length; j++)
            {
                sumR = 0.0f;
                sumG = 0.0f;
                sumB = 0.0f;

                for(int x = 0; x < eMask.length; x++)
                    for(int y = 0; y < eMask[0].length; y++)
                    {
                        sumR += eMask[x][y] * (rByteData[x+i][y+j] & 0xff);
                        sumG += eMask[x][y] * (gByteData[x+i][y+j] & 0xff);
                        sumB += eMask[x][y] * (bByteData[x+i][y+j] & 0xff);
                    }
                
                sumR = ImageIo.clip(Math.abs(sumR));
                sumG = ImageIo.clip(Math.abs(sumG));
                sumB = ImageIo.clip(Math.abs(sumB));
                
                rByteDataNew[i][j]= (byte) sumR;
                gByteDataNew[i][j]= (byte) sumG;
                bByteDataNew[i][j]= (byte) sumB;
            }
        }
        
        //HANDLE BORDERS
        //Top to Bottom
        int arrayHeight = rByteDataNew.length;
        for(int i = 0; i < arrayHeight; i++)
        {
            for(int j = (rByteDataNew[0].length - eMask[0].length); j < rByteDataNew[0].length; j++)
            {
                rByteDataNew[i][j] = rByteData[i][j];
                gByteDataNew[i][j] = gByteData[i][j];
                bByteDataNew[i][j] = bByteData[i][j];
                //System.out.println("Coordinates ("+i+","+j+")");

            }
        }

        //Left to Right
        for(int i = rByteDataNew.length - eMask.length; i < rByteDataNew.length; i++)
        {
            for(int j = 0; j < rByteDataNew[0].length; j++)
            {
                rByteDataNew[i][j] = rByteData[i][j];
                gByteDataNew[i][j] = gByteData[i][j];
                bByteDataNew[i][j] = bByteData[i][j];
            }
        }
    }
    
    public void convolveOddMask(byte[][]inputR, byte[][]inputG, byte[][]inputB, byte[][] outputR,byte[][] outputG, byte[][] outputB, float[][] hMask) {

        float sumR,sumG,sumB;
        //Check you mask
        for (int i = 0; i < hMask.length; i++) {
            for (int j = 0; j < hMask[0].length; j++) {
                System.out.print(" " + hMask[i][j]);
            }
            System.out.println("\n------------------------------");
        }

        // Decide border handling regions
        int h = (int) Math.floor((hMask.length / 2));
        int v = (int) Math.floor((hMask[0].length / 2));
        System.out.println("h = :" + h);

        // Handle borders:
        handleBorder(inputR, outputR, h, v);
        handleBorder(inputG, outputG, h, v);
        handleBorder(inputB, outputB, h, v);
        
        System.out.println("Mask has: row size =" + hMask.length + " column size= " + hMask[0].length);

        // Convolution
        int m2 = (int) Math.floor((hMask.length / 2));
        int n2 = (int) Math.floor((hMask[0].length / 2));
        System.out.println("m2 = :" + m2 + " n2 = " + n2);

        for (int i = m2; i < outputR.length - m2; i++) {
            for (int j = n2; j < outputR[0].length - n2; j++) {
                sumR = 0.0f;
                sumG = 0.0f;
                sumB = 0.0f;
                for (int x = -m2; x <= m2; x++) {
                    for (int y = -n2; y <= n2; y++) {
                        sumR += hMask[x + m2][y + n2] * (inputR[i + x][ j + y] & 0xff);
                        sumG += hMask[x + m2][y + n2] * (inputG[i + x][ j + y] & 0xff);
                        sumB += hMask[x + m2][y + n2] * (inputB[i + x][ j + y] & 0xff);
                    }
                }
                sumR = ImageIo.clip(Math.abs(sumR));
                sumG = ImageIo.clip(Math.abs(sumR));
                sumB = ImageIo.clip(Math.abs(sumR));

                outputR[i][j]= (byte) sumR;
                outputG[i][j]= (byte) sumG;
                outputB[i][j]= (byte) sumB;
            }
        }
    }
    
   public void handleBorder(byte[][] input, byte[][]output, int hmask, int vmask) {

        int argb, r, g, b, a;
        int h = input.length;
        int w = input[0].length;
        //
        for (int i = 0; i < hmask; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }
        
        for (int i = h - hmask; i < h; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }
        
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < vmask; j++) {
                output[i][j] = input[i][j];
            }
        }
        for (int i = 0; i < h; i++) {
            for (int j = w - vmask; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }
    }    

}
