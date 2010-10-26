/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab;

/* Java IO imports */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* Java other imports */
import java.util.ArrayList;

/* JFreeChart imports (to display signals) */
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This class defines an abstract signal.<br/>
 * It implements utility methods for your signal (you do not have to modify this class).<br/>
 * The code required for your la exercises will be implemented in the class {@link Signal}.<br/>
 * To see an example of use of a {@link Signal} (an GeneralSignal cannot be instanciated),
 * please refer to the following example: {@link ce_00.ExampleUsage}<br/>
 * 
 * <h2>Signal description </h2>
 * 
 * A signal is stored the following way:
 *
 * <pre>
 *      |-----------|-------|-----|-----|-----|-----|-----|-----|-----|-----|
 *      |ordinate   |double |  y  |1.25 | 5.6 |4.759| 3.26| 8.2 | 5.5 |-7.6 |
 *      |-----------|-------|-----|-----|-----|-----|-----|-----|-----|-----|
 *      |abscissa   |double |  x  | -2  |-1.5 | -1  |-0.5 |  0  | 0.5 |  1  |
 *      |-----------|-------|-----|-----|-----|-----|-----|-----|-----|-----|
 *      |table index|int    |index|  0  |  1  |  2  |  3  |  4  |  5  |  6  |
 *      |-----------|-------|-----|-----|-----|-----|-----|-----|-----|-----|
 *
 * </pre>
 *
 * <h2>Usage</h2>
 * Each point is represented by an abscissa (coordinate on the x axis) and an
 * ordinate/value (coordiante on the y axis). An abscissa is unique in the signal (i.e.
 * there is only one point of the signal with a given abscissa). Of course, there 
 * may be several points with a given value within a same signal.
 * <br/>
 * A point is stored at a certain index in the signal. By default, the abscissa of
 * a point is its index (if no abscissa is given). Indices are also unique in a signal.
 * You can access the value (the ordiante) of a point either by its abscissa or by
 * its index.
 * 
 * <h2>Note: About Object Oriented Programming</h2>
 * This class and the class {@link Signal} one have been created only for the
 * Mosig "Signal Processing" computer exercises. The choice of the methods not
 * implemented in this class but delegated to the {@link Signal} implementaion
 * is <b>not</b> driven by OOP considerations, but only  by the computer exercises purposes.
 * Indeed, all the abstract methods are the one asked in the computer exercises,
 * whereas all the methods implemented in this class are utility method you do
 * not have to implement.
 * <br/>
 * <i>Please do not considere this code as an example of object oriented programming
 * paradigm. It contains some conception heresy to facilitate the computer
 * exercises work. </i>
 *
 * @author Celine Fouard
 * 
 */
public class GeneralSignal {

  /**
   * Data storage of the signal. 
   * XYSeries is a format given by the JFreeChart package, allowing to easily display a signal.
   */
  private XYSeries data;

  /**
   * Default constructor. Creates an empty signal.
   */
  public GeneralSignal() {
    this.data = new XYSeries("A Signal");
  }

  /**
   * Builds a signal stored in a file.
   * @param filename name of the file where the signal is stored.
   */
  public GeneralSignal(String filename) {
    this.data = readSeriesFromFile(filename, filename);
    if (this.data == null) {
      this.data = new XYSeries("Empty Signal");
    }
  }

  /* ************************************************************************* */
  /*                                                                           */
  /*                          Accessors and Modificators                       */
  /*                                                                           */
  /* ************************************************************************* */
  /**
   * Number of samples of the signal
   * @return the number of (abscissa, value) couple in the signal.
   */
  public int getNbSamples() {
    return data.getItemCount();
  }

  /**
   * Returns the name of the signal (the one which will be displaied in the signal display.
   */
  public String getName() {
    String name = data.getKey().toString();
    return name;
  }

  /**
   * Returns the name of the signal (the one which will be displaied in the signal display.
   */
  public void settName(String name) {
    this.data.setKey(name);
  }

  /**
   * Minimum value of the signal
   * @return the minimum value of the signal.
   */
  public double getMin() {
    return data.getMinY();
  }

  /**
   * @return the maximum value of the signal
   */
  public double getMax() {
    return data.getMaxY();
  }

  /**
   * Gives the entered abscissa at the index index.
   * Caution ! No check is performed on the value of the index (this method
   * may throw an outofbound exception).
   * @param index of the point (abscissa, value)
   * @return the corresponding abscossa.
   */
  public double getAbscissaOfIndex(int index) {
    return data.getX(index).doubleValue();
  }

  /**
   * Gives the entered value at the index index.
   * Caution ! No check is performed on the value of the index (this method
   * may throw an outofbound exception).
   * @param index of the point (abscissa, value)
   * @return the corresponding value.
   */
  public double getValueOfIndex(int index) {
    return data.getY(index).doubleValue();
  }

