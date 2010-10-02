/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce_01;

import java.util.ArrayList;
import lab.GeneralSignal;
import lab.Signal;
import utils.KeyboardIO;

/**
 *
 * @author cfouard
 */
public class ComputerExercise_01 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    int choice = -1;

    String menuMsg = "\n------------------------------------------------------------\n";
    menuMsg += " Main Menu:                                                 \n";
    menuMsg += "    1: Read a signal from a file and display it             \n";
    menuMsg += "    2: Read a signal from a file and display its histogram \n";
    menuMsg += "    3: Generate (and display) a random signal              \n";
    menuMsg += "    4: Generate a Gaussian Noise signal                     \n";
    menuMsg += "    5: Test Signal Decomposition                            \n";
    menuMsg += "                                                            \n";
    menuMsg += "    9: Exit                                                 \n";
    menuMsg += " Please choose a number between 1 and 9                     \n";
    menuMsg += "------------------------------------------------------------\n\n";

    while (choice != 9) {
      choice = KeyboardIO.readInteger(menuMsg);

      String filename = null;
      Signal signal = null;
      Signal histo = null;
      String msg = null;
      int nbElements = 0;
      double min = 0.0;
      double max = 0.0;


      switch (choice) {
        case 1:
          filename = KeyboardIO.readString("Please enter the file name\n");
          signal = new Signal(filename);
          signal.display();
          System.out.println("Signal mean: " + signal.getMean());
          System.out.println("Signal standard deviation: " + signal.getStandardDev());

          break;

        case 2:
          filename = KeyboardIO.readString("Please enter the file name\n");
          signal = new Signal(filename);
          signal.display();
          System.out.println("Signal mean: " + signal.getMean());
          System.out.println("Signal standard deviation: " + signal.getStandardDev());

          double length = KeyboardIO.readDouble("Please enter the length of the histogram interval\n");
          histo = signal.getHitogram(length);
          histo.display(true);
          break;

        case 3:
          nbElements = utils.KeyboardIO.readInteger("Please enter the number of elements of your signal\n");
          min = utils.KeyboardIO.readDouble("Please enter the min value of your random signal:\n");
          max = utils.KeyboardIO.readDouble("Please enter the max value of your random signal:\n");
          signal = Signal.createRandomSeries(min, max, nbElements);
          signal.display();

          if (utils.KeyboardIO.readYesNo("Do you want to display the histogram ? \n")) {
            double binLength = utils.KeyboardIO.readDouble("Please enter the intervall size: \n");
            histo = signal.getHitogram(binLength);
            histo.display(true);
          }

          if (utils.KeyboardIO.readYesNo("Do you want to save your signal? \n")) {
            String saveFilename = utils.KeyboardIO.readString("Please enter the file name: \n");
            signal.save(saveFilename);
          }

          break;

        case 4:
          nbElements = utils.KeyboardIO.readInteger("Please enter the number of elements of your signal\n");
          signal = Signal.createGaussianNoiseSeries(nbElements);
          signal.display();

          if (utils.KeyboardIO.readYesNo("Do you want to display the histogram ? \n")) {
            double binLength = utils.KeyboardIO.readDouble("Please enter the intervall size: \n");
            histo = signal.getHitogram(binLength);
            histo.display(true);
          }

          if (utils.KeyboardIO.readYesNo("Do you want to save your signal? \n")) {
            String saveFilename = utils.KeyboardIO.readString("Please enter the file name: \n");
            signal.save(saveFilename);
          }

          break;

        case 5:
          msg = "Do you want to\n";
          msg += " 1) read the original signal from a file or\n";
          msg += " 2) generate the original signal randomly ?\n";
          msg += "please choose 1 or 2\n";
          int readOrGenerate = KeyboardIO.readInteger(msg);
          switch (readOrGenerate) {
            case 1:
              filename = KeyboardIO.readString("Please enter the file name\n");
              signal = new Signal(filename);
              break;
            case 2:
              nbElements = utils.KeyboardIO.readInteger("Please enter the number of elements of your signal\n");
              min = utils.KeyboardIO.readDouble("Please enter the min value of your random signal:\n");
              max = utils.KeyboardIO.readDouble("Please enter the max value of your random signal:\n");
              signal = Signal.createRandomSeries(min, max, nbElements);
              break;
            default:
              System.out.println("Please choose 1 or 2\n");
              break;
          }
          if (signal != null) {
            signal.settName("Original signal");
            msg = "Choose a type of decomposition\n";
            msg += "1) impulse    decomposition\n";
            msg += "2) step       decomposition\n";
            msg += "3) even/odd   decomposition\n";
            msg += "4) interlaced decomposition\n";
            int decompositionType = KeyboardIO.readInteger(msg);
            ArrayList<GeneralSignal> decomposition = null;

            switch (decompositionType) {
              case 1:
                decomposition = signal.impulseDecomposition();
                decomposition.add(GeneralSignal.sumSignals(decomposition));
                signal.displayWithOtherSignals(decomposition, "Impulse decomposition");
                break;
              case 2:
                decomposition = signal.stepDecomposition();
                decomposition.add(GeneralSignal.sumSignals(decomposition));
                signal.displayWithOtherSignals(decomposition, "Step decomposition");
                break;
              case 3:
                decomposition = signal.evenOddDecomposition();
                decomposition.add(GeneralSignal.sumSignals(decomposition));
                signal.displayWithOtherSignals(decomposition, "Even / Odd decomposition");
                break;
              case 4:
                decomposition = signal.interlacedDecomposition();
                decomposition.add(GeneralSignal.sumSignals(decomposition));
                signal.displayWithOtherSignals(decomposition, "Interlaced decomposition");
                break;
              default:
                System.out.println("I do not understand your choise, sorry...\n");
                break;
            }
          }

          break;

        case 9:
          System.out.println("Good Bye.");
          break;
        default:
          System.out.println("The number you entered is not in the menu, sorry....");
          break;
      }
    }
  }
}
