/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ce_04;

import java.util.ArrayList;
import lab.GeneralSignal;
import lab.Image;
import lab.Signal;
import utils.KeyboardIO;

/**
 *
 * @author cfouard
 */
public class ComputerExercise_04 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    int choice = -1;

    String menuMsg = "\n------------------------------------------------------------\n";
    menuMsg += " Main Menu:                                                 \n";
    menuMsg += "    1: Open and display an image                            \n";
    menuMsg += "    2: Open an image and sub-sample it                      \n";
    menuMsg += "    3: Convert an image to a signal                         \n";
    menuMsg += "    4: Apply Contrast Stretching to a signal                \n";
    menuMsg += "    5: Convert a signal to an image                         \n";
    menuMsg += "    6: Open an image, convert it to a signal, apply contrast\n";
    menuMsg += "         stretching to the signal and converts it back to   \n";
    menuMsg += "         an image (when signal is to big to be saved with   \n";
    menuMsg += "         menu 3, 4 and 5...)                                \n";
    menuMsg += "    7: Open an image and apply 2D convolution               \n";
    menuMsg += "                                                            \n";
    menuMsg += "    9: Exit                                                 \n";
    menuMsg += " Please choose a number between 1 and 9                     \n";
    menuMsg += "------------------------------------------------------------\n\n";

    while (choice != 9) {
      choice = KeyboardIO.readInteger(menuMsg);

      String filename = null;
      String outputFilename = null;
      String kernelFilename = null;

      Image img = null;
      Image filteredImage = null;
      Image resultImage = null;
      Signal signal = null;
      Signal histo = null;
      Signal filteredSignal = null;
      Signal kernelSignal = null;
      Image kernelImage = null;

      int width;
      int height;
      int beanLength = 0;
      int newMin, newMax;

      int r1, s1, r2, s2;

      String msg = null;
      ArrayList<GeneralSignal> list;

      switch (choice) {
        case 1: // 1: Open and display an image
          filename = KeyboardIO.readString("Please enter the name of the file containing the image (png format)\n");
          img = Image.open(filename);
          if (img != null) {
            img.display();
            System.out.println("Image width: " + img.getWidth() + ", image height: " + img.getHeight() + "\n");
          }
          break;

        case 2: // 2: Open an image and sub-sample it
          filename = KeyboardIO.readString("Please enter the name of the file containing the image (png format)\n");
          img = Image.open(filename);
          if (img != null) {
            int factor = KeyboardIO.readInteger("Please enter the sub sampling factor.\n");
            filteredImage = img.subSamples(factor);
            filteredImage.display();

            if (KeyboardIO.readYesNo("Do you want to save the output image?\n")) {
              outputFilename = KeyboardIO.readString("Please enter the name of the file of the output image.\n");
              filteredImage.save(outputFilename);
            }
          }

          break;

        case 3: // 3: Convert an image to a signal
          filename = KeyboardIO.readString("Please enter the name of the file containing the image (png format)\n");
          img = Image.open(filename);
          if (img != null) {
            signal = img.getAsSignal();
            if (KeyboardIO.readYesNo("Display result signal?\n")) {
              signal.display();
            }
            if (KeyboardIO.readYesNo("Do you want to display the signal histogram? \n")) {
              beanLength = KeyboardIO.readInteger("Please enter the length of the bean:\n ");
              histo = signal.getHitogram(beanLength);
              histo.display(true);
            }
            if (KeyboardIO.readYesNo("Do you want to save the generated signal?\n")) {
              outputFilename = KeyboardIO.readString("Please enter the output file name.\n");
              signal.save(outputFilename);
            }
          }
          break;

        case 4: // 4: Apply Contrast Stretching to a signal
          filename = KeyboardIO.readString("Please enter the name of the file containing the signal.\n");
          signal = new Signal(filename);

          if (KeyboardIO.readYesNo("Do you want to display the original signal histogram? \n")) {
            beanLength = KeyboardIO.readInteger("Please enter the length of the bean:\n ");
            histo = signal.getHitogram(beanLength);
            histo.display(true);
          }

          newMin = KeyboardIO.readInteger("Please enter the minimum of the new range.\n");
          newMax = KeyboardIO.readInteger("Please enter the maximum of the new range.\n");

          filteredSignal = signal.stretchContrast((double) newMin, (double) newMax);
          filteredImage = new Image(filteredSignal, img.getWidth(), img.getHeight());

          filteredImage.display();

          if (KeyboardIO.readYesNo("Do you want to save the result image?\n")) {
            outputFilename = KeyboardIO.readString("Please enter the name of the file of the output image.\n");
            filteredImage.save(outputFilename);
          }


          if (KeyboardIO.readYesNo("Do you want to display the corresponding histogram?\n")) {
            beanLength = KeyboardIO.readInteger("Please enter the length of the bean:\n ");
            histo = filteredSignal.getHitogram(beanLength);
            histo.display(true);
          }
          break;




        case 5: // 5: Convert a signal to an image
          filename = KeyboardIO.readString("Please enter the name of the file containing the signal.\n");
          signal = new Signal(filename);
          width = KeyboardIO.readInteger("Please enter the width  of the output image.\n");
          height = KeyboardIO.readInteger("Please enter the height of the output image.\n");

          img = new Image(signal, width, height);
          img.display();

          if (KeyboardIO.readYesNo("Do you want to save the image?\n")) {
            outputFilename = KeyboardIO.readString("Please enter the file name of the output image (png format).\n");
            img.save(outputFilename);
          }
          break;

        case 6:
          filename = KeyboardIO.readString("Please enter the file name of the original image.\n");
          img = Image.open(filename);

          if (img != null) {
            img.display();
            signal = img.getAsSignal();
            if (KeyboardIO.readYesNo("Do you want to display the signal histogram? \n")) {
              beanLength = KeyboardIO.readInteger("Please enter the length of the bean:\n ");
              histo = signal.getHitogram(beanLength);
              histo.display(true);
            }
            System.out.println("Min grey level in the original signal: " + signal.getMin());
            System.out.println("Max grey level in the original signal: " + signal.getMax());

            newMin = KeyboardIO.readInteger("Please enter the minimum of the new range.\n");
            newMax = KeyboardIO.readInteger("Please enter the maximum of the new range.\n");

            filteredSignal = signal.stretchContrast((double) newMin, (double) newMax);
            filteredImage = new Image(filteredSignal, img.getWidth(), img.getHeight());

            filteredImage.display();

            if (KeyboardIO.readYesNo("Do you want to save the result image?\n")) {
              outputFilename = KeyboardIO.readString("Please enter the name of the file of the output image.\n");
              filteredImage.save(outputFilename);
            }


            if (KeyboardIO.readYesNo("Do you want to display the corresponding histogram?\n")) {
              beanLength = KeyboardIO.readInteger("Please enter the length of the bean:\n ");
              histo = filteredSignal.getHitogram(beanLength);
              histo.display(true);
            }
          }
          break;

        case 7: // 7: Open an image and apply 2D convolution
          filename = KeyboardIO.readString("Please enter the file name of the original image.\n");
          img = Image.open(filename);

          if (img != null) {
            img.display();
            kernelFilename = KeyboardIO.readString("Please enter the file name of the kernel (as a png image).\n");
            kernelImage = Image.open(kernelFilename);
            resultImage = img.convolve(kernelImage);
            resultImage.display();

            if (KeyboardIO.readYesNo("Do you want to save the output image?\n")) {
              outputFilename = KeyboardIO.readString("Please enter the output file name\n");
              resultImage.save(outputFilename);
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

  
