/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce_02;

import java.util.ArrayList;
import lab.GeneralSignal;
import lab.Signal;
import utils.KeyboardIO;

/**
 *
 * @author cfouard
 */
public class ComputerExercise_02 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    int choice = -1;

    String menuMsg = "\n------------------------------------------------------------\n";
    menuMsg += " Main Menu:                                                 \n";
    menuMsg += "    1: Test recursive filter                                \n";
    menuMsg += "    2: Test linear   convolution                            \n";
    menuMsg += "    3: Test circular convolution                            \n";
    menuMsg += "                                                            \n";
    menuMsg += "    9: Exit                                                 \n";
    menuMsg += " Please choose a number between 1 and 9                     \n";
    menuMsg += "------------------------------------------------------------\n\n";

    while (choice != 9) {
      choice = KeyboardIO.readInteger(menuMsg);

      String filename       = null;
      String outputFilename = null;
      String kernelFilename = null;

      Signal signal         = null;
      Signal kernel         = null;
      Signal filteredSignal = null;

      int nbElements = 0;
      String msg = null;
      ArrayList<GeneralSignal> list;
      GeneralSignal sum;

      switch (choice) {

        case 1:
          Signal x1 = null;
          Signal x2 = null;
          Signal x3 = null;
          Signal x4 = null;
          msg = "\n------------------------------------------------------------\n";
          msg += " Sub  Menu:                                                 \n";
          msg += "    1: Generate x1, x2, x3, gaussian noise and sums         \n";
          msg += "    2: Apply signlePolLowPassFilter to a signal             \n";
          msg += "    3: Sum several signals                                  \n";
          msg += "                                                            \n";
          msg += "    9: Back to main menu                                    \n";
          msg += " Please choose a number between 1 and 9                     \n";
          msg += "------------------------------------------------------------\n\n";
          int choice2 = 0;
          while (choice2 != 9) {
            choice2 = KeyboardIO.readInteger(msg);
            switch (choice2) {
              case 1:
                int nbSamples = KeyboardIO.readInteger("How many samples in each signal ?\n");
                x1 = Signal.generateX1(nbSamples);
                x2 = Signal.generateX2(nbSamples);
                x3 = Signal.generateX3(nbSamples);
                x4 = Signal.createGaussianNoiseSeries(nbSamples);

                list = new ArrayList<GeneralSignal>();
                list.add(x1);
                list.add(x2);
                list.add(x3);

                sum = GeneralSignal.sumSignals(list);
                sum.displayWithOtherSignals(list, "Generated signal", false);

                list.add(x4);
                GeneralSignal noisySignal = GeneralSignal.sumSignals(list);
                noisySignal.displayWithOtherSignals(list, "Noisy signal", false);

                // Saving signals
                if (KeyboardIO.readYesNo("Save the x1 (please answer yes or no)?\n")) {
                  outputFilename = KeyboardIO.readString("Please enter the output file name to save x1\n");
                  x1.save(outputFilename);
                }
                if (KeyboardIO.readYesNo("Save the x2 (please answer yes or no)?\n")) {
                  outputFilename = KeyboardIO.readString("Please enter the output file name to save x2\n");
                  x2.save(outputFilename);
                }
                if (KeyboardIO.readYesNo("Save the x3 (please answer yes or no)?\n")) {
                  outputFilename = KeyboardIO.readString("Please enter the output file name to save x3\n");
                  x3.save(outputFilename);
                }
                if (KeyboardIO.readYesNo("Save the x4 (please answer yes or no)?\n")) {
                  outputFilename = KeyboardIO.readString("Please enter the output file name to save x4\n");
                  x4.save(outputFilename);
                }

                if (KeyboardIO.readYesNo("Save the generated signal (please answer yes or no)?\n")) {
                  outputFilename = KeyboardIO.readString("Please enter the output file name\n");
                  sum.save(outputFilename);
                }
                if (KeyboardIO.readYesNo("Save the noisy signal (please answer yes or no)?\n")) {
                  outputFilename = KeyboardIO.readString("Please enter the output file name\n");
                  noisySignal.save(outputFilename);
                }

                break;

              case 2:
                filename = KeyboardIO.readString("Please enter the file name of the signal to process.\n");
                signal = new Signal(filename);
                signal.settName("Original signal");
                signal.display();
                filteredSignal = signal.singlePolLowPassFilter();
                filteredSignal.display();
                list = new ArrayList<GeneralSignal>();
                list.add(signal);
                filteredSignal.displayWithOtherSignals(list, "Initial and filtered signal", false);
                break;
              case 9:
                break;
              default:
                System.out.println("I do not understand your choice, sorry...\n");
                break;
                
              case 3:
                int nbSingals = KeyboardIO.readInteger("Please enter the number of signals you want to sum\n");

                list = new ArrayList<GeneralSignal>();
                for(int i = 0; i < nbSingals; i++) {
                  filename = KeyboardIO.readString("Please enter the signal number " + (i+1) + " file name.\n");
                  signal = new Signal(filename);
                  list.add(signal);
                }

                sum = GeneralSignal.sumSignals(list);
                sum.displayWithOtherSignals(list, "Generated signal", false);
                if (KeyboardIO.readYesNo("Do you want to save the result signal?\n")) {
                  outputFilename = KeyboardIO.readString("Please enter the name of the output file.\n");
                  sum.save(outputFilename);
                }
                
            }
          }

          break;

        case 2:
          filename = KeyboardIO.readString("Please enter the name of the signal file.\n");
          kernelFilename = KeyboardIO.readString("Please enter the name of the kernel file\n");
          signal = new Signal(filename);
          kernel = new Signal(kernelFilename);
          filteredSignal = signal.linearConvolve(kernel);
          list = new ArrayList<GeneralSignal>();
          list.add(signal);
          filteredSignal.displayWithOtherSignals(list, "Original and filtered signals", false);

          if (KeyboardIO.readYesNo("Save filtered signal?\n")) {
            outputFilename = KeyboardIO.readString("Please enter the name of the output file.\n");
            filteredSignal.save(outputFilename);
          }
          
          break;

        case 3:
          filename = KeyboardIO.readString("Please enter the name of the signal file.\n");
          kernelFilename = KeyboardIO.readString("Please enter the name of the kernel file\n");
          signal = new Signal(filename);
          kernel = new Signal(kernelFilename);
          filteredSignal = signal.circularConvolve(kernel);
          list = new ArrayList<GeneralSignal>();
          list.add(signal);
          filteredSignal.displayWithOtherSignals(list, "Original and filtered signals", false);

          if (KeyboardIO.readYesNo("Save filtered signal?\n")) {
            outputFilename = KeyboardIO.readString("Please enter the name of the output file.\n");
            filteredSignal.save(outputFilename);
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