  /**
   * Sets the value of the existing point (abscissa, value) at index index to y.
   * If the index is negative, does not do anything.
   * If the index equals the index of the last element of the signal plus one,
   * adds an element (index, y) to the signal at the index index.
   * If the index is greateur than the index of the last element of the signal plus one,
   * does not do anything.
   * @param index index of the exsisting point. If the point does not exists, adds it
   * @param y value to be given to the corresponding point (abscissa, value).
   */
  public void setValueOf(int index, double y) {
    if (index == this.getNbSamples()) {
      data.add(index, y);
    } else if ((index >= 0) && (index < this.getNbSamples())) {
      data.updateByIndex(index, y);
    }
  }

  /**
   * Check if a point of abscissa abscissa is already in the signal
   * @param abscissa abscissa of the point (abscissa, value) to look for
   * @return true if there exists a point of the given abscissa in the signal, false otherwise
   */
  public boolean isInSeries(double abscissa) {
    return (data.indexOf(abscissa) > -1);
  }

  /**
   * Returns the index of a point (abscissa, value) in the signal.
   * Returns -1 if there is no point of the given abscissa in the signal.
   * @param abscissa abscissa of the point to look for
   * @return the index in the signal if such a point exists, -1 otherwise.
   */
  public int indexOf(double abscissa) {
    return data.indexOf(abscissa);
  }

  /**
   * Adds the element (abscissa, value) to the signal.
   * If an element of absicssa abscissa already exists, just update the value.
   * (the signal can not contain 2 points with the same abscissa).
   * @param abscissa abscissa of the point
   * @param ordinate value of the point
   */
  public void addElement(double abscissa, double ordinate) {
    int index = data.indexOf(abscissa);
    if (index < 0) {
      data.add(abscissa, ordinate);
    } else {
      data.updateByIndex(index, ordinate);
    }
  }

  /**
   * Looks for the element of the given abscissa and sets its value to ordiante.
   * If there is no points existing with the given abcisssa, adds a point
   * (abscissa, ordinate) to the signal.
   * (actually, this method does the exact same thing as addElement).
   * @param abscissa abscissa of the considered point
   * @param ordinate value to add or update in the signal
   */
  public void setElement(double abscissa, double ordinate) {
    int index = data.indexOf(abscissa);
    if (index < 0) {
      data.add(abscissa, ordinate);
    } else {
      data.updateByIndex(index, ordinate);
    }
  }

  /**
   * Returns the value (the ordianate) of a point given its abscissa.
   * @param abscissa abscissa of the considered point
   * @return return the value of the considered point (returns -1 if there is
   * no points with the given abscissa in the signal).
   */
  public double getValueOfAbscissa(double abscissa) {
    int isInSeries = data.indexOf(abscissa);
    if (isInSeries >= 0) {
      return data.getY(isInSeries).doubleValue();
    } else {
      System.out.println("There is no point whith the abscissa " + abscissa + " in the signal.\n");
      return -1;
    }
  }

  /**
   * Please do not use this method.
   * @return
   */
  protected XYSeries getData() {
    return data;
  }

  /* ************************************************************************* */
  /*                                                                           */
  /*                          Utility functions                                */
  /*                                                                           */
  /* ************************************************************************* */
  /**
   * Displays a signal as line and points.
   */
  public void display() {
    display(false);
  }

  /**
   * Displays a signal (using the JFreeChart package).
   * @param useChart if set to true, the signal is displaied as a bar chart
   * (this is used for histograms).
   */
  public void display(boolean useChart) {

    JFreeChart chart;
    int nbSamples = getNbSamples();

    if (useChart) {
      String[] categories = new String[nbSamples];
      for (int i = 0; i < nbSamples; i++) {
        categories[i] = data.getX(i).toString();
      }
      String[] categoryNames = {"Histogram"};
      double[][] categoryData = new double[1][nbSamples];
      for (int i = 0; i < nbSamples; i++) {
        categoryData[0][i] = data.getY(i).doubleValue();
      }

      CategoryDataset categoryDataset = DatasetUtilities.createCategoryDataset(
              categoryNames,
              categories,
              categoryData);

      chart = ChartFactory.createBarChart(
              "Histogram", // Title
              "Data Value", //X axis label
              "Number of Elements", // Y axis label
              categoryDataset, // dataset
              PlotOrientation.VERTICAL, // orientation
              true, // legends
              true, // tool tips
              true);
    } else {
      XYDataset xyDataSet = new XYSeriesCollection(this.data);
      chart = ChartFactory.createXYLineChart(
              "Example Dataset", // Title
              "Abscissa", //X axis label
              "Ordinate", // Y axis label
              xyDataSet, // dataset
              PlotOrientation.VERTICAL, // orientation
              true, // legends
              true, // tool tips
              true);  // urls

      XYPlot plot = (XYPlot) chart.getPlot();
      XYItemRenderer r = plot.getRenderer();
      if (r instanceof XYLineAndShapeRenderer) {
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);
      }
    }

