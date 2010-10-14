/*
 * Please modify this class and complete the following methods corresponding
 * to your computer exercise.
 */
package lab;

import java.util.ArrayList;

/**
 *
 * @author Raquel & Jander
 */
public class Signal extends GeneralSignal {

  public Signal() {
    super();
  }

  public Signal(String filename) {
    super(filename);
  }

  /* ************************************************************************* */
  /*                                                                           */
  /*         Computer Exercise number 01                                       */
  /*                                                                           */
  /* ************************************************************************* */
  /**
   * Computes and returns the mean value of the signal.
   * @return mean value of the signal.
   */
  public double getMean() {
    int nbSamples = this.getNbSamples();
    double mean = 0.0;

    // Write your code here
    for(int i=0;i<=this.getNbSamples()-1;i++)
        mean = mean+this.getValueOfIndex(i);
    mean=mean/this.getNbSamples();
    
    return mean;
  }

  /**
   * Computes and returns the standard deviation (&#963;) of the signal.
   * @return Standard deviation (&#963;) of the signal.
   */
  public double getStandardDev() {
    int nbSamples = this.getNbSamples();
    double standardDev = 0.0;

    // Write your code here
    double variance = 0.0;
    double mean=this.getMean();
    for(int i=0;i<=this.getNbSamples()-1;i++){
        variance=variance + (Math.pow((this.getValueOfIndex(i)-mean),2)); //sum of (the var's power 2)
    }
    variance=variance / (this.getNbSamples()-1);
    standardDev=Math.sqrt(variance);

    return standardDev;
  }

  /**
   * Computes the histogramm of a signal for a given interval length.<br/>
   * This means that this function:
   *  - divides the value interval interval of the signal
   * ([getMin(), getMax()]) into N intervals of length {@link intervalLength}
   * N = ???.
   * <br/>
   * <ul>
   *    <li>for each interval of length intervalLength, counts the number of
   * signal samples which value/ordinate is within the corresponding interval.
   *    </li>
   *    <li>stores this value at the ascissa corresponding to the center of the
   * interval.
   *    </li>
   *
   * </ul>
   * @param intervalLength length of each interval
   * @return a signal containing the histogram of the current signal.
   */
  public Signal getHitogram(double intervalLength) {
    Signal histo = new Signal();
    if (intervalLength <= 0.0) {
      System.out.println("Error in computeHistogram method from Signal:\n The length of an interval must be strictly positive.\n");
      return null;
    }

    // Write your code here
    int cont; 
    for (double valor = this.getMin(); valor<=this.getMax(); valor=valor+intervalLength){
        cont=0;
        for (int j=0; j<=this.getNbSamples()-1;j++){
            if ((this.getValueOfIndex(j)>=valor) && (this.getValueOfIndex(j)<valor+intervalLength)){
                cont++;
            }
        }

        histo.addElement(valor + (intervalLength/2), cont);
    }

    return histo;
  }

  public static Signal createRandomSeries(int nbElements) {
    return createRandomSeries(0.0, 1.0, nbElements);
  }

  public static Signal createRandomSeries(double min, double max, int nbElements) {
    Signal resultSignal = new Signal();
    resultSignal.settName("Random Signal");

    // Write your code here
    double number, min_number_ger=1, max_number_ger=0, mean, std_dev;
    if ((min<0)||(max>1)) {
      System.out.println("Error in createRandomSeries method from Signal:\n The interval min and max must be between 0 and 1.\n");
      return null;
    }
    for (int i=0;i<=nbElements-1;i++){
        do{            
            number=Math.random();                    
        }while((number<min) || (number>max));
        if (number<min_number_ger){
            min_number_ger=number;
        }
        if (number>max_number_ger){
            max_number_ger=number;
        }       
        resultSignal.addElement(i, number);
    }
    mean=(max_number_ger+min_number_ger)/2;
    std_dev=(max_number_ger-min_number_ger)/Math.sqrt(12);
    System.out.println("theoretical mean : "+mean);
    System.out.println("theoretical standard deviation : "+std_dev);
    return resultSignal;
  }

