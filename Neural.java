/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author esheinon
 */

// This is a regression model to convert °C temperatures to °F. It has only one 
// input and one output. It uses a small set of training data to learn the weight
// and bias in the model. 

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Neural {

    public static void main(String[] args) {

        Scanner read = new Scanner(System.in);

        // Define the array lists needed in the learning process.
        ArrayList<Double> feed = new ArrayList<>();
        ArrayList<Double> exptResults = new ArrayList<>();
        ArrayList<Double> errors = new ArrayList<>();
        ArrayList<Double> weightDerivatives = new ArrayList<>();
        ArrayList<Double> trainResults = new ArrayList<>();

        // Assign random numbers (between -25 and 25) for the weight w and bias b.
	// Random numbers are used just to see how the network performs when starting
	// from different initial values of the parameters.
        Random r = new Random();
        double w = 50 * r.nextDouble() - 25;
        double b = 50 * r.nextDouble() - 25;

        //    System.out.println("How many data pairs in training data?");
        //    int dataSize = Integer.valueOf(read.nextLine());
        //    System.out.println("Insert the test values:");
        //    for (int i = 0; i < dataSize; i++) {
        //        double x = Double.valueOf(read.nextLine());
        //        feed.add(x);
        //    }
        //    System.out.println("Insert the test data results:");
        //    for (int i = 0; i < dataSize; i++) {
        //        double y = Double.valueOf(read.nextLine());
        //        exptResults.add(y);
        //    }
        // With the code above one could manually give the training data but
        // for simplicity we just put it there automatically with 7 data points.
        int dataSize = 7;

        //Below are the learning data feed and expected results
        feed.add(-40.0);
        feed.add(-10.0);
        feed.add(0.0);
        feed.add(8.0);
        feed.add(15.0);
        feed.add(22.0);
        feed.add(38.0);

        exptResults.add(-40.0);
        exptResults.add(14.0);
        exptResults.add(32.0);
        exptResults.add(46.0);
        exptResults.add(59.0);
        exptResults.add(72.0);
        exptResults.add(100.0);

        System.out.println("Strating values for weight and bias were w = " + w + ",  b = " + b);
        System.out.println("Learning data feed was " + feed);
        System.out.println("Learning data results were " + exptResults);

        // Start the training loop
        // it seems that if the initial weight and bias are farther away
        // from the correct values, it takes quite a lot of iterations to get 
        // close to the corrct values. Less than 1000 is certainly not enough,
        // something between 1000-2000 works rather well. With 4000 rounds the
        // result seems to be very good.
        for (int n = 0; n < 4000; n++) {

            // Use training data feed to compute initial results and add them to an array list
            for (int i = 0; i < dataSize; i++) {
                double tRes = w * feed.get(i) + b;
                trainResults.add(tRes);
            }

            // Compute the difference with results and expected results from the training data
            // and add them to an array list
            for (int i = 0; i < dataSize; i++) {
                double err = trainResults.get(i) - exptResults.get(i);
                errors.add(err);
            }

            // As an error function E we use the mean squared error
            // E = 1/(2N) \sum_{x \in A} |t(x)-y(x)|^2, where N is the number 
            // of data points in training data set A.
            // 
            // The derivative of the error (with single data point) with respect
            // to the weight w is
            // dE/dw = x(wx+b - y) = x(t-y)
            // and with respect to the bias b is
            // dE/db = wx+b - y = t-y.
            // To get the derivatives of the "full" error function, we just 
            // add the derivatives with all points and divide by 2N.
            // Compute the values of derivatives w.r.t. weights and add into an array list.
            for (int i = 0; i < dataSize; i++) {
                double wDeriv = feed.get(i) * errors.get(i);
                weightDerivatives.add(wDeriv);
            }

            // Compute the mean error of the derivatives for the correction of w
            double wMeanError = 0;
            for (Double d : weightDerivatives) {
                wMeanError += d;
            }
            wMeanError = wMeanError / dataSize;

            // Compute the mean error for the correction of b
            double bMeanError = 0;
            for (Double d : errors) {
                bMeanError += d;
            }
            bMeanError = bMeanError / dataSize;

            // Assing new corrected values for the weight w and bias b using the
            // formulae w -> w-0.001/N *\sum_{x \in A} x(wx+b-y) and
            // b -> b-0.001/N *\sum_{x \in A} x(wx+b-y). Here we chose 0.001 to 
            // be the learning speed of the network since
            // it seems that with a faster learning speed the network doesn't converge 
            // even close to the expected parameters.
            // The mean (i.e. sum divided by N) was computed above.
            w = w - (0.001 * wMeanError);
            b = b - (0.001 * bMeanError);

            // A rough rounding of the parameters to doubles with three decimals.
            // It seems that without any rounding the parameters become NaN after 
            // a couple of hundred training rounds.
            int wInt = (int) (w * 1000.0);
            w = ((double) wInt) / 1000.0;

            int bInt = (int) (b * 1000.0);
            b = ((double) bInt) / 1000.0;

            // Here one could print the values during the training but with
            // this isn't an option with so many rounds...
            //System.out.println("w = " + w + ",  b = " + b);
            // Clear the arraylists for next round
            trainResults.clear();
            errors.clear();
            weightDerivatives.clear();

        }

        // Print the learned parameters and use them to estimate 100°C in °F.
        System.out.println("");
        System.out.println("Learned values for weigt w and bias b are:");
        System.out.println("w = " + w + ",  b = " + b + " (expected w=1.8 and b=32)");

        System.out.println("");
        System.out.println("Predicted value using the learned parameters:");
        System.out.println("100°C ~ " + (w * 100 + b) + "°F (expected 212)");

    }
}
