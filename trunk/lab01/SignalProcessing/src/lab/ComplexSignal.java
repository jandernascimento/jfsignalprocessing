/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author cfouard
 */
public class ComplexSignal {

  private ArrayList<Complex> data;

  public ComplexSignal() {
    this.data = new ArrayList<Complex>();
  }

  public ComplexSignal(String filename) {
    BufferedReader reader;
    this.data = new ArrayList<Complex>();

    try {
      reader = new BufferedReader(new FileReader(filename));
    } catch (IOException e) {
      System.out.println("Could not open file " + filename + " sorry...");
      System.out.println("The system returned the following error: " + e.getMessage() + "\n\n");
      return;
    }

    //... Loop as long as there are input lines.
    String line = null;
    int i = 0;
    int numberOfPoints = -1;
    try {
      while ((line = reader.readLine()) != null) {
        if (line.length() > 0) {
          char firstChar = line.charAt(0);
          // Read the number of points only once
          if ((numberOfPoints == -1) && (firstChar == '#')) {
            numberOfPoints = Integer.valueOf(line.substring(1));
          } else {

            // If the first character of a line is a %, then we have a comment, do nothing...
            if (!(firstChar == '%')) {
              int index0 = 0;
              int index1 = line.indexOf(' ');
              int index2 = line.lastIndexOf(' ');

              double real = Double.valueOf(line.substring(index0, index1));
              double imaginary = Double.valueOf(line.substring(index2));

              data.add(Complex.createFromCarthesian(real, imaginary));
            }

          }
        }
        i++;
      }

      reader.close();  // Close to unlock.
    } catch (IOException e) {
      System.out.println("Error while reading file " + filename + " sorry...\n");
      System.out.println("The system returned the following error: " + e.getMessage());
      return;
    }
  }

  public void add(Complex c) {
    data.add(c);
  }

  public Complex get(int index) {
    return data.get(index);
  }

  public void set(int index, Complex c) {
    data.set(index, c);
  }

  public int getNbSamples() {
    return data.size();
  }

  public Signal getRealSignal() {
    Signal signal = new Signal();
    signal.settName("Real part");

    for (int i = 0; i < data.size(); i++) {
      signal.addElement(i, data.get(i).getRe());
    }
    return signal;
  }

  public Signal getImaginarySignal() {
    Signal signal = new Signal();
    signal.settName("Imaginary part");

    for (int i = 0; i < data.size(); i++) {
      signal.addElement(i, data.get(i).getIm());
    }
    return signal;
  }

  public Signal getMagnitudeSignal() {
    Signal signal = new Signal();
    signal.settName("Magnitude");

    for (int i = 0; i < data.size(); i++) {
      signal.addElement(i, data.get(i).getNorm());
    }
    return signal;
  }

  public Signal getPhaseSignal() {
    Signal signal = new Signal();
    signal.settName("Phase");

    for (int i = 0; i < data.size(); i++) {
      signal.addElement(i, data.get(i).getAngle());
    }
    return signal;
  }

  public void displayReal() {
    Signal signal = this.getRealSignal();
    signal.display();
  }

  public void displayImaginary() {
    Signal signal = this.getImaginarySignal();
    signal.display();
  }

  public void displayMagnitude() {
    Signal signal = this.getMagnitudeSignal();
    signal.display();
  }

  public void displayPhase() {
    Signal signal = this.getPhaseSignal();
    signal.display();
  }

  public void display() {
    Signal real = this.getRealSignal();
    Signal imag = this.getImaginarySignal();
    Signal magnitude = this.getMagnitudeSignal();
    Signal phase = this.getPhaseSignal();

    ArrayList<GeneralSignal> list = new ArrayList<GeneralSignal>();
    list.add(real);
    list.add(imag);
    list.add(phase);

    magnitude.displayWithOtherSignals(list, "Real part, Imaginary part, magnitude and phase of a complex signal", false);
  }

  public void save(String filename) {
    int nbSamples = this.getNbSamples();
    String fileContent = "% Complex Signal\n";
    fileContent += "#" + nbSamples + "\n";
    fileContent += "\n";
    for (int i = 0; i < nbSamples; i++) {
      fileContent += data.get(i).getRe() + "\t \t" + data.get(i).getIm() + "\n";
    }
    fileContent += "\n";

    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(filename));
      out.write(fileContent);
      out.close();
    } catch (IOException e) {
      System.out.println("Could not save file " + filename + " sorry...\n");
    }

  }
}