  public static Signal createGaussianNoiseSeries(int nbElements) {
    Signal resultSignal = new Signal();
    float standardDesviation = 1;
    float mean = 0;
    
    for (int i = 0; i < nbElements; i++) {
      float rnd1 = (float)Math.random();
      float rnd2 = (float)Math.random();
      float ln=(float)-2d*(float)Math.log(rnd1);
      float sqrtLn=(float)Math.sqrt(ln);
      float cosRad=(float)2 * (float)Math.PI * rnd2;
      //based on http://www.dspguru.com/dsp/howtos/how-to-generate-white-gaussian-noise
      resultSignal.setValueOf(i,sqrtLn*Math.cos(cosRad) * standardDesviation + mean);
    }

    return resultSignal;
  }

  /**
   * Given the number of samples <i>nbSamples</i> (nbSamples = this.getNbSamples()) of the
   * current signal, decomposes the signal in <i>nbSamples</i> <b>impulse</b> component singals.
   * @return an ArrayList containing nbSamples impulse component signals.
   */
  public ArrayList<GeneralSignal> impulseDecomposition() {
    ArrayList<GeneralSignal> list = new ArrayList<GeneralSignal>();

    // Write your code here
    double x,y;
   
    for(int n=0;n<=this.getNbSamples()-1;n++){
        x=this.getAbscissaOfIndex(n);
        y=this.getValueOfIndex(n); //returns the y value of the index
        Signal signal = new Signal();
        for (int k=0;k<=this.getNbSamples();k++){
            if(k==n){
                signal.addElement(k, y);
            }
            else{
                signal.addElement(k, 0);
            }
        }
        list.add(signal);
    }
    
    return list;
  }

  /**
   * Given the number of samples <i>nbSamples</i> (nbSamples = this.getNbSamples()) of the
   * current signal, decomposes the signal in <i>nbSamples</i> <b>step</b> component signals.
   * @return an ArrayList containing nbSamples step component signals.
   */
  public ArrayList<GeneralSignal> stepDecomposition() {
    ArrayList<GeneralSignal> list = new ArrayList<GeneralSignal>();

    // Write your code here
    double x,y;
    int prior;

    for(int n=0;n<=this.getNbSamples()-1;n++){
        x=this.getAbscissaOfIndex(n);
        y=this.getValueOfIndex(n); 
        Signal signal = new Signal();
        for(int k=0;k<=this.getNbSamples()-1;k++){
            if (k<n){
                signal.addElement(k, 0);                
            }
            else{
                if (n==0){
                    signal.addElement(k, y);
                }
                else{
                    prior=(int) (y-this.getValueOfIndex((int) x-1));
                    signal.addElement(k, prior);                 
                }
            }         
        }
        list.add(signal);
      }  
    return list;
  }

  /**
   * Decomposes the current signal into an <b>even signal</b> and an <b>odd signal</b>.
   * @return an ArrayList of 2 signals (one even component signal and one odd component signal).
   */
  public ArrayList<GeneralSignal> evenOddDecomposition() {
    ArrayList<GeneralSignal> list = new ArrayList<GeneralSignal>();

    // Write your code here
    double x,y;
    int n_ele = this.getNbSamples();
   
    Signal even_signal = new Signal();
    Signal odd_signal = new Signal();

    for(int i=0;i<=this.getNbSamples()-1;i++){
        x=this.getAbscissaOfIndex(i);
        y=this.getValueOfIndex(i);

        even_signal.addElement(x, (y + this.getValueOfIndex(n_ele-1-i))/2);
        odd_signal.addElement(x, (y - this.getValueOfIndex(n_ele-1-i))/2);
    }
    list.add(even_signal);
    list.add(odd_signal);

    return list;
  }

  /**
   * Decomposes the current signal into an <b>even samples signal</b> and an <b>odd samples signal</b>.
   * @return an ArrayList of 2 signals (one even component signal and one odd component signal).
   */
  public ArrayList<GeneralSignal> interlacedDecomposition() {
    ArrayList<GeneralSignal> list = new ArrayList<GeneralSignal>();

    // Write your code here
    double x,y;

    Signal even_signal = new Signal();
    Signal odd_signal = new Signal();

    for(int i=0;i<=this.getNbSamples()-1;i++){
        x=this.getAbscissaOfIndex(i);
        y=this.getValueOfIndex(i); 

        if ((x % 2) == 0){ //then it is even
            even_signal.addElement(x, y);
            odd_signal.addElement(x, 0);
        }
        else{
            even_signal.addElement(x, 0);
            odd_signal.addElement(x, y);
        }
        
    }
    list.add(even_signal);
    list.add(odd_signal);

    return list;
  }

