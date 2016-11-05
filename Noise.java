/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dip_javafx;

import java.util.Random;

/**
 *
 * @author Dr. mkquweider
 */
public class Noise {

    public static byte[][] createGaussionNoise(int mean, int variance, int w, int h) {
        float noise;
        byte[][] noiseArray = new byte[h][w];

        Random noisegenerator = new Random();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                noise = (float) (mean + noisegenerator.nextGaussian() * Math.sqrt((double) variance));
                noiseArray[i][j] = (byte) (ImageIo.clip(noise));
   
            }
        }
        return noiseArray;
    }

    public static byte[][] createSaltPepperNoise(float prob, int w, int h) {
        byte[][] noiseArray = new byte[h][w];
        float low;
        float temp, noise = 0;
        float high;

        low = prob / 2;
        high = 1 - low;

        Random noisegenerator = new Random();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                temp = (float) noisegenerator.nextFloat();
                if (temp <= low) {
                    noise = 0;
                } else if (temp >= high) {
                    noise = 255;
                } else {
                    noise = 128; //neutral 
                }
                noiseArray[i][j] = (byte) (ImageIo.clip(noise));
            }
        }
        return noiseArray;
    }

    public static byte[][] addGaussianNoise(byte[][] original, byte[][] noiseImage) {
        int w = original[0].length;
        int h = original.length;
        byte[][] imagePlusNoiseArray = new byte[h][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                imagePlusNoiseArray[i][j] = (byte) ImageIo.clip((float) ((original[i][j] & 0xff) + (noiseImage[i][j] & 0xff)));

            }
        }
        return imagePlusNoiseArray;
    }

    public static Object[] addGaussianNoise_3(Object[] original, byte[][] noiseImage) {
        Object[] threeChannel = new Object[3];
        for (int k = 0; k < 3; k++) {
            byte[][] current_color = (byte[][]) original[k];
            threeChannel[k] = addGaussianNoise(current_color, noiseImage);
        }
        return threeChannel;
    }

    public static byte[][] addSaltPepperNoise(byte[][] original, byte[][] noiseImage) {
        int w = original[0].length;
        int h = original.length;
        byte[][] imagePlusNoiseArray = new byte[h][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((noiseImage[i][j] & 0xff) == 128) {
                    imagePlusNoiseArray[i][j] = (byte) original[i][j];
                } else {
                    imagePlusNoiseArray[i][j] = (byte) ImageIo.clip((float) ((noiseImage[i][j] & 0xff)));
                }
            }
        }
        return imagePlusNoiseArray;
    }

    public static Object[] addSaltPepperNoise_3(Object[] original, byte[][] noiseImage) {
        Object[] threeChannel = new Object[3];
        for (int k = 0; k < 3; k++) {
            byte[][] current_color = (byte[][]) original[k];
            threeChannel[k] = addSaltPepperNoise(current_color, noiseImage);
        }
        return threeChannel;
    }

}