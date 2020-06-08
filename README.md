# Temperatures-neural-java
This is a regression model to convert °C temperatures to °F. It has only one input and one output. It uses a small set (7 pairs of °C and °F temperatures) of training data to learn the weight and bias in the model.

I was going through some Tensorflow tutorial and this kind of neural network was one of the first examples. As I had just finished self-studying a Java course and had read about the theory of neural networks, I wanted to try to build an example by hand. The error function is differentiated with respect to the weight and the bias and these derivatives are used for backpropagation to train the network.

Currently there is not much functionality in the program as the idea was to try to build a simple neural network by hand and see how it performs a simple task of computing a linear function (the conversion from °C to °F is given by the function °F = 1.8*°C + 32). 

In the beginning we assign random values (between -25 and 25) for the weight and the bias to see how the network learns from different starting points. As the error function we use the mean squared error and the error is computed from the results after each training round and the weight and bias are corrected according to the error after each round. After the given number of epochs we print the final values for the weight and the bias and use them to predict some temperature that was not included in the training data.

It seems that the network gives very good results with ~4000 epochs. 1000-2000 epochs is rather okay but the results certainly depend on the initial values of the weight and the bias. Less that 1000 is certainly not enough.