  /* ************************************************************************* */
  /*                                                                           */
  /*         Computer Exercise number 02                                       */
  /*                                                                           */
  /* ************************************************************************* */
  /**
   * Generates and returns the signal
   * x1[n] = sin(2 &Pi;n / 100)
   * @param nbSamples number of samples of the signal.
   * @return x1[n] = sin(2 &Pi;n / 100)
   */
  public static Signal generateX1(int nbSamples) {
    Signal x1 = new Signal();
    x1.settName("x1[n] = sin(2 Pi n / 100)");

    // Write your code here
    double y;
    for(int i=0;i<nbSamples-1;i++){
        y=Math.sin((2.0 * Math.PI * i) / 100.0);
        x1.addElement((double) i, y);
    }

    return x1;
  }

  /**
   * Generates and returns the signal
   * x2[n] =  4*exp(-(n-150)^2/300) - exp(-(n-150)^2/2500)
   * @param nbSamples number of samples of the signal.
   * @return x2[n] =  4*exp(-(n-150)^2/300) - exp(-(n-150)^2/2500)
   */
  public static Signal generateX2(int nbSamples) {
    Signal x2 = new Signal();
    x2.settName("x2[n] =  4*exp(-(n-150)^2/300) - exp(-(n-150)^2/2500)");

    // Write your code here
    double part1, part2, y;
    for(int i=0;i<nbSamples-1;i++){
        part1=i-150.0;
        part1=part1 * part1;
        part1=part1/300.0;
        part1=part1*(-1);
        part1=Math.exp(part1);
        part1=4*part1;

        part2=i-150.0;
        part2=part2 * part2;
        part2=part2 / 2500.0;
        part2=part2*(-1);
        part2=Math.exp(part2);

        y=part1-part2;
        x2.addElement((double) i, y);
    }

    return x2;
  }

  /**
   * Generates and returns the signal
   * x3[n] =  1 for 240 &lt; n &gt; 300
   *         -2 for 299 &lt; n &gt; 380
   *          0  otherwise
   * @param nbSamples
   * @return x3
   */
  public static Signal generateX3(int nbSamples) {
    Signal x3 = new Signal();
    x3.settName("x3");

    // Write your code here
    double y;
    for(int i=0;i<nbSamples-1;i++){
        if (240<i && i<300)
            y=1;
        else if(299<i && i<380)
            y=-2;
        else
            y=0;
        x3.addElement((double) i, y);
    }

    return x3;
  }

  /**
   *
   * Applies the following filter to current signal and returns the resulting signal:
   * y[0] = ??
   * For n from 1 to numberOfSamples-1
   * y[n] = 0.05*x[n] + 0.95*y[n-1]
   *
   */
  public Signal singlePolLowPassFilter() {
    Signal result = new Signal();
    result.settName("Filtered signal");
    // Write your code here
    for(int n=0;n<getNbSamples();n++){
 
        double yOfNMinusOne=result.getValueOfIndex(n-1);
        double xOfN=result.getAbscissaOfIndex(n);
        result.addElement(n, 0.05*xOfN+0.95*yOfNMinusOne);
        
    }
    //end of my code

    return result;
  }

   /**
   * Truncates linear convolution of the current signal with the given kernel
   * (Note: the output signal has the same number of samples
   *  than the current signal)
   */
  public Signal linearConvolve(Signal kernel) {
    Signal result = new Signal();
    result.settName("Convolved signal");
    int nbSamples = this.getNbSamples();
    int kernelSize = kernel.getNbSamples();

    // Write your code here

    return result;
  }

 /**
   * Circular convolve the current signal with the given kernel
   * (Note: the output signal has the same number of samples
   *  than the current signal)
   */
  public Signal circularConvolve(Signal kernel) {
    Signal result = new Signal();

    result.settName("Convolved signal");
    int nbSamples = this.getNbSamples();
    int kernelSize = kernel.getNbSamples();

    // Write your code here
    return result;
  }

}