    ChartFrame frame = new ChartFrame("Frame Title", chart);
    frame.setVisible(true);
    frame.setSize(512, 512);

  }

  /**
   * Displais a signal together whith other signals in a same chart.
   * This method is mainly use to display and check signal decomposition.
   * @param otherSignals list of signals to display together with the current one.
   */
  public void displayWithOtherSignals(ArrayList<GeneralSignal> otherSignals, String title) {
    displayWithOtherSignals(otherSignals, title, true);
  }

  public void displayWithOtherSignals(ArrayList<GeneralSignal> otherSignals, String title, boolean shapeVisible) {
    JFreeChart chart;

    XYSeriesCollection xyDataSet = new XYSeriesCollection(this.data);
    for (int i = 0; i < otherSignals.size(); i++) {
      xyDataSet.addSeries(otherSignals.get(i).getData());
    }

    chart = ChartFactory.createXYLineChart(
            title, // Title
            "Abscissa", //X axis label
            "Ordinate", // Y axis label
            xyDataSet, // dataset
            PlotOrientation.VERTICAL, // orientation
            true, // legends
            true, // tool tips
            true);  // urls

    if (shapeVisible) {
      XYPlot plot = (XYPlot) chart.getPlot();
      XYItemRenderer r = plot.getRenderer();
      if (r instanceof XYLineAndShapeRenderer) {
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);
      }
    }


    ChartFrame frame = new ChartFrame("Frame Title", chart);
    frame.setVisible(true);
    frame.setSize(512, 512);


  }

  /**
   * Reads and create a signal from a file (this function is called by the
   * constructor {@link #GeneralSignal(java.lang.String)}.
   * @param filename name of the file where to read the signal.
   * @param title title given to the representation of the signal
   * @return a XYSeries (from JFreeChart).
   */
  private XYSeries readSeriesFromFile(String filename, String title) {
    if (title == null) {
      title = filename;
    }

    XYSeries series = new XYSeries(title);
    BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(filename));
    } catch (IOException e) {
      System.out.println("Could not open file " + filename + " sorry...");
      System.out.println("The system returned the following error: " + e.getMessage() + "\n\n");
      return null;
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

              double index = Double.valueOf(line.substring(index0, index1));
              double value = Double.valueOf(line.substring(index2));

              series.add(index, value);

            }

          }
        }
        i++;
      }

      reader.close();  // Close to unlock.
    } catch (IOException e) {
      System.out.println("Error while reading file " + filename + " sorry...\n");
      System.out.println("The system returned the following error: " + e.getMessage());
      return null;
    }

    return series;
  }

  /**
   * Save the current signal in the file named filename.
   * @param filename name of the file where to store the signal.
   */
  public void save(String filename) {
    int nbSamples = this.getNbSamples();
    String fileContent = "% Signal from Computer Exercise\n";
    fileContent += "#" + nbSamples + "\n";
    fileContent += "\n";
    for (int i = 0; i < nbSamples; i++) {
      fileContent += data.getX(i) + "\t \t" + data.getY(i) + "\n";
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

  public static GeneralSignal sumSignals(ArrayList<GeneralSignal> list) {
    GeneralSignal result = new GeneralSignal();
    result.settName("Sum of signals");
    boolean checkSignalLength = true;
    int signalNumber = 0;

   if (list.isEmpty()) {
      System.out.println("The list is empty, so is the sum of signals...\n");
      return result;
    }

    int length = list.get(signalNumber).getNbSamples();
    while ((signalNumber < list.size()) && (checkSignalLength)) {
      checkSignalLength = (length == list.get(signalNumber).getNbSamples());
      signalNumber++;
    }

    /*if (!checkSignalLength) {
      System.out.println("The signals do not all have the same length. I cannot perform a sum");
      return result;
    }*/

    for (int i = 0; i < length; i++) {
      signalNumber = 0;
      GeneralSignal signal = list.get(signalNumber);
      result.addElement(signal.getAbscissaOfIndex(i), signal.getValueOfIndex(i));
      for (signalNumber = 1; signalNumber < list.size(); signalNumber++) {
        signal = list.get(signalNumber);
        double previousVal = result.getValueOfIndex(i);
        double valToAdd = signal.getValueOfIndex(i);
        result.setValueOf(i, previousVal + valToAdd);

      }
    }

    return result;
  }
}
